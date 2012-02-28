package org.garethaye.minimax.connect_four;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.garethaye.minimax.generated.ConnectFourGameState;
import org.garethaye.minimax.generated.GameState;
import org.garethaye.minimax.generated.Minimax;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.tic_tac_toe.TicTacToeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
;

public class ConnectFourClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeClient.class.getName());
  
  public static List<List<Integer>> getBoard() {
    List<List<Integer>> board = new LinkedList<List<Integer>>();
    board.add(ImmutableList.of(1, 1, 1, 2, 1, 2, 1));
    board.add(ImmutableList.of(2, 0, 2, 1, 2, 0, 2));
    board.add(ImmutableList.of(1, 0, 0, 2, 0, 0, 0));
    board.add(ImmutableList.of(2, 0, 0, 1, 0, 0, 0));
    board.add(ImmutableList.of(1, 0, 0, 2, 0, 0, 0));
    board.add(ImmutableList.of(2, 0, 0, 1, 0, 0, 0));
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
        new GameState(GameState._Fields.CONNECT_FOUR_GAME_STATE, 
            new ConnectFourGameState(1, 2, getBoard())), 
        5);
    LOGGER.info(move.toString());
    transport.close();
  }
}
