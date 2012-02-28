package org.garethaye.minimax.connect_four;

import java.util.LinkedList;
import java.util.List;

import org.garethaye.minimax.framework.BotUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class ConnectFourUtils {
  private static final int HEIGHT = 6;
  private static final int WIDTH = 7;
  
  public static void drop(List<List<Integer>> board, int col, int playerId) {
    for (int row = 0; row < HEIGHT; row++) {
      if (board.get(row).get(col) == 0) {
        board.get(row).set(col, playerId);
        break;
      }
    }
  }
  
  public static int getNumWins(List<List<Integer>> board, final int activePlayer,
      final int inactivePlayer) {
    int count = 0;
    for (List<Integer> quadruple : getAllQuadruples(board)) {
      if (quadruple.contains(activePlayer) && !quadruple.contains(inactivePlayer)) {
        count++;
      }
    }
    
    return count;
  }
  
  public static boolean hasFourInARow(List<List<Integer>> board, final int id) {
    return Iterables.any(getAllQuadruples(board), new Predicate<List<Integer>>() {
      @Override
      public boolean apply(List<Integer> three) {
        return BotUtils.allEqual(three, id);
      }
    });
  }
  
  public static List<List<Integer>> getAllQuadruples(List<List<Integer>> board) {
    List<List<Integer>> quadruples = new LinkedList<List<Integer>>();
    
    // Rows
    for (int i = 0; i < HEIGHT; i++) {
      for (int left = 0; left < WIDTH - 4 - 1; left++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(i).get(left + disp));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    // Cols
    for (int j = 0; j < WIDTH; j++) {
      for (int bottom = 0; bottom < HEIGHT - 4 - 1; bottom++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(bottom + disp).get(j));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    // Up diagonal
    for (int left = 0; left < WIDTH - 4 - 1; left++) {
      for (int bottom = 0; bottom < HEIGHT - 4 - 1; bottom++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(bottom + disp).get(left + disp));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    // Down diagonal
    for (int right = WIDTH - 1; right >= 4; right--) {
      for (int bottom = 0; bottom < HEIGHT - 4 - 1; bottom++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(bottom + disp).get(right - disp));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    return quadruples;
  }
}
