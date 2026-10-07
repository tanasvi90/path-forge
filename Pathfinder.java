package pathforge;

import java.util.*;

public final class Pathfinder {
    private Pathfinder() {}
    public static SearchResult bfs(Grid g){
        g.resetSearch(); long t=System.nanoTime(); List<Node> order=new ArrayList<>();
        Queue<Node> q=new ArrayDeque<>(); Set<Node> seen=new HashSet<>(); Node s=g.getStart(), target=g.getTarget();
        q.add(s); seen.add(s); s.frontier=true;
        while(!q.isEmpty()){ Node u=q.remove(); u.frontier=false; if(u.visited)continue; u.visited=true; order.add(u); if(u==target)break;
            for(Node v:g.neighbors(u)) if(!seen.contains(v)){seen.add(v);v.previous=u;v.frontier=true;q.add(v);}
        }
        return result(order,target,t);
    }
    public static SearchResult dfs(Grid g){
        g.resetSearch(); long t=System.nanoTime(); List<Node> order=new ArrayList<>();
        Deque<Node> st=new ArrayDeque<>(); Set<Node> seen=new HashSet<>(); Node s=g.getStart(), target=g.getTarget(); st.push(s);seen.add(s);s.frontier=true;
        while(!st.isEmpty()){Node u=st.pop();u.frontier=false;if(u.visited)continue;u.visited=true;order.add(u);if(u==target)break;
            List<Node> ns=g.neighbors(u); Collections.reverse(ns);
            for(Node v:ns)if(!seen.contains(v)){seen.add(v);v.previous=u;v.frontier=true;st.push(v);}
        }
        return result(order,target,t);
    }
    public static SearchResult dijkstra(Grid g){
        g.resetSearch(); long t=System.nanoTime(); List<Node> order=new ArrayList<>();
        PriorityQueue<Node> pq= new PriorityQueue<>(Comparator.comparingDouble(n->n.distance)); Node s=g.getStart(), target=g.getTarget(); s.distance=0;pq.add(s);s.frontier=true;
        while(!pq.isEmpty()){
            Node u=pq.poll(); if(u.visited)continue; u.frontier=false;u.visited=true;order.add(u);if(u==target)break;
            for(Node v:g.neighbors(u)){double nd=u.distance+1;if(nd<v.distance){v.distance=nd;v.previous=u;pq.add(v);v.frontier=true;}}
        }
        return result(order,target,t);
    }
    private static SearchResult result(List<Node> order,Node target,long t){
        List<Node> path=new ArrayList<>(); for(Node cur=target;cur!=null;cur=cur.previous)path.add(cur); Collections.reverse(path);
        if(path.size()==1 && order.get(order.size()-1)!=target)path.clear();
        for(Node n:path)n.path=true;
        return new SearchResult(order,path,System.nanoTime()-t);
    }
}
