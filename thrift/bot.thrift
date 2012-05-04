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
namespace java org.garethaye.minimax.generated

include "tic_tac_toe.thrift"
include "connect_four.thrift"

union GameState {
  1: tic_tac_toe.TicTacToeGameState ticTacToeGameState;
  2: connect_four.ConnectFourGameState connectFourGameState;
}

union Move {
  1: tic_tac_toe.TicTacToeMove ticTacToeMove;
  2: connect_four.ConnectFourMove connectFourMove;
}

struct GameStateAndMove {
  1: GameState state;
  2: Move move;
}

service Bot {
  /**
   * @return        All pairs of moves and states that can follow directly (in one move) from state
   * @param state   The game state whose children we want
   */
  list<GameStateAndMove> actions(1: GameState state, 2: list<i32> playerList, 3: i32 player);

  /**
   * @return            An integer score for how good the state is for player
   * @param state       The game state to evaluate
   * @param player      Integer id of active player
   */
  map<i32, i32> eval(1: GameState state, 2: list<i32> playerList, 3: i32 player);

  /**
   * @return        Whether or not to search the tree whose root has state
   * @param state   The game state at the root of the tree
   */
  bool cutoffTest(1: GameState state, 2: i32 depth, 3: list<i32> playerList, 4: i32 player);

  /**
   * @return                  The integer id of the next player to act
   * @param state             The game state for the current turn
   * @param playerList        List of integer player ids
   * @param player            Integer id of active player
   */
   i32 nextPlayer(1: GameState state, 2: list<i32> playerList, 3: i32 player);
}

