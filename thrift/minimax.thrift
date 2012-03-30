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

include "bot.thrift"

struct MinimaxConfig {
  1: required i32 depth;
}

service Minimax {
  /**
   * @param host    String hostname where a bot.Bot service is running
   * @param port    Integer port where a bot.Bot service is running
   * @param state   Current game state
   * @param depth   The maximum number of turns ahead the minimax algorithm should consider
   * @return        A move computed using minimax with alphabeta pruning
   */
  bot.Move getMove(1: string host, 2: i32 port, 3: bot.GameState state, 4: MinimaxConfig config);
}

