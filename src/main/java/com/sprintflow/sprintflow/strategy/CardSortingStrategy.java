package com.sprintflow.sprintflow.strategy;

import com.sprintflow.sprintflow.model.Card;
import java.util.List;

public interface CardSortingStrategy {
    List<Card> sort(List<Card> cards);
}
