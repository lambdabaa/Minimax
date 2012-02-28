package org.garethaye.minimax.connect_four;

import java.util.LinkedList;
import java.util.List;

import org.apache.thrift.TException;
import org.garethaye.minimax.framework.BotUtils;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.ConnectFourGameState;
import org.garethaye.minimax.generated.ConnectFourMove;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.Move;

public class ConnectFourBot implements Bot.Iface {
  private static final int HEIGHT = 6;
  private static final int WIDTH = 7;
  
  private List<List<Integer>> board;
  private int activePlayer;
  private int inactivePlayer;
  
  public void init(GameState state) throws TException {
    if (!state.isSetConnectFourGameState()) {
      throw new TException("ConnectFourBot received non-ConnectFour game state");
    }
    
    board = state.getConnectFourGameState().getBoard();
    activePlayer = state.getConnectFourGameState().getActivePlayer();
    inactivePlayer = state.getConnectFourGameState().getInactivePlayer();
  }

  @Override
  public List<GameStateAndMove> getChildren(GameState state) throws TException {
    List<GameStateAndMove> list = new LinkedList<GameStateAndMove>();
    for (int col = 0; col < WIDTH; col++) {
      if (board.get(HEIGHT - 1).get(col) == 0) {
        List<List<Integer>> clone = BotUtils.clone(board);
        ConnectFourUtils.drop(clone, col, activePlayer);
        list.add(new GameStateAndMove(
            new GameState(GameState._Fields.CONNECT_FOUR_GAME_STATE, 
                new ConnectFourGameState(inactivePlayer, activePlayer, clone)), 
            new Move(Move._Fields.CONNECT_FOUR_MOVE, new ConnectFourMove(col))));
      }
    }
    
    return list;
  }

  @Override
  public int eval(GameState state, int playerId, int opponentId)
      throws TException {
    init(state);
    
    if (ConnectFourUtils.hasFourInARow(board, playerId)) {
      return Integer.MIN_VALUE;
    } else if (ConnectFourUtils.hasFourInARow(board, opponentId)) {
      return Integer.MIN_VALUE;
    } else {
      return ConnectFourUtils.getNumWins(board, playerId, opponentId)
          - ConnectFourUtils.getNumWins(board, opponentId, playerId);
    }
  }

  @Override
  public boolean explore(GameState state, int depth) throws TException {
    init(state);
    
    return !BotUtils.isFull(board)
        && !ConnectFourUtils.hasFourInARow(board, activePlayer)
        && !ConnectFourUtils.hasFourInARow(board, inactivePlayer);
  }

}
