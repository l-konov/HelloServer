/*
 * Объект с данными одной строки таблицы Users
 */

package ru.leonid.databaseService;

/**
 *
 * @author Лёня
 */
public class UsersDataSet {
    private int id;
    private String name;

    public UsersDataSet(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }    
}
