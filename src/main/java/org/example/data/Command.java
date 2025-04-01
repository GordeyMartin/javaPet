package org.example.data;

public enum Command {
    ADD("add"), LIST("list"), EDIT("edit"), DELETE("delete"),
    FILTER("filter"), SORT("sort"), EXIT("exit");

    public final String name;

    Command(String name) {
        this.name = name;
    }

}
