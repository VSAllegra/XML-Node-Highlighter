import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import java.io.*;


public class Highlighter
 {   
    
    //Code Refactored From https://mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/
    public static void main(String[] args) {


    File directoryPath = new File("Programming-Assignment-Data");

    File[] fileList = directoryPath.listFiles();


    for(int i = 0; i < fileList.length; i+= 2){

        File fileXML = fileList[i+1];
        
        File imageToModify = fileList[i];

        String fileName = fileXML.getName();

        String imageName = imageToModify.getName();

        System.out.print("FILENAME: " + fileName + "\n");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream("Programming-Assignment-Data\\" + fileName)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            //Get Nodes In Document
            NodeList listOfNodes = doc.getElementsByTagName("node");

            BufferedImage imgToHighlight = ImageIO.read(imageToModify);
            Graphics2D g = imgToHighlight.createGraphics();
            g.setPaint(new Color(251,247,25));

            //Go Through Each Node and Change Value of Selected from False to True
            for (int j = 0; j < listOfNodes.getLength(); j++) {
                Node node = listOfNodes.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Node selected_state = node.getAttributes().getNamedItem("bounds");
                    int[] CordinateInt = coordinate_to_int(selected_state);
                    imageHighlight(CordinateInt, g);
                    ImageIO.write(imgToHighlight, "PNG", new File("modified"+imageName));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

private static int[] coordinate_to_int(Node Coordinate){
    String[] Coordinates = Coordinate.getTextContent().split("\\[", 3);
    String XCord[] = Coordinates[1].replace("]", "").split(",");
    String YCord[] = Coordinates[2].replace("]", "").split(",");
    int[] Cord_to_ret = new int[4];
    Cord_to_ret[0] = Integer.parseInt(XCord[0]);
    Cord_to_ret[1] = Integer.parseInt(XCord[1]);
    Cord_to_ret[2] = Integer.parseInt(YCord[0]);
    Cord_to_ret[3] = Integer.parseInt(YCord[1]);

    return Cord_to_ret;
 }

 private static void imageHighlight(int[] Coordinate, Graphics2D g){
    try{
        g.fillRect(Coordinate[0], Coordinate[2], Coordinate[1] - Coordinate[0], Coordinate[3] - Coordinate[2]);
    }catch(Exception e){
        e.printStackTrace();
    }

 }

}


