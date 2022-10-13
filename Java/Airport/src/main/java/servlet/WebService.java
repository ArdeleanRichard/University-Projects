package servlet;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class WebService {

    public static final String GET_URL = "http://new.earthtools.org/timezone/40.71417/-74.00639";

    public String getLocalTimeByLocation(String latitude, String longitude) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL("http://new.earthtools.org/timezone/" + latitude + "/" + longitude);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        // System.out.println(response.toString());
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(response.toString())));
        return doc.getElementsByTagName("localtime").item(0).getTextContent();
    }

    public String getOffsetByLocation(String latitude, String longitude) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL("http://new.earthtools.org/timezone/" + latitude + "/" + longitude);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        // System.out.println(response.toString());
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(response.toString())));
        return doc.getElementsByTagName("offset").item(0).getTextContent();

    }
}
