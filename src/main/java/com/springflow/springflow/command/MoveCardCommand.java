package com.springflow.springflow.command;

import com.springflow.springflow.model.Board;
import com.springflow.springflow.model.Card;
import com.springflow.springflow.model.Column;

public class MoveCardCommand implements Command{
    private final Column from;
    private final Column to;
    private final Card card;
    private final Board board;

    public MoveCardCommand(Board board, Column from, Column to, Card card){
        this.from = from; this.to = to; this.card = card;
        this.board = board;
    }
    @Override
    public void execute(){
        if(from != null){
            from.getCards().remove(card);
        } else {
            board.getBacklog().remove(card);
        }

        // add to destination
        if(to != null){
            to.getCards().add(card);
        } else {
            board.getBacklog().add(card);
        }
    }
    @Override
    public void undo(){
        // reverse of execute
        if(to != null){
            to.getCards().remove(card);
        } else {
            board.getBacklog().remove(card);
        }

        if(from != null){
            from.getCards().add(card);
        } else {
            board.getBacklog().add(card);
        }
    }
}
