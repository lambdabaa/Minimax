package org.garethaye.minimax.tic_tac_toe;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.GameStateUnion;
import org.garethaye.minimax.generated.Minimax;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.generated.TicTacToeGameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class TicTacToeClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeClient.class.getName());
  
  public static List<List<Integer>> getBoard() {
    List<List<Integer>> board = new LinkedList<List<Integer>>();
    board.add(ImmutableList.of(0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0));
    return board;
  }
  
  public static void main(String[] args) throws TException {
    BasicConfigurator.configure();
    TTransport transport = new TFramedTransport(new TSocket("localhost", 4200));
    Minimax.Client client = new Minimax.Client(new TBinaryProtocol(transport));
    transport.open();
    Move move = client.getMove(
        "localhost", 
        4201, 
        new GameState(
            new GameStateUnion(
                GameStateUnion._Fields.TIC_TAC_TOE_GAME_STATE,
                new TicTacToeGameState(1, 2, getBoard())),
            1,
            2),
        7);
    LOGGER.info(move.toString());
    transport.close();
  }
}
