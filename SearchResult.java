package pathforge;

import java.util.*;

public class SearchResult {
    public final List<Node> visitedOrder;
    public final List<Node> path;
    public final long elapsedNanos;
    public SearchResult(List<Node> visitedOrder,List<Node> path,long elapsedNanos){this.visitedOrder=visitedOrder;this.path=path;this.elapsedNanos=elapsedNanos;}
}
