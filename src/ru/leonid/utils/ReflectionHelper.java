/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julia
 */
public class ReflectionHelper {
    public static Object createInstance(String className){
        try{
            return Class.forName(className).newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void setFieldValue(Object object, String fieldName, String value){
        Field f = null;
        try {
            f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            if(f.getType().equals(String.class))
                f.set(object, value);
            else if(f.getType().equals(int.class))
                f.set(object, Integer.decode(value));
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(f != null) 
                f.setAccessible(false);
        }
        
    }
}
