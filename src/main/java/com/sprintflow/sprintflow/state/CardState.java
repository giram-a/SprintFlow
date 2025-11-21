package com.sprintflow.sprintflow.state;

import com.sprintflow.sprintflow.model.Card;

public interface CardState {
    void next(Card card);
    String name();
}
