package com.springflow.springflow.model;

import java.time.Instant;
import java.util.UUID;
import com.springflow.springflow.state.CardState;

public class Card {
    public enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private Priority priority = Priority.MEDIUM;
    private int storyPoints;
    private Instant createdAt = Instant.now();
    // State (State pattern)
    private CardState state;
    public Card() {}
    public Card(String title, String description){
        this.title = title; this.description = description;
    }
    // getters/setters
    public UUID getId(){ return id; }
    public String getTitle(){ return title; }
    public void setTitle(String t){ this.title = t; }
    public String getDescription(){ return description; }
    public void setDescription(String d){ this.description = d; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public int getStoryPoints(){ return storyPoints; }
    public void setStoryPoints(int s){ this.storyPoints = s; }
    public CardState getState(){ return state; }
    public void setState(CardState s){ this.state = s; }
    public Instant getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Instant createdAt){ this.createdAt = createdAt; }
    public String getStateName() {
        // Returns the name from the current state object, handling null if the card
        // was somehow created without the Factory (though the Factory should prevent this).
        return state != null ? state.name() : "UNINITIALIZED";
    }
}
