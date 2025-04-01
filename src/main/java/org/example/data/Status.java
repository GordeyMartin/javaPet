package org.example.data;

public enum Status {
    TODO("todo"), IN_PROGRESS("in progress"), DONE("done");

    public final String name;

    Status(String name) {
        this.name = name;
    }
}
