package com.springflow.springflow.state;

import com.springflow.springflow.model.Card;

public class InProgressState implements CardState {
    public void next(Card card){
        card.setState(new DoneState());
    }
    public String name(){ return "IN_PROGRESS"; }
}
