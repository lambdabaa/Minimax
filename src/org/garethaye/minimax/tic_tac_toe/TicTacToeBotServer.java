package org.garethaye.minimax.tic_tac_toe;

import org.apache.log4j.BasicConfigurator;
import org.garethaye.minimax.framework.BotServer;

public class TicTacToeBotServer {
  public static void main(String[] args) {
    BasicConfigurator.configure();
    BotServer server = new BotServer(new TicTacToeBot(), 4201);
    server.start();
  }
}
