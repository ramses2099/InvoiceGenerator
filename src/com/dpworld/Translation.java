package com.dpworld;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;

public class Translation {
    private File _file;
    private Dictionary<String, String> _dict;

    public Translation(TranslationType translationType) {
        this._dict = new Hashtable<>();
        try {
            if (translationType == TranslationType.ID) {
                this._file = new File(getClass().getResource("/data/tariffs.xml").toURI());
            } else if (translationType == TranslationType.DESCRIPTION) {
                this._file = new File(getClass().getResource("/data/tarrifs_descripcion.xml").toURI());
            }

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

    public String getValue(String key) {
        String rs = "";
        if (this._dict != null) {
            rs = this._dict.get(key);
        }
        //
        if(rs == null){
            rs = key;
        }
        return rs;
    }

}
