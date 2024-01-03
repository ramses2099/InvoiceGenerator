package com.dpworld;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;

public class Tariff {

    File _file;
    Dictionary<String, String> _dict;

    public Tariff() {
        try {
            this._dict = new Hashtable<>();
            this._file = new File(getClass().getResource("/data/tariffs.xml").toURI());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(this._file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("row");
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attr = node.getAttributes();
                        if (attr != null) {
                            String key = attr.getNamedItem("name").getNodeValue();
                            String value = attr.getNamedItem("value").getNodeValue();
                            this._dict.put(key, value);
                        }

                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Dictionary<String, String> get_dict() {
        return _dict;
    }

    public String getValue(String key){
        String rs = "";
        if(this._dict != null) {
            rs = this._dict.get(key);
        }
        return rs;
    }

}
