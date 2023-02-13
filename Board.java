import java.util.ArrayList;

public class Board {

    private Object[][] grid;

    public Board(Object[][] grid) {
        this.grid = grid;
    }

    public Board(Board b) {
        this.grid = new Object[3][3];
        for (int i = 0; i < b.grid.length; i++) {
            for (int j = 0; j < b.grid[i].length; j++) {
                this.grid[i][j] = b.grid[i][j];
            }
        }
    }

    public int hamming(Object[][] goal){
        int sum = 0;
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                if(this.grid[i][j] != goal[i][j]){
                    sum++;
                }
            }
        }
        return sum;
    }

    public int manhattan(Object[][] goal){
        int sum = 0;
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                if(this.grid[i][j] != goal[i][j]){
                    sum += manhattan_helper( goal, this.grid[i][j], i, j);
                }
            }
        }
        return sum;
    }

    public int manhattan_helper(Object[][] goal, Object value, int i, int j){
        int score = 0;
        int iGoal = -1, jGoal = -1;
        
        //find value in goal board
        for (int x = 0; x < goal.length; x++) {
            for (int y = 0; y < goal[x].length; y++) {
                if(goal[x][y] == value){
                    iGoal = x;
                    jGoal = y;
                }
            }
        }

        score = Math.abs(i - iGoal);
        score += Math.abs(j - jGoal);

        return score;
    }

    public int[] findStar() {
        // Find * in grid, return coordinates
        int[] coords = { -1, -1 };
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                if (this.grid[i][j].equals("*")) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        return coords;
    }

    public ArrayList<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        ;
        Board copy = new Board(this);

        int[] coords = findStar();
        int i = coords[0];
        int j = coords[1];

        if (i < 2) { // BOTTOM
            copy.grid[i][j] = copy.grid[i + 1][j];
            copy.grid[i + 1][j] = "*";
            neighbors.add(copy);
            copy = new Board(this);
        }

        if (i > 0) { // TOP
            copy.grid[i][j] = copy.grid[i - 1][j];
            copy.grid[i - 1][j] = "*";
            neighbors.add(copy);
            copy = new Board(this);
        }

        if (j > 0) { // LEFT
            copy.grid[i][j] = copy.grid[i][j - 1];
            copy.grid[i][j - 1] = "*";
            neighbors.add(copy);
            copy = new Board(this);
        }

        if (j < 2) { // RIGHT
            copy.grid[i][j] = copy.grid[i][j + 1];
            copy.grid[i][j + 1] = "*";
            neighbors.add(copy);
            copy = new Board(this);
        }

        return neighbors;
    }

    public Object[][] getGrid() {
        return this.grid;
    }

    public String toString() {
        String puzzle = "";
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                puzzle += grid[i][j] + " ";
            }
            puzzle += "\n";
        }
        return puzzle;
    }

}
