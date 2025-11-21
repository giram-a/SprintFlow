package com.springflow.springflow.command;

public interface Command {
    void execute();
    void undo();
}
