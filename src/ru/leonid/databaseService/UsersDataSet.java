/*
 * Объект с данными одной строки таблицы Users
 */

package ru.leonid.databaseService;

import javax.persistence.*;

/**
 *
 * @author Лёня
 */
@Entity
@Table(name = "users")
public class UsersDataSet {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name")
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
