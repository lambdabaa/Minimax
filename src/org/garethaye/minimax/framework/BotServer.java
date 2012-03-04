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

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TNonblockingServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.garethaye.minimax.generated.Bot;
import org.garethaye.minimax.generated.Bot.Iface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotServer {
  private static final Logger LOGGER = LoggerFactory.getLogger(BotServer.class.getName());
  
  private Bot.Iface bot;
  private int port;
  private TServer server;
  
  public BotServer(Bot.Iface bot, int port) {
    this.bot = bot;
    this.port = port;
  }
  
  /**
   * This blocking call starts a server on supplied port with supplied bot
   */
  public void start() {
    try {
      server = new TNonblockingServer(
          new Args(new TNonblockingServerSocket(port))
              .processor(new Bot.Processor<Iface>(bot))
              .protocolFactory(new TBinaryProtocol.Factory()));
      LOGGER.info(String.format("Starting BotServer on port %d...", port));
      server.serve();
    } catch (TTransportException e) {
      e.printStackTrace();
    }
  }
}