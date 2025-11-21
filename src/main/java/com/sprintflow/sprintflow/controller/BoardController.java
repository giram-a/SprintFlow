package com.sprintflow.sprintflow.controller;

import com.sprintflow.sprintflow.command.CommandInvoker;
import com.sprintflow.sprintflow.command.MoveCardCommand;
import com.sprintflow.sprintflow.facade.AgileBoardFacade;
import com.sprintflow.sprintflow.model.Board;
import com.sprintflow.sprintflow.model.Card;
import com.sprintflow.sprintflow.model.Column;
import com.sprintflow.sprintflow.service.BoardService;
import com.sprintflow.sprintflow.strategy.CardSorter;
import com.sprintflow.sprintflow.strategy.PrioritySort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final AgileBoardFacade facade;
    private final BoardService boardService;
    private final CardSorter sorter;
    private final CommandInvoker invoker;
    public BoardController(AgileBoardFacade f, BoardService bs, CardSorter sorter, CommandInvoker invoker){
        this.facade = f; this.boardService = bs; this.sorter = sorter; this.invoker = invoker;
    }

    @PostMapping("/boards")
    public Board createBoard(@RequestParam String name){
        return facade.createBoard(name, Board.BoardType.KANBAN);
    }

    @PostMapping("/boards/{id}/cards")
    public Card addCard(@PathVariable UUID id,
                        @RequestParam String type,
                        @RequestParam String title,
                        @RequestParam String desc){
        return facade.addCardToBacklog(id, type, title, desc);
    }

    @PostMapping("/boards/{id}/move")
    public String moveCard(@PathVariable UUID id,
                           @RequestParam String cardId,
                           @RequestParam int fromCol,
                           @RequestParam int toCol){
        var b = boardService.getBoard(id).orElseThrow();
        Card card = b.getBacklog().stream()
                .filter(c -> c.getId().toString().equals(cardId))
                .findFirst().orElse(null);
        if(card==null) return "card not in backlog: try adding then moving via direct cols";
        Column from = b.getColumns().get(fromCol);
        Column to = b.getColumns().get(toCol);
        // execute command
        MoveCardCommand cmd = new MoveCardCommand(b, from, to, card);
        invoker.execute(cmd);
        return "moved";
    }

    @PostMapping("/undo")
    public String undo(){
        invoker.undoLast();
        return "undone";
    }

    @GetMapping("/boards")
    public Collection<Board> list(){ return boardService.listBoards(); }

    @GetMapping("/boards/{id}/sorted")
    public List<Card> getSorted(@PathVariable UUID id){
        var b = boardService.getBoard(id).orElseThrow();
        var cards = new ArrayList<>(b.getBacklog());
        sorter.setStrategy(new PrioritySort());
        return sorter.sort(cards);
    }


    @PostMapping("/redo")
    public String redo(){
        invoker.redoLast();
        return "redone";
    }

    // *** USAGE FOR STATE PATTERN ***
    @PostMapping("/boards/{id}/cards/{cardId}/advance-state")
    public String advanceState(@PathVariable UUID id, @PathVariable String cardId){
        facade.advanceCardState(id, cardId);
        return "state advanced";
    }

    // *** USAGE FOR ADAPTER PATTERN ***
    @PostMapping("/boards/{id}/cards/{cardId}/assign")
    public String assign(@PathVariable UUID id, @PathVariable String cardId, @RequestParam String userId){
        facade.assignUser(id, cardId, userId);
        return "assigned";
    }

}
