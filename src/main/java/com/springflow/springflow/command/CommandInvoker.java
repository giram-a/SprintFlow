package com.springflow.springflow.command;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class CommandInvoker {
    private final Deque<Command> history = new ArrayDeque<>();
    private final Deque<Command> undone = new ArrayDeque<>();

    public void execute(Command c){
        c.execute();
        history.push(c);
        undone.clear();
    }
    public void undoLast(){
        if(history.isEmpty()) return;
        Command c = history.pop();
        c.undo();
        undone.push(c);
    }

    public void redoLast(){
        if(undone.isEmpty()) return;
        Command c = undone.pop();
        c.execute();
        history.push(c);
    }
}
