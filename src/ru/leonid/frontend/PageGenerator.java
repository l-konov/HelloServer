/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.frontend;

/**
 *
 * @author Julia
 */
public class PageGenerator {
    private String title = "HelloServer";

    public PageGenerator() {
    }
    
//    public String getTextPage(String message, int id){
//        return "<!DOCTYPE html>" 
//                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
//                + "<head>"
//                + "<title>" + title + "</title>"                
//                + "<!-- <meta http-equiv=\"refresh\" content=\"1\"> -->"
//                + "<!-- script>"
//                + "window.onLoad() = function() {" 
//                + "  document.getElementById(\"userForm\").submit();" 
//                + "};"
//                + "</script -->"
//                + "</head>"
//                + "<body>"
//                + "<form name=\"userForm\" action=\"/\" method=\"post\">"
//                + "<h1>" + message +  "</h1>"
//                + "<input name=\"id\" value=\"" + id + "\" hidden>"
//                + "<input type=\"submit\" value=\"Submit\">"
//                + "</form>"
//                + "</body>";
//    }
//    
//    public String getTextPage(String message){
//        return "<!DOCTYPE html>" 
//                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
//                + "<head>"
//                + "<title>" + title + "</title>"                
//                + "<!-- <meta http-equiv=\"refresh\" content=\"1\"> -->"
//                + "<!-- script>"
//                + "window.onLoad() = function() {" 
//                + "  document.getElementById(\"userForm\").submit();" 
//                + "};"
//                + "</script -->"
//                + "</head>"
//                + "<body>"
//                + "<form name=\"userForm\" action=\"/\" method=\"post\">"
//                + "<h1>" + message + "</h1>"
//                + "<input type=\"submit\" value=\"Submit\">"
//                + "</form>"
//                + "</body>";
//    }  
    
    public String getInputNamePage(){
        return "<!DOCTYPE html>" 
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<title>" + title + "</title>"                
                + "<!-- <meta http-equiv=\"refresh\" content=\"1\"> -->"
                + "<!-- script>"
                + "window.onLoad() = function() {" 
                + "  document.getElementById(\"userForm\").submit();" 
                + "};"
                + "</script -->"
                + "</head>"
                + "<body>"
                + "<form name=\"userForm\" action=\"/\" method=\"post\">"
                + "<h1>Введите имя пользователя</h1>"
                + "<input name=\"name\">"
                + "<input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>";
    } 
    
    public String getAuthorizationPage(String name){
        return "<!DOCTYPE html>" 
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<title>" + title + "</title>"
                + "<!-- <meta http-equiv=\"refresh\" content=\"1\"> -->"
                + "<!-- script>"
                + "window.onLoad() = function() {" 
                + "  document.getElementById(\"userForm\").submit();" 
                + "};"
                + "</script -->"
                + "</head>"
                + "<body>"
                + "<form name=\"userForm\" action=\"/\" method=\"post\">"
                + "<h1>Подождите.... Происходит авторизация</h1>"
                + "<input name=\"name\" value=\"" + name + "\" hidden>"
                + "<input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>";
    }  
    
    public String getStartingGamePage(int id, String name, boolean waiting){
        return "";
    }
    
    public String getClickPage(int id1, String name1, int id2, String name2){
        return "";
    }
    
    public String getResultPage(int id1, String name1, int id2, String name2, int winnerId){
        //результаты и предложение начать новую игру
        return "";
    }
}
