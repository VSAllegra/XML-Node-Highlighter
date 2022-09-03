import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class Highlighter
 {   
    
    //Code Refactored From https://mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/
    public static void main(String[] args) {

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try (InputStream is = new FileInputStream("com.dropbox.android.xml")) {

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(is);

        //Get Nodes In Document
        NodeList listOfNodes = doc.getElementsByTagName("node");

        //Go Through Each Node and Change Value of Selected from F to T
        for (int i = 0; i < listOfNodes.getLength(); i++) {
            Node node = listOfNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Node selected_state = node.getAttributes().getNamedItem("selected");
                if ("false".equals(selected_state.getTextContent())) {
                        selected_state.setTextContent("true");
                }   
            }
        }
        FileOutputStream output = new FileOutputStream("modified.xml");
        writeXml(doc, output);
    }catch(Exception e){
        e.printStackTrace();
    }
}

private static void writeXml(Document doc,  OutputStream output)    throws TransformerException, UnsupportedEncodingException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();


    Transformer transformer = transformerFactory.newTransformer();

    // pretty print
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);

    }
}


