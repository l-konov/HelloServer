/*
 * Объект с данными одной строки таблицы Users
 */

package ru.leonid.databaseService;

/**
 *
 * @author Лёня
 */
public class UsersDataSet {
    private long id;
    private String name;

    public UsersDataSet(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public long getId() {
        return id;
    }    
}
