package pathforge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PathForge extends JFrame {
    private final Grid grid = new Grid(25,40);
    private final Board board = new Board();
    private final JComboBox<String> algorithm = new JComboBox<>(new String[]{"BFS","DFS","Dijkstra"});
    private final JComboBox<String> mode = new JComboBox<>(new String[]{"Wall","Move Start","Move Target"});
    private final JSlider speed = new JSlider(10,250,45);
    private final JLabel status = new JLabel("Ready — draw walls, choose an algorithm, then Run.");
    private boolean running;

    public PathForge(){
        super("PathForge — Pathfinding Visualizer"); setDefaultCloseOperation(EXIT_ON_CLOSE); setLayout(new BorderLayout(10,10));
        JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton run=new JButton("Run"), reset=new JButton("Reset Search"), clear=new JButton("Clear Walls"), random=new JButton("Random Maze");
        top.add(new JLabel("Algorithm:"));top.add(algorithm);top.add(new JLabel("Mode:"));top.add(mode);top.add(run);top.add(reset);top.add(clear);top.add(random);
        top.add(new JLabel("Speed:"));top.add(speed);
        add(top,BorderLayout.NORTH); add(board,BorderLayout.CENTER); add(status,BorderLayout.SOUTH);
        run.addActionListener(e->runAlgorithm()); reset.addActionListener(e->{grid.resetSearch();board.repaint();status.setText("Search state reset.");});
        clear.addActionListener(e->{grid.clearWalls();grid.resetSearch();board.repaint();status.setText("All obstacles cleared.");});
        random.addActionListener(e->{grid.randomWalls(.28,new Random());grid.resetSearch();board.repaint();status.setText("Random obstacles generated.");});
        setSize(1050,760);setLocationRelativeTo(null);
    }
    private void runAlgorithm(){
        if(running)return; running=true; grid.resetSearch(); board.repaint();
        String name=(String)algorithm.getSelectedItem(); SearchResult r=switch(name){case "DFS"->Pathfinder.dfs(grid);case "Dijkstra"->Pathfinder.dijkstra(grid);default->Pathfinder.bfs(grid);};
        new Thread(()->animate(r,name),"pathforge-animation").start();
    }
    private void animate(SearchResult r,String name){
        try{
            for(Node n:r.visitedOrder){n.frontier=false;board.repaint();Thread.sleep(speed.getValue());}
            for(Node n:r.path){n.path=true;board.repaint();Thread.sleep(Math.max(10,speed.getValue()/2));}
            SwingUtilities.invokeLater(()->status.setText(String.format("%s complete — visited %d | path %d cells | %.3f ms",name,r.visitedOrder.size(),r.path.size(),r.elapsedNanos/1_000_000.0)));
        }catch(InterruptedException ignored){} finally{running=false;}
    }
    private class Board extends JPanel{
        Board(){setPreferredSize(new Dimension(1000,650));setBackground(Color.DARK_GRAY);setBorder(BorderFactory.createLineBorder(Color.GRAY));
            MouseAdapter m=new MouseAdapter(){public void mousePressed(MouseEvent e){edit(e);}
                public void mouseDragged(MouseEvent e){edit(e);}}; addMouseListener(m);addMouseMotionListener(m);}
        void edit(MouseEvent e){if(running)return; int cw=getWidth()/grid.cols(),ch=getHeight()/grid.rows();int c=e.getX()/Math.max(1,cw),r=e.getY()/Math.max(1,ch);if(r<0||r>=grid.rows()||c<0||c>=grid.cols())return;
            String m=(String)mode.getSelectedItem(); if("Move Start".equals(m))grid.setStart(r,c);else if("Move Target".equals(m))grid.setTarget(r,c);else grid.setWall(r,c,true);grid.resetSearch();repaint();}
        protected void paintComponent(Graphics g){super.paintComponent(g);int cw=getWidth()/grid.cols(),ch=getHeight()/grid.rows();
            for(int r=0;r<grid.rows();r++)for(int c=0;c<grid.cols();c++){Node n=grid.get(r,c);Color color=Color.WHITE;if(n.wall)color=Color.BLACK;else if(n.path)color=new Color(0,190,170);else if(n.visited)color=new Color(170,210,235);else if(n.frontier)color=new Color(255,210,100);if(n==grid.getStart())color=new Color(70,190,110);if(n==grid.getTarget())color=new Color(235,80,80);g.setColor(color);g.fillRect(c*cw,r*ch,cw-1,ch-1);}
        }
    }
}
