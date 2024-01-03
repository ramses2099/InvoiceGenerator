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

public class ConnectionString {

    private Dictionary<String, Credential> _dict;
    private File _file;

    public ConnectionString() {
        try {
            this._dict = new Hashtable<>();

            this._file = new File(getClass().getResource("/connection/connection_db.xml").toURI());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(this._file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("connectiondb");
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attr = node.getAttributes();
                        if (attr != null) {
                            String id = attr.getNamedItem("id").getNodeValue();
                            String url = attr.getNamedItem("url").getNodeValue();
                            String user = attr.getNamedItem("user").getNodeValue();
                            String password = attr.getNamedItem("password").getNodeValue();
                            //
                            this._dict.put(id, new Credential(url, user, password));

                        }

                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("ConnectionString : " + ex.getMessage());
        }
    }
    //
    public Credential getValue(DBConnectionType connectionType) {
        Credential credential = null;
        String key = "";
        if(connectionType == DBConnectionType.N4){
            key ="N4";
        }else if(connectionType == DBConnectionType.BILLING){
            key ="BILLING";
        }
        if (this._dict != null) {
            credential = this._dict.get(key);
        }
        return credential;
    }

}
