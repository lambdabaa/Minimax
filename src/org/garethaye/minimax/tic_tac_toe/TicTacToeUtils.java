package org.garethaye.minimax.tic_tac_toe;

import java.util.List;

import org.garethaye.minimax.framework.BotUtils;

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
        return BotUtils.allEqual(three, id);
      }
    });
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
