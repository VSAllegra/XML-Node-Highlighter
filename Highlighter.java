import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    }catch(Exception e){
        e.printStackTrace();
    }
    }
}


