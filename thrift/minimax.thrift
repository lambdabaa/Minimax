/**
 * @author  gareth.aye@gmail.com (Gareth Aye)
 */
namespace java org.garethaye.minimax.generated

include "bot.thrift"

service Minimax {
  /**
   * @param host    String hostname where a bot.Bot service is running
   * @param port    Integer port where a bot.Bot service is running
   * @param state   Current game state
   * @param depth   The maximum number of turns ahead the minimax algorithm should consider
   * @return        A move computed using minimax with alphabeta pruning
   */
  bot.Move getMove(1: string host, 2: i32 port, 3: bot.GameState state, 4: i32 depth);
}

