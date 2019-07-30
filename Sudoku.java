import java.util.Random;
public class Sudoku {
    private Random random;
    private int ranNum;
    private int[][] sudokuBoard;
    private int boardSize = 9;
    /*
    * Sudoku()
    * creates board with all spaces initialized to 0
    * I chose 0 since it will not be part of the 1-9
    * The first number between 1-9 will be randomized
    * to be used later
     */

    public Sudoku(){
        this.sudokuBoard = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}};
    }
    /*
     * fillBoard()
     * first checks the row if greater than 8 the board is finished
     * then checks column if board is greater than 8 the go to next row
     * then generates a random number and loops
     * then places the number and checks if the number is possible and
     * if the next move is possible
     * if both are possible then the play can be made and you can return true
     * otherwise, iterate through random numbers from 1-9 until it works
     * if no possible play, exit loop reset the specific value to 0 and
     * return false
     */
    private boolean fillBoard(int row, int col){
        if(row > 8){
            return true;
        }
        else if(col > 8){
            return fillBoard(row+1,0);
        }
        random = new Random();
        ranNum = random.nextInt(boardSize)+1;
        int i = 0;
        while (i < 9) {
            this.sudokuBoard[row][col] = ranNum;
            if(this.validPlay(row,col,ranNum)&&fillBoard(row, col+1)) {
                return true;
            } else {
                ranNum++;
                if (ranNum > 9) {
                    ranNum = 1;
                }
            }
            i++;
        }
        this.sudokuBoard[row][col] = 0;
        return false;
    }

    /*
     * fillBoard()
     * calls the recursive fill board starting at
     * row = 0 and col = 0
     */
    public boolean fillBoard(){
        return fillBoard(0,0);
    }
    /*
     * checkRow()
     * looks at the numbers in the row if they are
     * equal to the value
     * to add efficiency, you only need to check the values
     * that were previously placed on the board which is why
     * the limit is what column you are currently on
     */
    private boolean checkRow(int row, int value, int column){
        for(int i = 0; i < column; i++ ){
            if(this.sudokuBoard[row][i] == value){
                return false;
            }
        }
        return true;
    }
    /*
     * checkColumn()
     * looks at the numbers in the column if they are
     * equal to the value
     * to add efficiency, you only need to check the values
     * that were previously placed on the board which is why
     * the limit is what row you are currently on
     */
    private boolean checkColumn(int column, int value, int row){
        for(int i = 0; i < row; i++ ){
            if(this.sudokuBoard[i][column] == value){
                return false;
            }
        }
        return true;
    }
    /*
     * checkTBT()
     * checks the separated three by three box if the value
     * is inside
     * the way I implemented sudoku, the 3 by 3 only needs to
     * look at the previous row or 2 or none depending on the
     * current row.  checkrow checks the current row already,
     * so this makes the check 3 by 3 more efficient only checking
     * the above rows that have values since the ones below would
     * be filled with zeros.
     * for example if you are at row 0, this does not need to check anything
     * since there are no above rows and check rows already checks the current
     * row, but if you are at row 2, it needs to only check row 1 and 2 within
     * the 3 by 3 box
     */
    private boolean checkTBT(int ro, int column, int value){
        int row =  ro-ro%3;
        int col =  column-column%3;
        for(int i = row; i < ro; i++){
            for(int j = col; j < col + 3; j++){
                if(this.sudokuBoard[i][j] == value){
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * validPlay()
     * Checks all rows and all columns for the chosen
     * number and if the number is not there then place it
     * r for row, c for column, v for the value
     */
    public boolean validPlay(int r, int c, int v){
        if(this.checkRow(r,v,c) && this.checkColumn(c,v,r) && this.checkTBT(r,c,v)){
            return true;
        }
        return false;
    }

    /*
     * printBoard()
     * prints all values on board
     * used once board is solved
     */
    public void printBoard(){
        System.out.print("-------------------------\n");
        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){

                //Add vertical dividers for subgrids
                if (j==0 ||j==3 || j==6 ) {
                    System.out.print("| ");
                }
                System.out.print(this.sudokuBoard[i][j]+" ");
            }
            System.out.print("| ");
            //Add horizontal dividers for subgrids
            if (i==2 || i==5 || i ==8) {
                System.out.print("\n-------------------------");
            }
            System.out.print("\n");
        }
    }
}
