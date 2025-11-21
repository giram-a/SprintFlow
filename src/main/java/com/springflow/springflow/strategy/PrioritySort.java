package com.springflow.springflow.strategy;

import com.springflow.springflow.model.Card;

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
