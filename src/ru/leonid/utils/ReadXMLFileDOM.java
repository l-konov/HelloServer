/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Julia
 */
public class ReadXMLFileDOM {
    public static Object readXML(String xmlFile){
        String CLASSNAME = "class";
        Object object = null;
        String element = null;
        
        try {
            File fXmlFile = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            Element clazz = doc.getDocumentElement(); // получаем путь к классу, чтобы создать его
            String className = clazz.getAttribute("type");
            object = ReflectionHelper.createInstance(className); // создаём объект класса
            element = "period";
            NodeList nl = doc.getElementsByTagName(element);
            Node period = nl.item(0);
            ReflectionHelper.setFieldValue(object, element, period.getNodeValue()); // заполняем поле в объекте класса 
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ReadXMLFileDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ReadXMLFileDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadXMLFileDOM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return object;
    }
}
