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

package org.garethaye.minimax.framework;

import java.util.Collection;
import java.util.Map;

import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.Move;

public class GameNode {
  private GameState state;
  private Move move;
  private Map<Integer, Integer> playerToUtility;
  private int minimaxValue;
  private Collection<GameTree> children;
  private int level;
  
  public GameState getState() {
    return state;
  }
  
  public GameNode setState(GameState state) {
    this.state = state;
    return this;
  }

  public Move getMove() {
    return move;
  }

  public GameNode setMove(Move move) {
    this.move = move;
    return this;
  }

  public Map<Integer, Integer> getPlayerToUtility() {
    return playerToUtility;
  }

  public GameNode setPlayerToUtility(Map<Integer, Integer> playerToUtility) {
    this.playerToUtility = playerToUtility;
    return this;
  }

  public int getMinimaxValue() {
    return minimaxValue;
  }

  public GameNode setMinimaxValue(int minimaxValue) {
    this.minimaxValue = minimaxValue;
    return this;
  }

  public Collection<GameTree> getChildren() {
    return children;
  }

  public GameNode setChildren(Collection<GameTree> children) {
    this.children = children;
    return this;
  }

  public int getLevel() {
    return level;
  }

  public GameNode setLevel(int level) {
    this.level = level;
    return this;
  }
}

