/**
 * @author  gareth.aye@gmail.com (Gareth Aye)
 */
namespace java org.garethaye.minimax.generated

/**
 * A struct containing a 2d array used to represent a TicTacToe board
 * along with integers representing the ids of the active and inactive players
 */
struct TicTacToeGameState {
  1: required i32 activePlayer;
  2: required i32 inactivePlayer;
  3: required list<list<i32>> board;
}

/**
 * A struct containing a TicTacToe move specified by integer row and col
 */
struct TicTacToeMove {
  1: required i32 row;
  2: required i32 col;
}

