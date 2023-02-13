import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver {

  private Object[][] goal = { { 7, 8, 1 }, { 6, "*", 2 }, { 5, 4, 3 } };
  private Set<Board> visited = new HashSet<Board>();
  private final int DEPTH_LIMIT = 10;
  private boolean solved = false;
  private int enq = 0;

  private hammingOrder hamOrder = new hammingOrder();
  private PriorityQueue<Node> hq = new PriorityQueue<Node>(100, hamOrder);

  private manhattanOrder manOrder = new manhattanOrder();
  private PriorityQueue<Node> mq = new PriorityQueue<Node>(100, manOrder);

  private class hammingOrder implements Comparator<Node> {
    public int compare(Node a, Node b) {
      int aScore = a.getBoard().hamming(goal) + a.getMoves();
      int bScore = b.getBoard().hamming(goal) + b.getMoves();
      if (aScore > bScore) {
        return 1;
      } else if (bScore > aScore) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  private class manhattanOrder implements Comparator<Node> {
    public int compare(Node a, Node b) {
      int aScore = a.getBoard().manhattan(goal) + a.getMoves();
      int bScore = b.getBoard().manhattan(goal) + b.getMoves();
      if (aScore > bScore) {
        return 1;
      } else if (bScore > aScore) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  public boolean isGoal(Board b) {
    if (Arrays.deepEquals(b.getGrid(), goal)) {
      return true;
    } else {
      return false;
    }
  }

  public ArrayList<Board> getPath(Node node) {
    ArrayList<Board> path = new ArrayList<Board>();
    path.add(node.getBoard());
    while (node.getPrev() != null) {
      path.add(node.getPrev().getBoard());
      node = node.getPrev();
    }
    return path;
  }

  public int moves() {
    return 0;
  }

  public void foundGoal(Node node) {
    solved = true;
    ArrayList<Board> path = getPath(node);

    for (int i = path.size() - 1; i >= 0; i--) {
      System.out.println(path.get(i));
    }

    System.out.println("Moves = " + node.getMoves());
    System.out.println("enq = " + enq);
    System.out.println("States Enqueued = " + visited.size());
  }

  public void dfs(Node node) {

    // Handle Found Goal State
    if (isGoal(node.getBoard()) && !solved) {
      foundGoal(node);
      return;
    }

    // Handle non-Goal State
    if (node.getMoves() < DEPTH_LIMIT && !solved) {
      visited.add(node.getBoard());
      enq++;
      ArrayList<Board> neighbors = node.getBoard().neighbors();
      for (Board b : neighbors) {
        if (!visited.contains(b)) {
          dfs(new Node(b, node));
        }
      }
    }
  }

  public void ids(Node root) {
    for (int i = 0; i <= DEPTH_LIMIT; i++) {
      dls(root, i);
    }
  }

  public void dls(Node node, int depth) {
    // Handle found state
    if (isGoal(node.getBoard()) && !solved) {
      foundGoal(node);
      return;
    }

    // Handle non-goal state
    if (depth > 0 && !solved) {
      visited.add(node.getBoard());
      enq++;
      ArrayList<Board> neighbors = node.getBoard().neighbors();
      for (Board b : neighbors) {
        if (!visited.contains(b)) {

          dls(new Node(b, node), depth - 1);
        }
      }
    }
  }

  public void astar1(Node root) { // Hamming
    Node node = root;

    while (!isGoal(node.getBoard())) {
      visited.add(node.getBoard());
      for (Board b : node.getBoard().neighbors()) {
        if (!visited.contains(b)) {
          Node neighbor = new Node(b, node);
          hq.add(neighbor);
          enq++;
        }
      }

      node = hq.poll();
    }

    foundGoal(node);

  }

  public void astar2(Node root) { // Manhattan
    Node node = root;

    while (!isGoal(node.getBoard())) {
      visited.add(node.getBoard());
      for (Board b : node.getBoard().neighbors()) {
        if (!visited.contains(b)) {
          Node neighbor = new Node(b, node);
          mq.add(neighbor);
          enq++;
        }
      }

      node = mq.poll();
    }

    foundGoal(node);
  }

  public static void main(String[] args) {

    File file = new File(args[1]);
    ArrayList<Object> buffer = new ArrayList<Object>();

    try {

      Scanner sc = new Scanner(file);

      while (sc.hasNextLine()) {
          buffer.add(sc.next());

      }
      sc.close();
    } 
    catch (FileNotFoundException e) {
        e.printStackTrace();
    }

    Object[][] input = new Object[3][3];

    int index = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
          input[i][j] = buffer.get(index);
          index++;
      }
  }

    
    Solver solver = new Solver();
    Board game = new Board(input);
    Node root = new Node(game);


    switch(args[0]) {
      case "dfs":
        System.out.println("Solving puzzle with Depth First Search...");
        solver.dfs(root);
        break;
      case "ids":
      System.out.println("Solving puzzle with Iterative Deepening Search...");
        solver.ids(root);
        break;
      case "astar1":
      System.out.println("Solving puzzle with A* (Hamming)...");
        solver.astar1(root);
        break;
      case "astar2":
        solver.astar2(root);
        System.out.println("Solving puzzle with A* (Manhattan)...");
        break;
      default:
        System.out.println("Algorithm Not Recognized.");
    }
  }
}