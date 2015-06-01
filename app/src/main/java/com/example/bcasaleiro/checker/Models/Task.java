package com.example.bcasaleiro.checker.Models;

/**
 * Created by bcasaleiro on 01/06/15.
 */
public class Task {
    private String name;

    //TODO add deadlines

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
