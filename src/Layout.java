/**
 * stores 2 instance variables, a board layout and its associated integer score, represented by a string 
 * @author nicklam
 *
 */
public class Layout {

    // variable to hold the layout 
    private String boardLayout;

    // integer variable to hold the score
    private int score;
    
    // constructor for the layout and score entered into the Dictionary 
    public Layout(String boardLayout, int score) {
        this.boardLayout = boardLayout;
        this.score = score;
    }
    
    // returns the boardLayout stored 
    public String getBoardLayout() {
            return this.boardLayout;
    }

    // returns the score stored in the Layout object 
    public int getScore() {
        return this.score;
    }

}
