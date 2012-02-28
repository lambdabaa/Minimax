/**
 * @author  gareth.aye@gmail.com (Gareth Aye)
 */
namespace java org.garethaye.minimax.generated

include "tic_tac_toe.thrift"

union GameState {
  1: tic_tac_toe.TicTacToeGameState ticTacToeGameState;
}

union Move {
  1: tic_tac_toe.TicTacToeMove ticTacToeMove;
}

/**
 * A struct containing the game state and the most recent move made
 */
struct GameStateAndMove {
  1: GameState state;
  2: Move move;
}

service Bot {
  /**
   * @return        All pairs of moves and states that can follow directly (in one move) from state
   * @param state   The game state whose children we want
   */
  list<GameStateAndMove> getChildren(1: GameState state);

  /**
   * @return            An integer score for how good the state is for player
   * @param state       The game state to evaluate
   * @param playerId    Player performing the adversarial search
   * @param opponentId  Player not performing the adversarial search
   */
  i32 eval(1: GameState state, 2: i32 playerId, 3: i32 opponentId);

  /**
   * @return        Whether or not to search the tree whose root has state
   * @param state   The game state at the root of the tree
   * @param depth   The depth of the root of the tree
   */
  bool explore(1: GameState state, 2: i32 depth);
}

