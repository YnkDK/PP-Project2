#! venv/bin/python
import sys
from datetime import datetime

from lxml import etree

root = etree.Element("kml", nsmap={None: "http://opengis.net/kml/2.2"})



doc = etree.SubElement(root, "Document")
# Set default style - This could be prettier?
style = etree.SubElement(doc, "Style", id="seeadler-dot-icon")
styleIcon = etree.SubElement(style, "StyleIcon")
icon = etree.SubElement(styleIcon, "Icon")
href = etree.SubElement(icon, "href")
href.text = "http://dagik.org/kml_intro/E/ball.png"


with open(sys.argv[1]) as f:
	styleUrl = etree.Element("styleUrl")
	styleUrl.text = "#seeadler-dot-icon"
	for line in f:
		# Read line and tokenize
		timestamp, lon, lat, alt = line.strip().split(";")
		# Format timestamp to YYY-MM-DDTHH:MM:SSZ
		timestamp = datetime.utcfromtimestamp(int(timestamp)/1000).isoformat() + "Z"
		# Create timestamp element
		ts = etree.Element("TimeStamp")
		when = etree.Element("when")
		when.text = timestamp
		ts.append(when)
		# Point
		point = etree.Element("Point")
		coordinates = etree.SubElement(point, "coordinates")
		coordinates.text = lon + "," + lat + "," + alt
		
		
		placemark = etree.Element("Placemark")
		placemark.append(ts)
		placemark.append(styleUrl)
		placemark.append(coordinates)
		doc.append(placemark)

# Print the tree to standard output
print '<?xml version="1.0" encoding="UTF-8"?>'
print etree.tostring(root, pretty_print=True)
