package org.garethaye.minimax.tic_tac_toe;

import java.util.LinkedList;
import java.util.List;

import org.apache.thrift.TException;
import org.garethaye.minimax.framework.BotUtils;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.GameStateUnion;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.generated.TicTacToeGameState;
import org.garethaye.minimax.generated.TicTacToeMove;

public class TicTacToeBot implements Bot.Iface {
  private List<List<Integer>> board;
  private int activePlayerId;
  private int inactivePlayerId;
  
  public void init(GameState state) throws TException {
    if (!state.getState().isSetTicTacToeGameState()) {
      throw new TException("TicTacToeBot received non-TicTacToe game state");
    }
    
    board = state.getState().getTicTacToeGameState().getBoard();
    activePlayerId = state.getState().getTicTacToeGameState().getActivePlayer();
    inactivePlayerId = state.getState().getTicTacToeGameState().getInactivePlayer();
  }

  @Override
  public List<GameStateAndMove> getChildren(GameState state) throws TException {
    init(state);
    
    List<GameStateAndMove> list = new LinkedList<GameStateAndMove>();
    for (int i = 0; i < board.size(); i++) {
      List<Integer> row = board.get(i);
      for (int j = 0; j < row.size(); j++) {
        if (board.get(i).get(j) == 0) {
          List<List<Integer>> clone = BotUtils.clone(board);
          clone.get(i).set(j, activePlayerId);
          list.add(new GameStateAndMove(
              new GameState(
                  new GameStateUnion(
                      GameStateUnion._Fields.TIC_TAC_TOE_GAME_STATE,
                      new TicTacToeGameState(inactivePlayerId, activePlayerId, clone)),
                   state.getPlayerId(),
                   state.getOpponentId()),
              new Move(Move._Fields.TIC_TAC_TOE_MOVE, new TicTacToeMove(i, j))));
        }
      }
    }
    
    return list;
  }

  @Override
  public int eval(GameState state) throws TException {
    init(state);

    if (TicTacToeUtils.hasThreeInARow(board, state.getPlayerId())) {
      return Integer.MAX_VALUE;
    } else if (TicTacToeUtils.hasThreeInARow(board, state.getOpponentId())) {
      return Integer.MIN_VALUE;
    } else {
      return TicTacToeUtils.getNumWins(board, state.getPlayerId(), state.getOpponentId())
          - TicTacToeUtils.getNumWins(board, state.getOpponentId(), state.getPlayerId());
    }
  }

  @Override
  public boolean explore(GameState state) throws TException {
    init(state);
    
    return (!BotUtils.isFull(board))
        && (!TicTacToeUtils.hasThreeInARow(board, activePlayerId))
        && (!TicTacToeUtils.hasThreeInARow(board, inactivePlayerId));
  }
}
