package org.garethaye.minimax.framework;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TNonblockingServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.garethaye.minimax.generated.Minimax;
import org.garethaye.minimax.generated.Minimax.Iface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimaxServer {
  private static final Logger LOGGER = LoggerFactory.getLogger(MinimaxServer.class.getName());
  
  private int port;
  private TServer server;
  
  public MinimaxServer(int port) {
    this.port = port;
  }

  /**
   * Starts a new nonblocking minimax server on specified port and serves forever
   */
  public void start() {
    try {
      server = new TNonblockingServer(
          new Args(new TNonblockingServerSocket(port))
              .processor(new Minimax.Processor<Iface>(new MinimaxImpl()))
              .protocolFactory(new TBinaryProtocol.Factory()));
      LOGGER.info(String.format("Starting Minimax server on port %d...", port));
      server.serve();
    } catch (TTransportException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    MinimaxServer server = new MinimaxServer(4200);
    server.start();
  }
}
