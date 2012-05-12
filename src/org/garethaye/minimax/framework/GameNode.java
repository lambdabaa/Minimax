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

