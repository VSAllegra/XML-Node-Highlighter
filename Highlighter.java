import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.IOException;


public class Highlighter
 {  

    // Code Refactored From:  https://stackoverflow.com/questions/5059224/which-is-the-best-library-for-xml-parsing-in-java 
    public static void parse(String filename) throws XMLStreamException, IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
            XMLStreamReader reader = xmlInFact.createXMLStreamReader(fis);
            while(reader.hasNext()) {
                reader.next(); // do something here
            }
        }
    }

    public static void main(String[] args){
        try{
        parse("com.dropbox.android.xml");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
   
}
