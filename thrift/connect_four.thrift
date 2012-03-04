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

/**
 * A struct containing a 2d array used to represent a Connect Four board
 * along with integers representing the ids of the active and inactive players
 */
struct ConnectFourGameState {
  1: required i32 activePlayer;
  2: required i32 inactivePlayer;
  3: required list<list<i32>> board;
}

/**
 * A struct containing a Connect Four move specified by an int col between 1 and 7
 */
struct ConnectFourMove {
  1: required i32 col;
}

