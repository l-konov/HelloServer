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
        String form = "<h1>Введите имя пользователя</h1>"
                + "<input name=\"name\">"
                + "<input type=\"submit\" value=\"Submit\">";
        return preparePage(form, -1);
    } 
    
    public String getAuthorizationPage(String name){
        String form = "<h1>Подождите.... Происходит авторизация</h1>"
                + "<input name=\"name\" value=\"" + name + "\" hidden>"
                + "<input type=\"submit\" value=\"Submit\">";
        return preparePage(form, -1);
    }  
    
    public String getStartingGamePage(int id, String name, boolean waiting){
        String form = "<h1>Вы успешно авторизованы.</h1>"
                + "<h2>Пользователь: " + name + "ID: " + id + "</h2>";
        if(waiting) form += "<h2>Ожидание других игрков....</h2>";
        form += "<input name=\"id\" value=\"" + id + "\" hidden>"
                + "<input type=\"submit\" value=\"Submit\">";
        return preparePage(form, -1);
    }
    
    public String getClickPage(int id1, String name1, int score1, int id2, String name2, int score2){
        String form = "<h1>Нажимайте на Кнопку как можно чаще</h1>"
                + "<table>"
                + "<tr>"
                + "<td>" + name1 + "</td><td>" + name2 + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>" + score1 + "</td><td>" + score2 + "</td>"
                + "</tr>"
                + "</table>"
                + "<input name=\"id\" value=\"" + id1 + "\" hidden>"
                + "<input type=\"submit\" value=\"+\">";
        return preparePage(form, -1);
    }
    
    public String getResultPage(int id1, String name1, int score1, int id2, String name2, int score2, String winnerName){
        //результаты и предложение начать новую игру
        String form = "<h1>Игра окончена</h1>"
                + "<h1>Победитель " + winnerName + "</h1>"
                + "<table>"
                + "<tr>"
                + "<td>" + name1 + "</td><td>" + name2 + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>" + score1 + "</td><td>" + score2 + "</td>"
                + "</tr>"
                + "</table>"
                + "<input name=\"id\" value=\"" + id1 + "\" hidden>"
                + "<input type=\"submit\" value=\"+\">";
        return preparePage(form, -1);
    }
    
    
    private String preparePage(String form, int refresh){
        String page = "<!DOCTYPE html>" 
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<title>" + title + "</title>";
        if(refresh >= 0)
            page += "<meta http-equiv=\"refresh\" content=\"1\">";
        page += "</head>"
                + "<body>"
                + "<form name=\"userForm\" action=\"/\" method=\"post\">";
        page += form;
        page += "</form>"
                + "</body>"
                + "</html>";
        return page;
    }
}
