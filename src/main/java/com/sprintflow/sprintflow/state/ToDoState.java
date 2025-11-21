package com.sprintflow.sprintflow.state;

import com.sprintflow.sprintflow.model.Card;

public class ToDoState implements CardState {
    public void next(Card card){
        card.setState(new InProgressState());
    }
    public String name(){ return "TODO"; }
}