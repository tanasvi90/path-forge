package pathforge;

public class Node {
    public final int row, col;
    public boolean wall;
    public boolean visited;
    public boolean frontier;
    public boolean path;
    public double distance = Double.POSITIVE_INFINITY;
    public Node previous;

    public Node(int row, int col) { this.row = row; this.col = col; }

    public void resetSearchState() {
        visited = false; frontier = false; path = false;
        distance = Double.POSITIVE_INFINITY;
        previous = null;
    }
}
