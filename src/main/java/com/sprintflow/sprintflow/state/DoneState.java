package com.sprintflow.sprintflow.state;

import com.sprintflow.sprintflow.model.Card;

public class DoneState  implements CardState {
    public void next(Card card){
        // terminal state
    }
    public String name(){ return "DONE"; }
}