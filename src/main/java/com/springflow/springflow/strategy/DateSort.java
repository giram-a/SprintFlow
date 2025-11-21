package com.springflow.springflow.strategy;

import com.springflow.springflow.model.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DateSort implements CardSortingStrategy {
    @Override
    public List<Card> sort(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getCreatedAt))
                .collect(Collectors.toList());
    }
}
