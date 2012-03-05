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
  public Move getMove(String host, int port, GameState state, int depth) throws TException {
    TTransport transport = new TFramedTransport(new TSocket(host, port));
    Bot.Client client = new Bot.Client(new TBinaryProtocol(transport));
    transport.open();
    
    // Build the game tree, compute minimax search, and find the root's child with the best score
    GameTree tree = new GameTree(client, state, depth, state.getPlayerId(), state.getOpponentId());
    tree.alphabeta(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    Move best = tree.getBestMove();
    
    transport.close();
    return best;
  }
  
}
