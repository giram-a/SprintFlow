package com.springflow.springflow.service;

import org.springframework.stereotype.Service;
import com.springflow.springflow.model.Board;
import java.util.*;

@Service
public class BoardService {
    private final Map<UUID, Board> boards = new HashMap<>();
    public Board createBoard(Board board){
        boards.put(board.getId(), board);
        return board;
    }
    public Optional<Board> getBoard(UUID id){ return Optional.ofNullable(boards.get(id)); }
    public Collection<Board> listBoards(){ return boards.values(); }
}
