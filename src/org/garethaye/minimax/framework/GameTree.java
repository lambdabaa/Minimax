package org.garethaye.minimax.framework;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.thrift.TException;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.Move;

public class GameTree {
  private Bot.Iface bot;
  private Move move;        // Most recent move
  private GameNode root;
  private int value;        // Assigned during minimax
  private int playerId;
  private int opponentId;
  
  public GameTree(Bot.Iface bot, GameState state, int maxLevel, int playerId, int opponentId)
      throws TException {
    this(bot, state, null, maxLevel, playerId, opponentId);
  }
  
  public GameTree(Bot.Iface bot, GameState state, Move move, int maxLevel, int playerId,
      int opponentId) throws TException {
    this(bot, state, move, 0, maxLevel, playerId, opponentId);
  }
  
  public GameTree(Bot.Iface bot, GameState state, Move move, int level, int maxLevel, 
      int playerId, int opponentId) throws TException {
    setBot(bot);
    setMove(move);
    setPlayerId(playerId);
    setOpponentId(opponentId);
    setRoot(new GameNode(state, level));
    if (level != maxLevel && bot.explore(state)) {
      root.setChildren(new LinkedList<GameTree>());
      for (GameStateAndMove option : bot.getChildren(state)) {
        root.getChildren().add(new GameTree(
            bot, 
            option.getState(), 
            option.getMove(), 
            level + 1, 
            maxLevel, 
            playerId, 
            opponentId));
      }
    }
  }
  
  public Bot.Iface getBot() {
    return bot;
  }

  public GameTree setBot(Bot.Iface bot) {
    this.bot = bot;
    return this;
  }
  
  public Move getMove() {
    return move;
  }

  public GameTree setMove(Move move) {
    this.move = move;
    return this;
  }

  public GameNode getRoot() {
    return root;
  }

  public GameTree setRoot(GameNode root) {
    this.root = root;
    return this;
  }
  
  public int getValue() {
    return value;
  }

  public GameTree setValue(int value) {
    this.value = value;
    return this;
  }
  
  public int getPlayerId() {
    return playerId;
  }

  public GameTree setPlayerId(int playerId) {
    this.playerId = playerId;
    return this;
  }

  public int getOpponentId() {
    return opponentId;
  }

  public GameTree setOpponentId(int opponentId) {
    this.opponentId = opponentId;
    return this;
  }
  
  public int alphabeta(int alpha, int beta, boolean maximize) throws TException {
    GameState state = root.getState();
    Collection<GameTree> children = root.getChildren();
    
    int value;
    if (children == null || !bot.explore(state)) {
      value = bot.eval(state);
    } else if (maximize) {
      for (GameTree child : children) {
        alpha = Math.max(alpha, child.alphabeta(alpha, beta, false));
        if (beta <= alpha) {
          break;
        }
      }
      
      value = alpha;
    } else {
      for (GameTree child : children) {
        beta = Math.min(beta, child.alphabeta(alpha, beta, true));
        if (beta <= alpha) {
          break;
        }
      }
      
      value = beta;
    }
    
    setValue(value);
    return value;
  }
  
  public Move getBestMove() {
    Move best = null;
    int bestValue = Integer.MIN_VALUE;
    for (GameTree child : root.getChildren()) {
      int value = child.getValue();
      if (best == null || value > bestValue) {
        best = child.getMove();
        bestValue = value;
      }
    }
    
    return best;
  }
}
