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

