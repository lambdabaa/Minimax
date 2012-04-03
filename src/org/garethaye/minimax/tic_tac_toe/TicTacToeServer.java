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

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.garethaye.minimax.framework.BotServer;
import org.garethaye.minimax.framework.BotUtils;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.GameStateAndProbability;
import org.garethaye.minimax.generated.GameStateUnion;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.generated.TicTacToeGameState;
import org.garethaye.minimax.generated.TicTacToeMove;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class TicTacToeServer implements Bot.Iface {
  private List<List<Integer>> board;
  private int activePlayerId;
  private int inactivePlayerId;
  
  private void init(GameState state) throws TException {
    if (!state.getState().isSetTicTacToeGameState()) {
      throw new TException("TicTacToeBot received non-TicTacToe game state");
    }
    
    board = state.getState().getTicTacToeGameState().getBoard();
    activePlayerId = state.getState().getTicTacToeGameState().getActivePlayer();
    inactivePlayerId = state.getState().getTicTacToeGameState().getInactivePlayer();
  }

  @Override
  public List<GameStateAndMove> getChildrenAndMoves(GameState state) throws TException {
    init(state);
    
    List<GameStateAndMove> list = new LinkedList<GameStateAndMove>();
    for (int i = 0; i < board.size(); i++) {
      List<Integer> row = board.get(i);
      for (int j = 0; j < row.size(); j++) {
        if (board.get(i).get(j) == 0) {
          List<List<Integer>> clone = BotUtils.clone(board);
          clone.get(i).set(j, activePlayerId);
          list.add(new GameStateAndMove(
              new GameState(
                  new GameStateUnion(
                      GameStateUnion._Fields.TIC_TAC_TOE_GAME_STATE,
                      new TicTacToeGameState(inactivePlayerId, activePlayerId, clone)),
                   state.getPlayerId(),
                   state.getOpponentId()),
              new Move(Move._Fields.TIC_TAC_TOE_MOVE, new TicTacToeMove(i, j))));
        }
      }
    }
    
    return list;
  }
  
  @Override
  public List<GameStateAndProbability> getChildrenAndProbabilities(GameState state) throws TException {
    return ImmutableList.of(new GameStateAndProbability().setState(state).setProbability(1.0));
  }

  @Override
  public int eval(GameState state) throws TException {
    init(state);

    if (hasThreeInARow(board, state.getPlayerId())) {
      return Integer.MAX_VALUE;
    } else if (hasThreeInARow(board, state.getOpponentId())) {
      return Integer.MIN_VALUE;
    } else {
      return getNumWins(board, state.getPlayerId(), state.getOpponentId())
          - getNumWins(board, state.getOpponentId(), state.getPlayerId());
    }
  }

  @Override
  public boolean explore(GameState state) throws TException {
    init(state);
    
    return (!BotUtils.isFull(board))
        && (!hasThreeInARow(board, activePlayerId))
        && (!hasThreeInARow(board, inactivePlayerId));
  }
  
  private static int getNumWins(List<List<Integer>> board, final int activePlayer,
      final int inactivePlayer) {
    int count = 0;
    for (List<Integer> triple : getAllTriples(board)) {
      if (triple.contains(activePlayer) && !triple.contains(inactivePlayer)) {
        count++;
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
  
  public static void main(String[] args) {
    BasicConfigurator.configure();
    BotServer server = new BotServer(new TicTacToeServer(), 4201);
    server.start();
  }
}
