import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;

public class PosTester {

	public static void main(String[] args) {
		/*    THIS FILE SHOULD BE THE REQUEST XML BEING SENT FROM POS    */
		String action = (String) args[0];
		String url = (String) args[1];
		String fileName = "";
		String xml = "";
		//String encodedData = "";
		String USER_AGENT = "Mozilla/5.0";
		
		/*    SEND DATA    */
		try {
			System.out.println("Action: " + args[0]);
			System.out.println("URL: " + args[1]);
			
			URL u = new URL(url);
			
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setConnectTimeout( 20000 ); 
			
		    /*Begin Test
			if(action.equalsIgnoreCase("POST")) {
				System.out.println("Sending POST request");
				
				fileName = (String) args[2];
				xml = readFile(fileName);
				
				conn.setRequestMethod("POST");
				conn.setRequestProperty( "Content-Type", "text/xml" );
				conn.setRequestProperty( "Content-Length", String.valueOf(xml.length()));
				System.out.println(xml.length());
				
				OutputStream os = conn.getOutputStream();
				os.write(xml.getBytes());
				os.close();
				
			} else {
				System.out.println("Sending GET request");
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent", USER_AGENT);
			}
			//End of Test*/
			
			///*Begin s2s Messages Test
			if(action.equalsIgnoreCase("GET")) {
				System.out.println("Sending GET request");
				
				fileName = (String) args[2];
				xml = readFile(fileName);
				
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				System.out.println(xml.length());
				System.out.println(xml);
				
				//OutputStream os = conn.getOutputStream();
				//os.write(xml.getBytes());
				//os.close();
				
			} else {
				System.out.println("Sending POST request");
				conn.setRequestMethod("POST");
				conn.setRequestProperty("User-Agent", USER_AGENT);
			}
			//End of s2s Messages Test*/
			
			BufferedReader br;
			StringBuilder sb = new StringBuilder();
			String output;
			int code = conn.getResponseCode();

			System.out.println("Response Code: " + code);
			System.out.println("Response Msg: " + code);
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			int finalLength = 0;
			while ((output = br.readLine()) != null) {
				finalLength += output.length();
				sb.append(output);
			}
			
			System.out.println("Response:\n" + sb.toString());
			System.out.println(finalLength);
			
			if (200 <= code && code <= 299)
				parseResponse(sb.toString());
			else 
				System.out.println("An error occurred: " + sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void parseResponse(String resp) {
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(resp));
		    
		    builder.parse(is);
		    System.out.println("XML response parsed succesfully");
		    System.out.println(is);
		    
		} catch (Exception e) {
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
	    } catch (Exception e) {
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
