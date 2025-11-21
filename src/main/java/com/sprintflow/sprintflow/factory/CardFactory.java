package com.sprintflow.sprintflow.factory;

import com.sprintflow.sprintflow.state.ToDoState;
import org.springframework.stereotype.Component;
import com.sprintflow.sprintflow.model.Card;

import java.time.Instant;

@Component
public class CardFactory {
    public Card create(String type, String title, String desc){
        Card c = new Card(title, desc);
        switch(type.toLowerCase()){
            case "bug":
                c.setPriority(Card.Priority.HIGH);
                c.setStoryPoints(1);
                break;
            case "story":
                c.setPriority(Card.Priority.MEDIUM);
                c.setStoryPoints(3);
                break;
            case "task":
            default:
                c.setPriority(Card.Priority.LOW);
                c.setStoryPoints(1);
        }
        c.setState(new ToDoState());
        c.setCreatedAt(Instant.now());
        return c;
    }
}
