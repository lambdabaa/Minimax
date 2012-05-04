package org.garethaye.minimax.framework;

import java.util.List;

import org.apache.thrift.TException;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;

public abstract class TwoPlayerGameBot implements Bot.Iface {

  @Override
  public int nextPlayer(GameState state, List<Integer> playerList, int player)
      throws TException {
    throw new RuntimeException("Don't make calls to nextPlayer in a 2 player game!");
  }

}
