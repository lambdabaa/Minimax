package org.garethaye.minimax.framework;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.thrift.TException;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.Move;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree {
  private static final Logger LOGGER = LoggerFactory.getLogger(Tree.class.getName());
  
  private Bot.Iface bot;
  private Move move;        // Most recent move
  private Node root;
  private int value;        // Assigned during minimax
  private int playerId;
  private int opponentId;
  
  public Tree(Bot.Iface bot, GameState state, int maxLevel, int playerId, int opponentId)
      throws TException {
    this(bot, state, null, maxLevel, playerId, opponentId);
  }
  
  public Tree(Bot.Iface bot, GameState state, Move move, int maxLevel, int playerId,
      int opponentId) throws TException {
    this(bot, state, move, 0, maxLevel, playerId, opponentId);
  }
  
  public Tree(Bot.Iface bot, GameState state, Move move, int level, int maxLevel, 
      int playerId, int opponentId) throws TException {
    setBot(bot);
    setMove(move);
    setRoot(new Node(state, level));
    
    if (level != maxLevel && bot.explore(state, level)) {
      root.setChildren(new LinkedList<Tree>());
      for (GameStateAndMove option : bot.getChildren(state)) {
        Tree child = new Tree(bot, option.getState(), option.getMove(), level + 1, maxLevel, playerId, opponentId);
        root.getChildren().add(child);
      }
    }
    
    setPlayerId(playerId);
    setOpponentId(opponentId);
  }
  
  public Bot.Iface getBot() {
    return bot;
  }

  public void setBot(Bot.Iface bot) {
    this.bot = bot;
  }
  
  public Move getMove() {
    return move;
  }

  public void setMove(Move move) {
    this.move = move;
  }

  public Node getRoot() {
    return root;
  }

  public Tree setRoot(Node root) {
    this.root = root;
    return this;
  }
  
  public int getValue() {
    return value;
  }

  public Tree setValue(int value) {
    this.value = value;
    return this;
  }
  
  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public int getOpponentId() {
    return opponentId;
  }

  public void setOpponentId(int opponentId) {
    this.opponentId = opponentId;
  }
  
  public int alphabeta(int alpha, int beta, boolean maximize) throws TException {
    GameState state = root.getState();
    int level = root.getLevel();
    Collection<Tree> children = root.getChildren();
    
    int value;
    if (children == null || !bot.explore(state, level)) {
      LOGGER.info("Level : " + Integer.toString(level));
      LOGGER.info("State : " + state.toString());
      value = bot.eval(state, playerId, opponentId);
      LOGGER.info("Value : " + value);
    } else if (maximize) {
      for (Tree child : children) {
        alpha = Math.max(alpha, child.alphabeta(alpha, beta, false));
        if (beta <= alpha) {
          break;
        }
      }
      
      value = alpha;
    } else {
      for (Tree child : children) {
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
}
