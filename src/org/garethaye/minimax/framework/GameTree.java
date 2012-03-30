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
      Collection<GameTree> children = new LinkedList<GameTree>();
      for (GameStateAndMove child : bot.getChildren(state)) {
        children.add(
            new GameTree(
                bot,
                child.getState(),
                child.getMove(),
                level + 1,
                maxLevel,
                playerId,
                opponentId));
      }
      
      root.setChildren(children);
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
  
  public Move getBestMove() throws TException {
    // Compute minimax values on tree
    alphabeta(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    
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
