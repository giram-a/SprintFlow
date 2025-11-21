package com.sprintflow.sprintflow.model;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class Column {
    private String id = UUID.randomUUID().toString();
    private String name;
    private List<Card> cards = new ArrayList<>();
    public Column() {}
    public Column(String name) { this.name = name; }
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Card> getCards() { return cards; }
}
