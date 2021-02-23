# Lesson-4-Assignment

The  Lesson4XML four XML program is invoked without arguments to parse a file
named JobResult_UCSDExt.xml, which contains the following XML structure:
```xml
<jobresult>
    <serial>0000012345</serial>
    <data>
        <visible-string length="16">000000007b020000</visible-string>
    </data>
    <data>
        <structure count="2">
            <unsigned>255</unsigned>
            <double-long-unsigned>0</double-long-unsigned>
        </structure>
    </data>
    <data>
        <double-long-unsigned>1538752262</double-long-unsigned>
    </data>
</jobresult>
```
Lesson4XML uses three methods to retrieve the text component of the "serial",
"visible-string"  and "unsigned"  elements of  the document.  The methods  of
retrieval  are  DOM, SAX  and XPATH.  A successful run  of the program should
generate something resembling the following:
```
Results of XML parsing using DOM parser:
serial: 0000012345
visible-string: 000000007b020000
unsigned: 255

Results of XML parsing using SAX parser:
serial: 0000012345
visible-string: 000000007b020000
unsigned: 255

Results of XML parsing using XPATH:
serial: 0000012345
visible-string: 000000007b020000
unsigned: 255
```
This  is my first exposure to the use of Java for manipulating XML documents.
Of  the  three methodologies  I  implemented,  I found  SAX  to  be the  most
challenging. XPATH seems to be the most versatile. Using XPATH, the following
two lines…
```
        String expression = "//serial|//visible-string|//unsigned";
        NodeList nodeList =
        (NodeList)xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
```
… are all  that were required to generate a list of the targeted  nodes.
