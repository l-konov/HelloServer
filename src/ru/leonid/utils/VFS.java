/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.utils;

import java.util.Iterator;

public interface VFS {
    
    boolean isExist(String path);
    
    boolean isDirectory(String path);
    
    String getAbsolutePath(String file);
    
    byte[] getBytes(String file);
    
    String getUtf8Text(String file);
    
    Iterator<String> getIterator(String startDir);
}
