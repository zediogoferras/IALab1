import java.util.ArrayList;
import java.util.List;

public class Board implements Ilayout, Cloneable{
    private static final int dim = 3;
    private int board[][];

    public Board(){
        board = new int[dim][dim];
    }

    /**
     * Board constructor
     * Works as a shallow copy
     * @param b board to be copied to this new one
     */
    public Board(Board b){
        board = new int[dim][dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                board[i][j] = b.board[i][j];
            }
        }
    }

    /**
     * Board constructor
     * @param str linear string that defines the board
     * @throws IllegalStateException if the string is of wrong size (won't fit the board)
     */
    public Board(String str) throws IllegalStateException{
        if(str.length() != dim*dim){
            throw new IllegalStateException("Invalid arg in Board constructor");
        }
        board = new int[dim][dim];
        int si = 0;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                board[i][j] = Character.getNumericValue(str.charAt(si++));
            }
        }
    }


    /**
     * toString method
     * @return board in string format 2D
     */
    public String toString(){
        String str = "";
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                int num = board[i][j];
                //zero represents empty
                if(num == 0){
                    str += " ";
                }
                //add a normal number
                else{
                    str += Character.forDigit(num,10);
                }
            }
            //add new line between rows
            str += "\r\n";
        }
        return str;
    }

    /**
     * Equals method
     * @param o object to compare
     * @return true if they are equal, false if not
     */
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }

    /**
     * hashCode method
     * @return hashCode of the string format of the board
     */
    public int hashCode(){
        return toString().hashCode();
    }

    /**
     * Finds the empty space coordinates in the matrix
     * @return empty space coordinates {i, j}
     */
    private int[] findEmptySpace(){
        int emptySpaceI = 0;
        int emptySpaceJ = 0;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(board[i][j] == 0){
                    emptySpaceI = i;
                    emptySpaceJ = j;
                    return new int[]{emptySpaceI, emptySpaceJ};
                }
            }
        }
        return null;
    }

    /**
     * NOTE: all the comparisons are made from the perspective of the empty space to any other piece
     * @return the possible board layouts after all the possible moves have been made from current position
     */
    @Override
    public List<Ilayout> children() {
        int emptySpaceCoords[] = findEmptySpace();
        int emptySpaceI = emptySpaceCoords[0];
        int emptySpaceJ = emptySpaceCoords[1];
        List<Ilayout> children = new ArrayList<>();

        Board tempBoard;
        int tempNum;

        //can exchange with top when it is anywhere but the first row
        if(emptySpaceI > 0){
            tempBoard = new Board(this);
            tempNum = tempBoard.board[emptySpaceI-1][emptySpaceJ];
            tempBoard.board[emptySpaceI-1][emptySpaceJ] = 0;
            tempBoard.board[emptySpaceI][emptySpaceJ] = tempNum;
            children.add(tempBoard);
        }

        //can exchange with bottom when it is anywhere but the last row
        if(emptySpaceI < dim-1){
            tempBoard = new Board(this);
            tempNum = tempBoard.board[emptySpaceI+1][emptySpaceJ];
            tempBoard.board[emptySpaceI+1][emptySpaceJ] = 0;
            tempBoard.board[emptySpaceI][emptySpaceJ] = tempNum;
            children.add(tempBoard);
        }

        //can exchange with left when it is anywhere but the first column
        if(emptySpaceJ > 0){
            tempBoard = new Board(this);
            tempNum = tempBoard.board[emptySpaceI][emptySpaceJ-1];
            tempBoard.board[emptySpaceI][emptySpaceJ-1] = 0;
            tempBoard.board[emptySpaceI][emptySpaceJ] = tempNum;
            children.add(tempBoard);
        }

        //can exchange with right when it is anywhere but the last column
        if(emptySpaceJ < dim-1){
            tempBoard = new Board(this);
            tempNum = tempBoard.board[emptySpaceI][emptySpaceJ+1];
            tempBoard.board[emptySpaceI][emptySpaceJ+1] = 0;
            tempBoard.board[emptySpaceI][emptySpaceJ] = tempNum;
            children.add(tempBoard);
        }

        return children;
    }

    /**
     * Checks if current board layout is the same as the goal board layout
     * @param l goal board layout
     * @return true if they are the same, false if not
     */
    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    /**
     * @return cost for each move (will always be 1)
     */
    @Override
    public double getG() {
        return 1;
    }
}