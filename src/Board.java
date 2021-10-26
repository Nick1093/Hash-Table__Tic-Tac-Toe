import java.util.Arrays;

/**
 * this class stores information about the tiles placed on the game-board and implements
 * support methods needed by the algorithm PlayGame.java 
 * @author nicklam
 *
 */
public class Board implements BoardADT{
    
	// this stores the game-board 
    private char[][] theBoard;

    private int size;

    private int empty_positions;

    /**
     * this is the constructor for the class
     * @param board_size specifies the size of the game-board
     * @param empty_positions number of positions that must remain empty
     * @param max_levels specifies the playing quality of the program 
     */
    public Board (int board_size, int empty_positions, int max_levels) {

    	// initializing instance variables of this class
        this.size = board_size;
        this.empty_positions = empty_positions;
        
        // initializing the game board of nxn where n is the specified size
        theBoard = new char[size][size];

        // theBoard must store 'e' at all positions when initialized by the constructor
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                theBoard[row][col] = 'e';
            }
        }
    }

    /**
     * makes the empty dictionary of selected size
     */
    public Dictionary makeDictionary() {
        Dictionary table = new Dictionary(3797);
        return table;
    }

    /**
     * stores symbol in the input row and column
     */
	public void saveTile(int i, int j, char symbol) {
        theBoard[i][j] = symbol;
    }

	/**
	 * returns true if the game layout is a draw assuming the player must perform the next
	 * move uses tiles of the type specified by player
	 * 
	 * @param player this is the specified tile type 
	 * @param minEmpty this is the minimum amount of empty tiles allowed 
	 */
	public boolean isDraw(char player, int minEmpty) {
		
		// 1. the simple scenario is when minEmpty is 0
		if (minEmpty == 0) {
			
			// count empty tiles
			int countE = 0;
			for (int row = 0; row < size; row++) {
	            for (int col = 0; col < size; col++) {
	                if (theBoard[row][col] == 'e') {
	                    countE++;
	                }
	            }
			}
			
			// once we reach the tiles, the game ends
			if (countE == 0) {
				return true;
			}
		}
		
		// 2. complex case when we have minEmpty > 0
		
		// first we will see if the game is in a drawable state - we have minEmpty currently
		int empties = 0;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(theBoard[i][j] == 'e') {
					empties++;
				}
			}
		}

		// char[][] empty = new char[size][size];
		int hasMove = 0;
		
		
		// we will loop through the multidimensional array and check all possibilities for empty tiles
		if (empty_positions > 0 && empties == minEmpty) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					
					// if we found an empty tile
					if (theBoard[i][j] == 'e') {
						
						// we will check every tile around the empty one to see if there are any possible moves
						// if out of bounds, catch block for IndexOutofBoundsException
						try {
							if (hasNorth(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasEast(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasSouth(player, i, j)) {
								hasMove++;
							}
						} catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasWest(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasNorthEast(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasEastSouth(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasSouthWest(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						try {
							if (hasWestNorth(player, i, j)) {
								hasMove++;
							}
						} 
						catch (IndexOutofBoundsException e) {}
						
						
						// after checking all squares, if no possible moves were found, it is a draw
						if (hasMove == 0) {
							return true;
						}
						else {
							return false;
						}
					}
				}
				
			}
		}
		
		return false;
		
    }
	
	// the next 8 private methods will use try to see if there is a playable piece, and throw IndexOutofBoundsException if checking non exitent tile
	private boolean hasNorth(char player, int i, int j) throws IndexOutofBoundsException {
		i--;
		
		// the logic for the other 7 methods is identical, check the tile and if found a move we return true since there is a move, therefore, not a draw
		// if tile we are trying to check is out of bounds, throw a new exception
		if (i < 0) {
			i++;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		
		// if the found the tile, not a draw
		else if (theBoard[i][j] == player) {
			i++;
			return true;
		}
		
		// if not found, check the rest, currently drawable
		else {
			i++;
			return false;
		}
	}
	
	private boolean hasEast(char player, int i, int j) throws IndexOutofBoundsException {
		j++;
		if (j >= size) {
			j--;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			j--;
			return true;
		}
		else {
			j--;
			return false;
		}
	}
	
	private boolean hasSouth(char player, int i, int j) throws IndexOutofBoundsException {
		i++;
		if (i >= size) {
			i--;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			i--;
			return true;
		}
		else {
			i--;
			return false;
		}
	}
	
	private boolean hasWest(char player, int i, int j) throws IndexOutofBoundsException {
		j--;
		if (j < 0) {
			j++;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			j++;
			return true;
		}
		else {
			j++;
			return false;
		}
	}
	
	private boolean hasNorthEast(char player, int i, int j) throws IndexOutofBoundsException {
		i--;
		j++;
		if (i < 0 || j >= size) {
			i++;
			j--;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			i++;
			j--;
			return true;
		}
		else {
			i++;
			j--;
			return false;
		}
	}
	
	private boolean hasEastSouth(char player, int i, int j) throws IndexOutofBoundsException {
		i++;
		j++;
		if (i >= size || j >= size) {
			i--;
			j--;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			i--;
			j--;
			return true;
		}
		else {
			i--;
			j--;
			return false;
		}
	}
	
	private boolean hasSouthWest(char player, int i, int j) throws IndexOutofBoundsException {
		i++;
		j--;
		if (i >= size || j < 0) {
			i--;
			j++;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			i--;
			j++;
			return true;
		}
		else {
			i--;
			j++;
			return false;
		}
	}
	
	private boolean hasWestNorth(char player, int i, int j) throws IndexOutofBoundsException {
		i--;
		j--;
		if (i < 0 || j < 0) {
			i++;
			j++;
			throw new IndexOutofBoundsException("Out of Bounds");
		}
		else if (theBoard[i][j] == player) {
			i++;
			j++;
			return true;
		}
		else {
			i++;
			j++;
			return false;
		}
	}
	
	

	public boolean winner(char player) {

        // first we will check if a player won on the rows (tiles in a line across row same)
        for (int row = 0; row < size; row++) {
            int win = 0;

            // iterate over the rows and column
            for (int col = 0; col < size; col++) {
                if (theBoard[row][col] == player) {
                    win++;
                }
            }
            
            if (win == size) {
                return true;
            }
        }
        

        // then we check the columns
        for (int col = 0; col < size; col++) {
            int win = 0;

            // iterate over the columns then rows
            for (int row = 0; row < size; row++) {
                if (theBoard[row][col] == player) {
                    win++;
                }
            }
            
            if (win == size) {
                return true;
            }
        }
        // now we have 2 cases left, a consistent diagonal from top left to bottom right and bottom left to top right (for simplicity)

        // staring with top left to bottom right, we declare a variable assuming that the top left to bottom right is consistent
        boolean downdiag = true;
        int win = 0;

        for (int row = 0, col = 0; row < size; row++, col++) {
        	
            if (theBoard[row][col] == player) {
                win++;
            }
        }

        if (win == size) {
            return true;
        }
        
        // same thing but modified for loop for the bottom left to top right check
        boolean updiag = true;
        win = 0;
        
        for (int i = 0; i < size; i++) {
        	if (theBoard[i][size - 1 -i] == player) {
                win++;
            }
        }
        if (win != size) {
            updiag = false;
            return updiag;
        }
        else {
            return updiag;
        }
    }

	/**
	 * 3 if the computer wins, 0 if human, 2 if draw, and 1 if undecided state of the game
	 * interacts by calling the winner and isDraw class to find result
	 */
	public int evaluate(char player, int minEmpty) {
        if (winner('c')) {
            return 3;
        }
        else if (winner('h')) {
            return 0;
        }
        else if (isDraw(player, minEmpty)) {
            return 2;
        }
        else {
            return 1;
        }
    }

	/**
	 * represents the content of the 2-D array as a String s and then checks if there is a data item in the dictionary 
	 * referenced by dict with key s
	 */
	public int repeatedLayout(Dictionary dict) {
		
		// this formats the array into string s
        String s = Arrays.deepToString(theBoard).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
        System.out.println(s);
        return dict.getScore(s);
    }

	/**
	 * this method represents the content as s like above and creates a Layout object storing s and score and stores it in dict
	 */
	public void storeLayout(Dictionary table, int score) {
        String key = Arrays.deepToString(theBoard).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");

        Layout elem = new Layout(key, score);

        // need try and catch in case not able to store, put method
        try {
            table.put(elem);
        }
        catch (DictionaryException e) {}
    }

	/**
	 * if tile is 'e' return true, else return false
	 */
	public boolean positionIsEmpty(int row, int column) {
        if (theBoard[row][column] == 'e') {
            return true;
        }
        else {
            return false;
        }
    }

	/**
	 * if tile is 'c' return true, else return false
	 */
	public boolean isComputerTile(int row, int column) {
        if (theBoard[row][column] == 'c') {
            return true;
        }
        else {
            return false;
        }
    }

	/**
	 * if tile is 'h' return true, else return false
	 */
	public boolean isHumanTile(int row, int column) {
        if (theBoard[row][column] == 'h') {
            return true;
        }
        else {
            return false;
        }
    }
}
