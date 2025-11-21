package com.springflow.springflow.builder;

import com.springflow.springflow.model.Board;
import com.springflow.springflow.model.Column;

public class BoardBuilder {
    private final Board board = new Board();
    public BoardBuilder setName(String name){
        board.setName(name);
        return this;
    }
    public BoardBuilder setDescription(String desc){
        board.setDescription(desc);
        return this;
    }
    public BoardBuilder setType(Board.BoardType type){
        board.setType(type);
        return this;
    }
    public BoardBuilder addColumn(String name){
        Column c = new Column(name);
        board.getColumns().add(c);
        return this;
    }
    public Board build(){
        return board;
    }
}
