package com.springflow.springflow.state;

import com.springflow.springflow.model.Card;

public class DoneState  implements CardState {
    public void next(Card card){
        // terminal state
    }
    public String name(){ return "DONE"; }
}