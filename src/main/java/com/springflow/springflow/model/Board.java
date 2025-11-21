package com.springflow.springflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
    public enum BoardType { SCRUM, KANBAN }
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private BoardType type;
    private List<Column> columns = new ArrayList<>();
    private List<Card> backlog = new ArrayList<>();

    // getters/setters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BoardType getType() { return type; }
    public void setType(BoardType type) { this.type = type; }
    public List<Column> getColumns() { return columns; }
    public List<Card> getBacklog() { return backlog; }
}