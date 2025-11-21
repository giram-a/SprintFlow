package com.sprintflow.sprintflow.strategy;

import com.sprintflow.sprintflow.model.Card;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardSorter {
    private CardSortingStrategy strategy;
    public void setStrategy(CardSortingStrategy s){ this.strategy = s; }
    public List<Card> sort(List<Card> cards){
        if(strategy==null) return cards;
        return strategy.sort(cards);
    }
}
