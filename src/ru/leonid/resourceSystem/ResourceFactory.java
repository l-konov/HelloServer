
package ru.leonid.resourceSystem;

import ru.leonid.base.Resource;
import ru.leonid.utils.ReadXMLFileSAX;


public class ResourceFactory {
    
    private static ResourceFactory instance;
    
    private ResourceFactory(){};
    
    public static ResourceFactory getInstance(){
        if(instance == null) instance = new ResourceFactory();
        return instance;
    }
    
    public Resource getResource(String path){
        Resource r = (Resource) ReadXMLFileSAX.readXML(path);
        return r;
    }
}
