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
package org.garethaye.minimax.tic_tac_toe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.garethaye.minimax.framework.BotServer;
import org.garethaye.minimax.framework.BotUtils;
import org.garethaye.minimax.framework.TwoPlayerGameBot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.generated.TicTacToeGameState;
import org.garethaye.minimax.generated.TicTacToeMove;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class TicTacToeServer extends TwoPlayerGameBot {
  private List<List<Integer>> board;
  
  private void init(GameState state) throws TException {
    if (!state.isSetTicTacToeGameState()) {
      throw new TException("TicTacToeBot received non-TicTacToe game state");
    }
    
    board = state.getTicTacToeGameState().getBoard();
  }

  @Override
  public List<GameStateAndMove> actions(GameState state, List<Integer> playerList, int player)
      throws TException {
    init(state);
    
    List<GameStateAndMove> list = new LinkedList<GameStateAndMove>();
    for (int i = 0; i < board.size(); i++) {
      List<Integer> row = board.get(i);
      
      for (int j = 0; j < row.size(); j++) {
        if (isEmpty(row.get(j))) {
          List<List<Integer>> clone = BotUtils.clone(board);
          clone.get(i).set(j, player);
          list.add(
              new GameStateAndMove(
                  new GameState(
                      GameState._Fields.TIC_TAC_TOE_GAME_STATE,
                      new TicTacToeGameState(clone)),
                  new Move(
                      Move._Fields.TIC_TAC_TOE_MOVE,
                      new TicTacToeMove(i, j))));
        }
      }
    }
    
    return list;
  }

  @Override
  public Map<Integer, Integer> eval(GameState state, List<Integer> playerList, int active)
      throws TException {
    init(state);
    int inactive = playerList.get((playerList.indexOf(active) + 1) % 2);
    
    Map<Integer, Integer> result = new HashMap<Integer, Integer>();
    if (hasThreeInARow(board, active)) {
      result.put(active, 100);
      result.put(inactive, -100);
    } else if (hasThreeInARow(board, inactive)) {
      result.put(inactive, 100);
      result.put(active, -100);
    } else {
      int activeWins = getNumWins(board, active);
      int inactiveWins = getNumWins(board, inactive);
      result.put(active, activeWins - inactiveWins);
      result.put(inactive, inactiveWins - activeWins);
    }
    
    return result;
  }

  @Override
  public boolean cutoffTest(GameState state, int depth, List<Integer> playerList, int active)
      throws TException {
    init(state);
    
    if ((depth > 6) || BotUtils.isFull(board)) {
      return true;
    }
    
    for (int player : playerList) {
      if (hasThreeInARow(board, player)) {
        return true;
      }
    }
    
    return false;
  }
  
  private static int getNumWins(List<List<Integer>> board, int player) {
    List<List<Integer>> triples = getAllTriples(board);
    int count = triples.size();
    
    for (List<Integer> triple : getAllTriples(board)) {
      for (Integer i : triple) {
        if ((i != 0) && (i != player)) {
          count--;
        }
      }
    }
    
    return count;
  }
  
  private static boolean hasThreeInARow(List<List<Integer>> board, final int id) {
    return Iterables.any(getAllTriples(board), new Predicate<List<Integer>>() {
      @Override
      public boolean apply(List<Integer> three) {
        return BotUtils.allEqual(three, id);
      }
    });
  }
  
  @Override
  public int nextPlayer(GameState state, List<Integer> playerList, int player)
      throws TException {
    return playerList.get((playerList.indexOf(player) + 1) % playerList.size());
  }
  
  private static List<List<Integer>> getAllTriples(List<List<Integer>> board) {
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
  
  private boolean isEmpty(int value) {
    return value == 0;
  }
  
  public static void main(String[] args) {
    BasicConfigurator.configure();
    BotServer server = new BotServer(new TicTacToeServer(), 4201);
    server.start();
  }
}
