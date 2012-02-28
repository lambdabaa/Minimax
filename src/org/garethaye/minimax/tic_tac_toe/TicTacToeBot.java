package org.garethaye.minimax.tic_tac_toe;

import java.util.LinkedList;
import java.util.List;

import org.apache.thrift.TException;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateAndMove;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.generated.TicTacToeGameState;
import org.garethaye.minimax.generated.TicTacToeMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToeBot implements Bot.Iface {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeBot.class.getName());
  
  private List<List<Integer>> board;
  private int activePlayer;
  private int inactivePlayer;
  
  public void init(GameState state) throws TException {
    if (!state.isSetTicTacToeGameState()) {
      throw new TException("TicTacToeBot received non-TicTacToe game state");
    }
    
    board = state.getTicTacToeGameState().getBoard();
    activePlayer = state.getTicTacToeGameState().getActivePlayer();
    inactivePlayer = state.getTicTacToeGameState().getInactivePlayer();
  }

  @Override
  public List<GameStateAndMove> getChildren(GameState state) throws TException {
    init(state);
    
    List<GameStateAndMove> list = new LinkedList<GameStateAndMove>();
    for (int i = 0; i < board.size(); i++) {
      List<Integer> row = board.get(i);
      for (int j = 0; j < row.size(); j++) {
        if (board.get(i).get(j) == 0) {
          List<List<Integer>> clone = TicTacToeUtils.clone(board);
          clone.get(i).set(j, activePlayer);
          list.add(new GameStateAndMove(
              new GameState(GameState._Fields.TIC_TAC_TOE_GAME_STATE, 
                  new TicTacToeGameState(inactivePlayer, activePlayer, clone)),
              new Move(Move._Fields.TIC_TAC_TOE_MOVE, new TicTacToeMove(i, j))));
        }
      }
    }
    
    return list;
  }

  @Override
  public int eval(GameState state, int playerId, int opponentId) throws TException {
    init(state);
    
    LOGGER.info("eval called");

    if (TicTacToeUtils.hasThreeInARow(board, playerId)) {
      LOGGER.info("Us three in a row");
      return Integer.MAX_VALUE;
    } else if (TicTacToeUtils.hasThreeInARow(board, opponentId)) {
      LOGGER.info("Them three in a row");
      return Integer.MIN_VALUE;
    } else {
      return TicTacToeUtils.getNumWins(board, playerId, opponentId)
          - TicTacToeUtils.getNumWins(board, opponentId, playerId);
    }
  }

  @Override
  public boolean explore(GameState state, int depth) throws TException {
    init(state);
    
    return !TicTacToeUtils.isFull(state.getTicTacToeGameState().getBoard())
        && !TicTacToeUtils.hasThreeInARow(board, activePlayer)
        && !TicTacToeUtils.hasThreeInARow(board, inactivePlayer);
  }
}
