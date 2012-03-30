/**
 * Copyright 2012 Gareth G. Aye (gareth.aye@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.garethaye.minimax.connect_four;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.garethaye.minimax.framework.BotServer;
import org.garethaye.minimax.framework.BotUtils;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.ConnectFourGameState;
import org.garethaye.minimax.generated.ConnectFourMove;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.GameStateUnion;
import org.garethaye.minimax.generated.Move;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class ConnectFourServer implements Bot.Iface {
  private static final int HEIGHT = 6;
  private static final int WIDTH = 7;
  
  private List<List<Integer>> board;
  private int activePlayer;
  private int inactivePlayer;
  
  private void init(GameState state) throws TException {
    if (!state.getState().isSetConnectFourGameState()) {
      throw new TException("ConnectFourBot received non-ConnectFour game state");
    }
    
    board = state.getState().getConnectFourGameState().getBoard();
    activePlayer = state.getState().getConnectFourGameState().getActivePlayer();
    inactivePlayer = state.getState().getConnectFourGameState().getInactivePlayer();
  }

  @Override
  public List<GameStateAndMove> getChildren(GameState state) throws TException {
    List<GameStateAndMove> list = new LinkedList<GameStateAndMove>();
    for (int col = 0; col < WIDTH; col++) {
      if (board.get(HEIGHT - 1).get(col) == 0) {
        List<List<Integer>> clone = BotUtils.clone(board);
        drop(clone, col, activePlayer);
        list.add(
            new GameStateAndMove(
                new GameState(
                    new GameStateUnion(
                        GameStateUnion._Fields.CONNECT_FOUR_GAME_STATE,
                        new ConnectFourGameState(inactivePlayer, activePlayer, clone)), 
                    state.getPlayerId(),
                    state.getOpponentId()),
                new Move(Move._Fields.CONNECT_FOUR_MOVE, new ConnectFourMove(col))));
      }
    }
    
    return list;
  }

  @Override
  public int eval(GameState state) throws TException {
    init(state);
    
    if (hasFourInARow(board, state.getPlayerId())) {
      return Integer.MAX_VALUE;
    } else if (hasFourInARow(board, state.getOpponentId())) {
      return Integer.MIN_VALUE;
    } else {
      return getNumWins(board, state.getPlayerId(), state.getOpponentId())
          - getNumWins(board, state.getOpponentId(), state.getPlayerId());
    }
  }

  @Override
  public boolean explore(GameState state) throws TException {
    init(state);
    
    return !BotUtils.isFull(board)
        && !hasFourInARow(board, activePlayer)
        && !hasFourInARow(board, inactivePlayer);
  }
  
  private static void drop(List<List<Integer>> board, int col, int playerId) {
    for (int row = 0; row < HEIGHT; row++) {
      if (board.get(row).get(col) == 0) {
        board.get(row).set(col, playerId);
        return;
      }
    }
    
    throw new RuntimeException("Drop unsuccessful... column was full!");
  }
  
  private static int getNumWins(List<List<Integer>> board, final int activePlayer,
      final int inactivePlayer) {
    int count = 0;
    for (List<Integer> quadruple : getAllQuadruples(board)) {
      if (quadruple.contains(activePlayer) && !quadruple.contains(inactivePlayer)) {
        count++;
      }
    }
    
    return count;
  }
  
  private static boolean hasFourInARow(List<List<Integer>> board, final int id) {
    return Iterables.any(getAllQuadruples(board), new Predicate<List<Integer>>() {
      @Override
      public boolean apply(List<Integer> three) {
        return BotUtils.allEqual(three, id);
      }
    });
  }
  
  private static List<List<Integer>> getAllQuadruples(List<List<Integer>> board) {
    List<List<Integer>> quadruples = new LinkedList<List<Integer>>();
    
    // Rows
    for (int i = 0; i < HEIGHT; i++) {
      for (int left = 0; left < WIDTH - 4; left++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(i).get(left + disp));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    // Cols
    for (int j = 0; j < WIDTH; j++) {
      for (int bottom = 0; bottom < HEIGHT - 4; bottom++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(bottom + disp).get(j));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    // Up diagonal
    for (int left = 0; left < WIDTH - 4; left++) {
      for (int bottom = 0; bottom < HEIGHT - 4; bottom++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(bottom + disp).get(left + disp));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    // Down diagonal
    for (int right = WIDTH - 1; right >= 3; right--) {
      for (int bottom = 0; bottom < HEIGHT - 4; bottom++) {
        List<Integer> quadruple = new LinkedList<Integer>();
        for (int disp = 0; disp < 4; disp++) {
          quadruple.add(board.get(bottom + disp).get(right - disp));
        }
        
        quadruples.add(quadruple);
      }
    }
    
    return quadruples;
  }
  
  public static void main(String[] args) {
    BasicConfigurator.configure();
    BotServer server = new BotServer(new ConnectFourServer(), 4201);
    server.start();
  }
}
