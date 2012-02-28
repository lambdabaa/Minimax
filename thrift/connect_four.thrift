/**
 * @author  gareth.aye@gmail.com (Gareth Aye)
 */
namespace java org.garethaye.minimax.generated

/**
 * A struct containing a 2d array used to represent a Connect Four board
 * along with integers representing the ids of the active and inactive players
 */
struct ConnectFourGameState {
  1: required i32 activePlayer;
  2: required i32 inactivePlayer;
  3: required list<list<i32>> board;
}

/**
 * A struct containing a Connect Four move specified by an int col between 1 and 7
 */
struct ConnectFourMove {
  1: required i32 col;
}

