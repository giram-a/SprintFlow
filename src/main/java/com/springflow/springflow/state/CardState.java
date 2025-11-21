package com.springflow.springflow.state;

import com.springflow.springflow.model.Card;

public interface CardState {
    void next(Card card);
    String name();
}
