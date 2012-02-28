package org.garethaye.minimax.framework;

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

  @Override
  public Move getMove(String host, int port, GameState state, int depth) throws TException {
    TTransport transport = new TFramedTransport(new TSocket(host, port));
    Bot.Client client = new Bot.Client(new TBinaryProtocol(transport));
    transport.open();
    
    // TODO(garethaye): This is a hack. Fix the thrift defs.
    int playerId = 0, opponentId = 0;
    switch (state.getSetField()) {
    case TIC_TAC_TOE_GAME_STATE:
      playerId = state.getTicTacToeGameState().getActivePlayer();
      opponentId = state.getTicTacToeGameState().getInactivePlayer();
      break;
    case CONNECT_FOUR_GAME_STATE:
      playerId = state.getConnectFourGameState().getActivePlayer();
      opponentId = state.getConnectFourGameState().getInactivePlayer();
      break;
    }
    
    Tree tree = new Tree(
        client,
        state,
        depth,
        playerId,
        opponentId);
    tree.alphabeta(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    
    Move best = null;
    int bestValue = Integer.MIN_VALUE;
    for (Tree child : tree.getRoot().getChildren()) {
      int value = child.getValue();
      if (best == null || value > bestValue) {
        best = child.getMove();
        bestValue = value;
      }
    }
    
    transport.close();
    return best;
  }
  
}
