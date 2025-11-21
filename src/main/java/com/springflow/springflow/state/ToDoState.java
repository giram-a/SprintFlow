package com.springflow.springflow.state;

import com.springflow.springflow.model.Card;

public class ToDoState implements CardState {
    public void next(Card card){
        card.setState(new InProgressState());
    }
    public String name(){ return "TODO"; }
}