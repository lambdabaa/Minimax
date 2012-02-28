package org.garethaye.minimax.tic_tac_toe;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class TicTacToeUtils {
  public static int getNumWins(List<List<Integer>> board, final int activePlayer,
      final int inactivePlayer) {
    int count = 0;
    for (List<Integer> triple : getAllTriples(board)) {
      if (triple.contains(activePlayer) && !triple.contains(inactivePlayer)) {
        count++;
      }
    }
    
    return count;
  }
  
  public static boolean hasThreeInARow(List<List<Integer>> board, final int id) {
    return Iterables.any(getAllTriples(board), new Predicate<List<Integer>>() {
      @Override
      public boolean apply(List<Integer> three) {
        return allEqual(three, id);
      }
    });
  }
  
  public static boolean isFull(List<List<Integer>> board) {
    return !Iterables.any(board, new Predicate<List<Integer>>() {
      @Override
      public boolean apply(List<Integer> row) {
        return row.contains(0);
      }
    });
  }
  
  public static boolean allEqual(List<Integer> list, final int value) {
    return Iterables.all(list, new Predicate<Integer>() {
      @Override
      public boolean apply(Integer elt) {
        return value == elt;
      }
    });
  }
  
  public static List<List<Integer>> clone(List<List<Integer>> board) {
    List<List<Integer>> list = new LinkedList<List<Integer>>();
    for (List<Integer> row : board) {
      LinkedList<Integer> rowClone = new LinkedList<Integer>();
      rowClone.addAll(row);
      list.add(rowClone);
    }
    
    return list;
  }
  
  public static List<List<Integer>> getAllTriples(List<List<Integer>> board) {
    return ImmutableList.of(
        // Rows
        board.get(0),
        board.get(1),
        board.get(2),
        
        // Columns
        ImmutableList.of(board.get(0).get(0), board.get(1).get(0), board.get(2).get(0)),
        ImmutableList.of(board.get(0).get(1), board.get(1).get(1), board.get(2).get(1)),
        ImmutableList.of(board.get(0).get(2), board.get(1).get(2), board.get(2).get(2)),
        
        // Diagonals
        ImmutableList.of(board.get(0).get(0), board.get(1).get(1), board.get(2).get(2)),
        ImmutableList.of(board.get(0).get(2), board.get(1).get(1), board.get(2).get(0)));
  }
}
