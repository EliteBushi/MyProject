import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Forecast {
    private String zipcode;
    private String urlString;
    private Document doc;

    public void setZipcode(String n) {
        zipcode = n;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setURL() {
        String urlString = "https://graphical.weather.gov/xml/sample_products/browser_interface/ndfdBrowserClientByDay.php?zipCodeList=" + zipcode + "&format=24+hourly&numDays=7";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("The URL is not valid.");
            System.out.println(e.getMessage());
        }
        this.urlString = urlString;
    }

    public void getDoc() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(urlString);
        doc.getDocumentElement().normalize();
        this.doc = doc;
    }

    public NodeList getValues() {

        NodeList nList = doc.getElementsByTagName("data");
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;

        NodeList eList = null;
        try {
            eList = eElement.getChildNodes();
        }
        catch (NullPointerException p) {
            System.out.println("No data found for that zip code");
            System.exit(0);
        }

        Node pNode = eList.item(11);
        Element tElement = (Element) pNode;

        NodeList tList = tElement.getChildNodes();
        Node tNode = tList.item(1);
        Element vElement = (Element) tNode;

        NodeList vList = vElement.getChildNodes();

        return vList;
    }

    public void displayResults(NodeList vList) {

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement((vList.item(1).getTextContent()));
        Vector columnNamesV = new Vector(Arrays.asList(columnNames));

        Vector<String> rowOne = new Vector<String>();
        Vector<Vector> rowData = new Vector<Vector>();

        for (int j = 3; j < vList.getLength(); j+=2) {
            rowOne.addElement(vList.item(j).getTextContent());
        }
        rowData.addElement(rowOne);


        JTable table = new JTable(columnNamesV, rowData);
        table.setShowGrid(false);
        JFrame results = new JFrame();
        results.setSize(300,400);
        results.add(new JScrollPane(table), BorderLayout.CENTER);
        results.setVisible(true);
        results.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
