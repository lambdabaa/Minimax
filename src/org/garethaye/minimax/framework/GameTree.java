package org.garethaye.minimax.framework;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.Move;

public class GameTree {
  private GameNode root;
  
  public GameTree(Bot.Iface bot, GameState state, List<Integer> playerList, int player)
      throws TException {
    this(bot, state, null, 0, playerList, player);
  }
  
  public GameTree(Bot.Iface bot, GameState state, Move move, int level, List<Integer> playerList, int player)
      throws TException {
    root = new GameNode()
        .setState(state)
        .setMove(move)
        .setLevel(level);
    
    if (!bot.cutoffTest(state, level, playerList, player)) {
      Collection<GameTree> children = new LinkedList<GameTree>();
      for (GameStateAndMove action : bot.actions(state, playerList, player)) {
        children.add(new GameTree(
            bot, 
            action.getState(), 
            action.getMove(), 
            level + 1,
            playerList, 
            bot.nextPlayer(state, playerList, player)));
      }
      
      root.setChildren(children);
    }
  }
  
  public Map<Integer, Integer> minimax(Bot.Iface bot, List<Integer> playerList, int active)
      throws TException {
    Map<Integer, Integer> result = null;
    
    Collection<GameTree> children = root.getChildren();
    GameState state = root.getState();
    
    if (children == null) {
      result = bot.eval(state, playerList, active);
    } else {
      int max = Integer.MIN_VALUE;
      
      for (GameTree child : children) {
        Map<Integer, Integer> scores = 
            child.minimax(bot, playerList, bot.nextPlayer(state, playerList, active));
        
        int score = scores.get(active);
        if (score > max) {
          result = scores;
          max = score;
        }
      }
    }
    
    root.setPlayerToUtility(result);
    return result;
  }
  
  public int alphabeta(Bot.Iface bot, List<Integer> playerList, int active, int inactive,
      int alpha, int beta, boolean maximize) throws TException {
    int result;
    
    Collection<GameTree> children = root.getChildren();
    GameState state = root.getState();
    
    if (children == null) {
      result = bot.eval(state, playerList, active).get(maximize ? active : inactive);
    } else if (maximize) {
      for (GameTree child : children) {
        result = child.alphabeta(bot, playerList, inactive, active, alpha, beta, false);
        alpha = Math.max(alpha, result);
        if (beta <= alpha) {
          break;
        }
      }
      
      result = alpha;
    } else {
      for (GameTree child : children) {
        result = child.alphabeta(bot, playerList, inactive, active, alpha, beta, true);
        beta = Math.min(beta, result);
        if (beta <= alpha) {
          break;
        }
      }
        
      result = beta;
    }
    
    root.setMinimaxValue(result);
    return result;
  }
  
  public GameNode getRoot() {
    return root;
  }
}
