package pathforge;

import java.util.*;

public class Grid {
    private final int rows, cols;
    private final Node[][] nodes;
    private Node start, target;

    public Grid(int rows, int cols) {
        this.rows = rows; this.cols = cols;
        nodes = new Node[rows][cols];
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++) nodes[r][c] = new Node(r,c);
        start = nodes[rows/2][2];
        target = nodes[rows/2][cols-3];
    }
    public int rows(){return rows;} public int cols(){return cols;}
    public Node get(int r,int c){ return nodes[r][c]; }
    public Node getStart(){return start;} public Node getTarget(){return target;}
    public void setStart(int r,int c){ if(!nodes[r][c].wall && nodes[r][c]!=target) start=nodes[r][c]; }
    public void setTarget(int r,int c){ if(!nodes[r][c].wall && nodes[r][c]!=start) target=nodes[r][c]; }
    public void toggleWall(int r,int c){ Node n=nodes[r][c]; if(n!=start && n!=target) n.wall=!n.wall; }
    public void setWall(int r,int c,boolean value){ Node n=nodes[r][c]; if(n!=start && n!=target) n.wall=value; }
    public List<Node> neighbors(Node n){
        List<Node> out=new ArrayList<>(4); int[][] d={{-1,0},{1,0},{0,-1},{0,1}};
        for(int[] x:d){int r=n.row+x[0],c=n.col+x[1]; if(r>=0&&r<rows&&c>=0&&c<cols&&!nodes[r][c].wall) out.add(nodes[r][c]);}
        return out;
    }
    public void resetSearch(){ for(Node[] row:nodes) for(Node n:row)n.resetSearchState(); }
    public void clearWalls(){ for(Node[] row:nodes) for(Node n:row)n.wall=false; }
    public void randomWalls(double density, Random random){
        clearWalls();
        for(Node[] row:nodes) for(Node n:row) if(n!=start&&n!=target)n.wall=random.nextDouble()<density;
    }
}
