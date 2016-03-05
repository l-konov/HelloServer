/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.base;

import java.io.Serializable;

/**
 *
 * @author Julia
 */
public interface Resource extends Serializable{
    public int getPeriod();
    public void setPeriod(int period);    
}
