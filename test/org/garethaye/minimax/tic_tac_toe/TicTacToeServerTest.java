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

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.TicTacToeGameState;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class TicTacToeServerTest {
  TicTacToeServer server;
  
  @Before
  public void setUp() {
    server = new TicTacToeServer();
  }
  
  @Test
  public void testActions() {
    // TODO(garethaye)
  }
  
  @Test
  public void testEmptyEval() throws TException {
    Map<Integer, Integer> scores = server.eval(
        new GameState(
            GameState._Fields.TIC_TAC_TOE_GAME_STATE,
            new TicTacToeGameState(getEmptyBoard())),
        ImmutableList.of(1, 2),
        1);
    assertEquals(8, (int) scores.get(1));
    assertEquals(8, (int) scores.get(2));
  }
  
  @Test
  public void testWinEval() throws TException {
    Map<Integer, Integer> scores = server.eval(
        new GameState(
            GameState._Fields.TIC_TAC_TOE_GAME_STATE,
            new TicTacToeGameState(getWonBoard())),
        ImmutableList.of(1, 2),
        1);
    assertEquals(100, (int) scores.get(1));
    assertEquals(-100, (int) scores.get(2));
  }
  
  @Test
  public void testExplore() {
    // TODO(garethaye)
  }
  
  private List<List<Integer>> getEmptyBoard() {
    List<List<Integer>> board = new LinkedList<List<Integer>>();
    board.add(ImmutableList.of(0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0));
    return board;
  }
  
  private List<List<Integer>> getWonBoard() {
    List<List<Integer>> board = new LinkedList<List<Integer>>();
    board.add(ImmutableList.of(1, 0, 2));
    board.add(ImmutableList.of(1, 2, 2));
    board.add(ImmutableList.of(1, 0, 1));
    return board;
  }
}
