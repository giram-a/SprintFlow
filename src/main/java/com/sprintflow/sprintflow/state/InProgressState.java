package com.sprintflow.sprintflow.state;

import com.sprintflow.sprintflow.model.Card;

public class InProgressState implements CardState {
    public void next(Card card){
        card.setState(new DoneState());
    }
    public String name(){ return "IN_PROGRESS"; }
}
