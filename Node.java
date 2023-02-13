public class Node {
    private int moves;
    private Board boardState;
    private Node prevNode;
    private boolean visited;

    public Node(Board b) {
        moves = 0;
        boardState = b;
        prevNode = null;
    }

    public Node(Board b, Node n) {
        moves = n.moves + 1;
        boardState = b;
        prevNode = n;
    }

    public int getMoves() {
        return this.moves;
    }

    public Board getBoard() {
        return this.boardState;
    }

    public Node getPrev() {
        return this.prevNode;
    }

    public void setVisited(boolean bool) {
        this.visited = bool;
    }
}
