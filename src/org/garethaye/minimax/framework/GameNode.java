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

import org.garethaye.minimax.generated.GameState;

public class GameNode {
  private GameState state;
  private Collection<GameTree> children;
  private int level;
  
  public GameNode(GameState state, int level) {
    setState(state);
    setLevel(level);
  }
  
  public GameState getState() {
    return state;
  }
  
  public GameNode setState(GameState state) {
    this.state = state;
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

