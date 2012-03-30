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
import org.garethaye.minimax.generated.GameStateUnion;
import org.garethaye.minimax.generated.Minimax;
import org.garethaye.minimax.generated.MinimaxConfig;
import org.garethaye.minimax.generated.Move;
import org.garethaye.minimax.tic_tac_toe.TicTacToeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ConnectFourClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeClient.class.getName());
  
  public static List<List<Integer>> getBoard() {
    List<List<Integer>> board = new LinkedList<List<Integer>>();
    board.add(ImmutableList.of(0, 0, 0, 0, 0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0, 0, 0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0, 0, 0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0, 2, 0, 0, 0));
    board.add(ImmutableList.of(0, 0, 0, 2, 0, 0, 0));
    board.add(ImmutableList.of(1, 1, 1, 2, 1, 0, 0));
    Lists.reverse(board);
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
                GameStateUnion._Fields.CONNECT_FOUR_GAME_STATE, 
                new ConnectFourGameState(1, 2, getBoard())),
            1,
            2),
        new MinimaxConfig().setDepth(6));
    LOGGER.info(move.toString());
    transport.close();
  }
}
