#! venv/bin/python
import sys
from datetime import datetime

from lxml import etree

root = etree.Element("kml", nsmap={None: "http://www.opengis.net/kml/2.2"})



doc = etree.SubElement(root, "Document")
# Set default style - This could be prettier?
style = etree.SubElement(doc, "Style", id="seeadler-dot-icon")
styleIcon = etree.SubElement(style, "StyleIcon")
icon = etree.SubElement(styleIcon, "Icon")
href = etree.SubElement(icon, "href")
href.text = "http://dagik.org/kml_intro/E/ball.png"

styleA = etree.SubElement(doc, "Style", id="path-style")
lineStyle = etree.SubElement(styleA, "LineStyle")
lineColor = etree.SubElement(lineStyle, "color")
lineColor.text = "FF1400FF"
lineWidth = etree.SubElement(lineStyle, "width")
lineWidth.text = "5"



LinePlacemarks = etree.Element("Placemark")

visibility = etree.SubElement(LinePlacemarks, "visibility")
visibility.text = "1"
LineString = etree.SubElement(LinePlacemarks, "LineString")
tessellate = etree.SubElement(LineString, "tessellate")
tessellate.text = "1"
gpsCoordinates = etree.SubElement(LineString, "coordinates")
styleUrlPath = etree.SubElement(LinePlacemarks, "styleUrl")
styleUrlPath.text = "#path-style"

points = []

with open(sys.argv[1]) as f:
	addPoint = points.append
	for line in f:
		# Read line and tokenize
		timestamp, lon, lat, alt = line.strip().split(";")
		try:
			addPoint((int(timestamp), float(lon), float(lat), float(alt)))
		except ValueError:
			addPoint((int(timestamp), None))
# Sort points by timestamp
points.sort()
# We assume that we go in circles, thus if the last is a waypoint, use the first measurement
if points[-1][1] is None:
	points.append(points[0])

coord = "\n"

i = 0
while i < len(points):
	p = points[i]
	if p[1] is None and 0 < i < len(points) - 1:
		# Format timestamp to YYY-MM-DDTHH:MM:SSZ
		timestamp = datetime.fromtimestamp(int(p[0])/1000).isoformat() + "Z"
		
		# Interpolate
		j = i
		while j >= 0 and points[j][1] is None:
			j -= 1
		if points[j][1] is None:
			break			
		ta, xa, ya = points[j][0], points[j][1], points[j][2]
		j = i
		while j < len(points) - 1 and points[j][1] is None:
			j += 1
		if points[j][1] is None:
			break
		tb, xb, yb = points[j][0], points[j][1], points[j][2]
		
		# Find x from the timestamps
		lon = xb-((1-((p[0]-float(ta)) / (tb - ta)))*(xb - xa))

		lat = unicode(ya + (yb - ya)* (lon - xa)/(xb-xa))
		lon = unicode(lon)
		alt = u'0'
		styleUrl = etree.Element("styleUrl")
		styleUrl.text = "#seeadler-dot-icon"
		# Create timestamp element
		ts = etree.Element("TimeStamp")
		when = etree.Element("when")
		when.text = timestamp
		ts.append(when)
		# Point
		point = etree.Element("Point")
		coordinates = etree.SubElement(point, "coordinates")
		coordinates.text = lat + "," + lon + "," + alt
		
		
		placemark = etree.Element("Placemark")
		placemark.append(ts)
		placemark.append(styleUrl)
		placemark.append(point)
		doc.append(placemark)
	else:
		coord += "          {:f},{:f},{:f}\n".format(p[2], p[1], p[3])
	i += 1
gpsCoordinates.text = coord
doc.append(LinePlacemarks)


# Print the tree to standard output
print '<?xml version="1.0" encoding="UTF-8"?>'
print etree.tostring(root, pretty_print=True)
