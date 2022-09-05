import org.w3c.dom.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import java.io.*;


public class Highlighter
 {   
    
    //Code Refactored From https://mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/
    public static void main(String[] args) {
 
    File solutionDirectory = makeDirectory();

    File directoryPath = new File("Programming-Assignment-Data");

    File[] fileList = directoryPath.listFiles();

    highlightFiles(fileList, solutionDirectory);
 
}

//Code Taken From: https://www.educative.io/answers/how-to-create-a-directory-using-java
private static File makeDirectory(){
    File f = new File("Modified-Programming-Assignment-Data"); 

    // check if the directory can be created 
    // using the specified path name 
    if (f.mkdir() == true) { 
        System.out.println("Directory has been created successfully"); 
    } 
    else { 
        System.out.println("Directory cannot be created"); 
    } 

    return f;
}

private static void highlightFiles(File[] fileList, File soldir){
    for(int i = 0; i < fileList.length; i+= 2){

        File fileXML = fileList[i+1];
        
        File imageToModify = fileList[i];

        String fileName = fileXML.getName();

        String imageName = imageToModify.getName();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream("Programming-Assignment-Data\\" + fileName)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            //Get Nodes In Document
            NodeList listOfNodes = doc.getElementsByTagName("node");

            BufferedImage imgToHighlight = ImageIO.read(imageToModify);
            Graphics2D g = imgToHighlight.createGraphics();
            g.setPaint(new Color(251,247,25));
            g.setStroke(new BasicStroke(8));

            //Go Through Each Node and Get its Bounds
            for (int j = 0; j < listOfNodes.getLength(); j++) {
                Node node = listOfNodes.item(j);
                //Translate Bounds into integer representation
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Node selected_state = node.getAttributes().getNamedItem("bounds");
                    int[] CordinateInt = coordinate_to_int(selected_state);
                    //Add Highlighted Borders to Running Graphics Object
                    imageHighlight(CordinateInt, g);  
                }
            }

            //Putting Edited PNGs into New Directory, and copying over XML files
            String imgpathname = soldir.getCanonicalPath() + "\\" + imageName;
            ImageIO.write(imgToHighlight, "PNG", new File(imgpathname));
            String xmlpathname = soldir.getCanonicalPath() + "\\" + fileName;
            FileOutputStream dest = new FileOutputStream(xmlpathname);
            Path src = Paths.get("Programming-Assignment-Data\\" + fileName);
            Files.copy(src, dest);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


private static int[] coordinate_to_int(Node Coordinate){
    String[] Coordinates = Coordinate.getTextContent().split("\\[", 3);
    String CordS1[] = Coordinates[1].replace("]", "").split(",");
    String CordS2[] = Coordinates[2].replace("]", "").split(",");
    int[] Cord_to_ret = new int[4];
    Cord_to_ret[0] = Integer.parseInt(CordS1[0]);
    Cord_to_ret[1] = Integer.parseInt(CordS1[1]);
    Cord_to_ret[2] = Integer.parseInt(CordS2[0]);
    Cord_to_ret[3] = Integer.parseInt(CordS2[1]);

    return Cord_to_ret;
 }

 private static void imageHighlight(int[] Coordinate, Graphics2D g){
    try{
        //Bottom Left to Top Left
        g.drawLine(Coordinate[0], Coordinate[1], Coordinate[0], Coordinate[3]);
        //Bottom Left to Bottom Right
        g.drawLine(Coordinate[0], Coordinate[1], Coordinate[2], Coordinate[1]);
        //Top Left to Top Right
        g.drawLine(Coordinate[0], Coordinate[3], Coordinate[2], Coordinate[3]);
        //Bottom Right to Top Right
        g.drawLine(Coordinate[2], Coordinate[1], Coordinate[2], Coordinate[3]);
    }catch(Exception e){
        e.printStackTrace();
    }
 }

}


