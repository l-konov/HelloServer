/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julia
 */
public class VFSImpl implements VFS{

    private String root;
    
    public VFSImpl(String root){
        this.root = root;
    }
    
    private class FileIterator implements Iterator<String>{
        
        private Queue<File> filesQueue = new LinkedList<File>();
        
        public FileIterator(String path){
            filesQueue.add(new File(root + path));
        }
        
        public boolean hasNext() {
            return !filesQueue.isEmpty();
        }

        public String next() {
            File f = filesQueue.peek();
            if(f.isDirectory()){
                for(File subFile : f.listFiles())
                    filesQueue.add(subFile);
            }
            return filesQueue.poll().getAbsolutePath();
        }
        
    }
    
    public boolean isExist(String path) {
        File f = new File(root + path);
        return f.exists();
    }

    public boolean isDirectory(String path) {
        File f = new File(root + path);
        return f.isDirectory();
    }

    public String getAbsolutePath(String file) {
        File f = new File(root + file);
        return f.getAbsolutePath();    
    }

    public byte[] getBytes(String file) {
        File f = new File(root + file);
        byte[] b = null;
        try {
            FileInputStream fis = new FileInputStream(f);
            int len = fis.read(b);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public String getUtf8Text(String file) {
        File f = new File(root + file);
        char[] cbuf = null;
        int len = 0;
        try {
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            InputStreamReader isr = new InputStreamReader(dis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            len = br.read(cbuf);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(cbuf);
    }

    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }
    
}
