package org.garethaye.minimax.framework;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.Minimax;
import org.garethaye.minimax.generated.Move;

public class MinimaxImpl implements Minimax.Iface {

  /**
   * @return move to make given @param state
   */
  @Override
  public Move getMove(String host, int port, GameState state, List<Integer> playerList, int player)
      throws TException {
    TTransport transport = new TFramedTransport(new TSocket(host, port));
    Bot.Client client = new Bot.Client(new TBinaryProtocol(transport));
    transport.open();
    
    // Build the game tree, compute minimax search, and find the root's child with the best score
    GameTree tree = new GameTree(client, state, playerList, player);
    Move move = playerList.size() == 2 ?
        getBestTwoPlayerMove(tree, client, playerList, player) :
        getBestMove(tree, client, playerList, player);
    
    transport.close();
    return move;
  }
  
  private Move getBestMove(GameTree tree, Bot.Iface bot, List<Integer> playerList, int player) throws TException {
    // Compute minimax values on tree
    tree.minimax(bot, playerList, player);
    
    Move move = null;
    int max = Integer.MIN_VALUE;
    
    for (GameTree child : tree.getRoot().getChildren()) {
      GameNode root = child.getRoot();
      int value = root.getPlayerToUtility().get(player);
      
      if (move == null || value > max) {
        move = root.getMove();
        max = value;
      }
    }
    
    return move;
  }
  
  private Move getBestTwoPlayerMove(GameTree tree, Bot.Iface bot, List<Integer> playerList, int player) throws TException {
    // Compute minimax values on tree
    tree.alphabeta(bot, playerList, player, playerList.get((playerList.indexOf(player) + 1) % 2), 
        Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    
    Move move = null;
    int max = Integer.MIN_VALUE;
    
    for (GameTree child : tree.getRoot().getChildren()) {
      GameNode root = child.getRoot();
      int value = root.getMinimaxValue();
      
      if (move == null || value > max) {
        move = root.getMove();
        max = value;
      }
    }
    
    return move;
  }
  
}
