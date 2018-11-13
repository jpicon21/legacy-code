import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLTester {

	public static void main(String[] args) {
		String fileName = (String) args[0];
		String xml = readFile(fileName);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(xml));
		    
		    builder.parse(is);
		    System.out.println("XML parsed succesfully");
		    System.out.println(is);
		    
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException saxEx) {
			saxEx.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFile(String filename) {
	    String content = null;
	    File file = new File(filename);
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader !=null){
	        		try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	}
	    }
	    return content;
	}

}
