/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid;

/**
 *
 * @author Julia
 */
public class PageGenerator {
    private String pageHtml;

    public PageGenerator(int id) {
        pageHtml = "<!DOCTYPE html>" 
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<!-- <meta http-equiv=\"refresh\" content=\"1\"> -->"
                + "<!-- script>"
                + "window.onLoad() = function() {" 
                + "  document.getElementById(\"userForm\").submit();" 
                + "};"
                + "</script -->"
                + "</head>"
                + "<body>"
                + "<form name=\"userForm\" action=\"/\" method=\"post\">"
                + "<h1>Hello User! Your Id is " + id +  "</h1>"
                + "<input name=\"id\" value=\"" + id + "\" hidden>"
                + "<input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>";
    }
    
    public String getPageHtml() {
        return pageHtml;
    }

    public void setPageHtml(String pageHtml) {
        this.pageHtml = pageHtml;
    }    
    
}
