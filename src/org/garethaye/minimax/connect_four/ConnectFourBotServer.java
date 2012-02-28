package org.garethaye.minimax.connect_four;

import org.apache.log4j.BasicConfigurator;
import org.garethaye.minimax.framework.BotServer;

public class ConnectFourBotServer {
  public static void main(String[] args) {
    BasicConfigurator.configure();
    BotServer server = new BotServer(new ConnectFourBot(), 4201);
    server.start();
  }
}
