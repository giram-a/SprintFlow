package com.springflow.springflow.strategy;

import com.springflow.springflow.model.Card;
import java.util.List;

public interface CardSortingStrategy {
    List<Card> sort(List<Card> cards);
}
