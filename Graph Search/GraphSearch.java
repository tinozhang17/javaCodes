import java.util.List;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 * Your implementations of various graph search algorithms.
 *
 * @author Ziyu Zhang
 * @version 1.0
 */
public class GraphSearch {

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using General Graph
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * The structure(struct) passed in is an empty structure that may behave as
     * a Stack or Queue and this function should execute DFS or BFS on the
     * graph, respectively.
     *
     * DO NOT use {@code instanceof} to determine the type of the Structure!
     *
     * @param start the object representing the node you are starting at.
     * @param struct the Structure you should use to implement the search.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists, false otherwise.
     */
    private static <T> boolean generalGraphSearch(T start, Structure<T> struct,
            Map<T, List<T>> adjList, T goal) {
        if (start == null || goal == null || adjList == null
                || !adjList.containsKey(start) || !adjList.containsKey(goal)) {
            throw new IllegalArgumentException("illegal input!");
        }
        Set<T> set = new HashSet<>();
        set.add(start);
        if (start.equals(goal)) {
            return true;
        }
        struct.add(start);
        while (!struct.isEmpty()) {
            T removed = struct.remove();
            List<T> list = adjList.get(removed);
            for (T neighbor : list) {
                if (neighbor.equals(goal)) {
                    return true;
                } else {
                    if (!set.contains(neighbor)) {
                        struct.add(neighbor);
                        set.add(neighbor);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Breadth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean breadthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        return generalGraphSearch(start, new StructureQueue<>(), adjList, goal);
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Depth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean depthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        return generalGraphSearch(start, new StructureStack<>(), adjList, goal);
    }

    /**
     * Find the shortest distance between the start node and the goal node
     * given a weighted graph in the form of an adjacency list where the
     * edges only have positive weights. If there are no adjacent nodes for
     * a node, then an empty list is present.
     *
     * Return the aforementioned shortest distance if there exists a path
     * between the start and goal, -1 otherwise.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue}.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return the shortest distance between the start and the goal node
     */
    public static <T> int dijkstraShortestPathAlgorithm(T start,
            Map<T, List<VertexDistancePair<T>>> adjList, T goal) {
        if (start == null || goal == null || adjList == null
                || !adjList.containsKey(start) || !adjList.containsKey(goal)) {
            throw new IllegalArgumentException("illegal input!");
        }
        Set<T> set = new HashSet<>();
        Queue<VertexDistancePair<T>> q = new PriorityQueue<>();
        q.add(new VertexDistancePair<>(start, 0));
        VertexDistancePair<T> node;
        T vertex;
        int distance;
        while (!q.isEmpty()) {
            node = q.remove();
            vertex = node.getVertex();
            distance = node.getDistance();
            if (vertex.equals(goal)) {
                return distance;
            } else {
                set.add(vertex);
                List<VertexDistancePair<T>> list = adjList.get(vertex);
                for (VertexDistancePair<T> removed : list) {
                    T newVertex = removed.getVertex();
                    if (!set.contains(newVertex)) {
                        q.add(new VertexDistancePair<>(newVertex,
                                removed.getDistance() + distance));
                    }
                }
            }
        }
        return -1;
    }

}
