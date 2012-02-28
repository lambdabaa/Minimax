package org.garethaye.minimax.framework;

import java.util.Collection;

import org.garethaye.minimax.generated.GameState;

public class Node {
  private GameState state;
  private Collection<Tree> children;
  private int level;
  
  public Node(GameState state, int level) {
    setState(state);
    setLevel(level);
  }
  
  public GameState getState() {
    return state;
  }
  
  public Node setState(GameState state) {
    this.state = state;
    return this;
  }

  public Collection<Tree> getChildren() {
    return children;
  }

  public Node setChildren(Collection<Tree> children) {
    this.children = children;
    return this;
  }

  public int getLevel() {
    return level;
  }

  public Node setLevel(int level) {
    this.level = level;
    return this;
  }
}

