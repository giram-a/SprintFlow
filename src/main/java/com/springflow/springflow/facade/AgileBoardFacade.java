package com.springflow.springflow.facade;

import com.springflow.springflow.adapter.ExternalUserAdapter;
import com.springflow.springflow.builder.BoardBuilder;
import com.springflow.springflow.command.CommandInvoker;
import com.springflow.springflow.command.MoveCardCommand;
import com.springflow.springflow.factory.CardFactory;
import com.springflow.springflow.model.Board;
import com.springflow.springflow.model.Card;
import com.springflow.springflow.model.Column;
import com.springflow.springflow.service.BoardService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AgileBoardFacade {
    private final BoardService boardService;
    private final CardFactory cardFactory;
    private final CommandInvoker invoker;
    private final ExternalUserAdapter userAdapter;

    public AgileBoardFacade(BoardService bs, CardFactory cf, CommandInvoker invoker, ExternalUserAdapter userAdapter){
        this.boardService = bs; this.cardFactory = cf;
        this.invoker = invoker;
        this.userAdapter = userAdapter;
    }

    public Board createBoard(String name, Board.BoardType type){
        var builder = new BoardBuilder()
                .setName(name)
                .setType(type)
                .setDescription("This is a Agile Board")
                .addColumn("To Do")
                .addColumn("In Progress")
                .addColumn("Done");
        Board b = builder.build();
        return boardService.createBoard(b);
    }

    public Card addCardToBacklog(UUID boardId, String type, String title, String desc){
        var opt = boardService.getBoard(boardId);
        if(opt.isEmpty()) throw new IllegalArgumentException("board not found");
        Card c = cardFactory.create(type, title, desc);
        opt.get().getBacklog().add(c);
        return c;
    }

    public void moveCard(UUID boardId, String cardId, int fromColIndex, int toColIndex){
        Board b = boardService.getBoard(boardId).orElseThrow(() -> new IllegalArgumentException("board not found"));
        Card card = findCardOnBoard(b, cardId);
        if(card == null) throw new IllegalArgumentException("card not found on board");

        Column from = fromColIndex >= 0 ? b.getColumns().get(fromColIndex) : null;
        Column to = toColIndex >= 0 ? b.getColumns().get(toColIndex) : null;

        // If from is null and backlog doesn't contain card, remove it from any column it's in
        if(from == null && !b.getBacklog().contains(card)){
            // try to find column containing it
            for(Column c : b.getColumns()){
                if(c.getCards().contains(card)){ // treat that column as source
                    from = c;
                    break;
                }
            }
        }

        // execute via command
        MoveCardCommand cmd = new MoveCardCommand(b, from, to, card);
        invoker.execute(cmd);
    }

    public void undoLast(){
        invoker.undoLast();
    }

    public void redoLast(){
        invoker.redoLast();
    }


    private Card findCardOnBoard(Board b, String cardId){
        // look in backlog
        for(Card c : b.getBacklog()){
            if(c.getId().toString().equals(cardId)) return c;
        }
        // look in columns
        for(Column col : b.getColumns()){
            for(Card c : col.getCards()){
                if(c.getId().toString().equals(cardId)) return c;
            }
        }
        return null;
    }


    public void assignUser(UUID boardId, String cardId, String userId){
        Board b = boardService.getBoard(boardId).orElseThrow(() -> new IllegalArgumentException("board not found"));
        Card card = findCardOnBoard(b, cardId);
        if(card == null) throw new IllegalArgumentException("card not found on board");

        // Use the Adapter to fetch external user data and convert it
        var user = userAdapter.getUser(userId);
        card.setAssignedUser(user);
        System.out.println("Assigned Card: " + card.getTitle() + " to adapted user: " + user.getName());
    }

    // *** USAGE FOR STATE PATTERN ***
    public void advanceCardState(UUID boardId, String cardId){
        Board b = boardService.getBoard(boardId).orElseThrow(() -> new IllegalArgumentException("board not found"));
        Card card = findCardOnBoard(b, cardId);
        if(card == null) throw new IllegalArgumentException("card not found on board");

        if(card.getState() != null){
            card.getState().next(card);
            System.out.println("Card: " + card.getTitle() + " state advanced to: " + card.getState().name());
        }
    }

}
