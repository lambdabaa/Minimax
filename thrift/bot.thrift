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

union GameStateUnion {
  1: tic_tac_toe.TicTacToeGameState ticTacToeGameState;
  2: connect_four.ConnectFourGameState connectFourGameState;
}

struct GameState {
  1: required GameStateUnion state;
  2: required i32 playerId;
  3: required i32 opponentId;
}

union Move {
  1: tic_tac_toe.TicTacToeMove ticTacToeMove;
  2: connect_four.ConnectFourMove connectFourMove;
}

/**
 * A struct containing the game state and the most recent move made
 */
struct GameStateAndMove {
  1: GameState state;
  2: Move move;
}

/**
 * A struct containing a game state and the probability with which it follows from its parent
 */
struct GameStateAndProbability {
  1: GameState state;
  2: double probability;
}

service Bot {
  /**
   * @return        All pairs of moves and states that can follow directly (in one move) from state
   * @param state   The game state whose children we want
   */
  list<GameStateAndMove> getChildrenAndMoves(1: GameState state);

  /**
   * @return        All moves that can follow directly (with one random event) from state with probabilities
   * @param state   The game state whose children we want
   */
  list<GameStateAndProbability> getChildrenAndProbabilities(1: GameState state);

  /**
   * @return            An integer score for how good the state is for player
   * @param state       The game state to evaluate
   */
  i32 eval(1: GameState state);

  /**
   * @return        Whether or not to search the tree whose root has state
   * @param state   The game state at the root of the tree
   */
  bool explore(1: GameState state);
}

