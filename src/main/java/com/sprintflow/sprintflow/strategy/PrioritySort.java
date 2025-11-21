package com.sprintflow.sprintflow.strategy;

import com.sprintflow.sprintflow.model.Card;

import java.util.*;
import java.util.stream.Collectors;

public class PrioritySort implements CardSortingStrategy {
    @Override
    public List<Card> sort(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getPriority).reversed())
                .collect(Collectors.toList());
    }
}
