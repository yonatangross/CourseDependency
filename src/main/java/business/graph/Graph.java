package business.graph;

public interface Graph<V> {
    int getNumVertices();

    int getNumEdges();

    default void validateVertex(V v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v.toString() + " is not a vertex");
    }

    int degree(V v);


    void addEdge(V v1, V v2);

    void addVertex(V v);

    boolean hasEdge(V v1, V v2);

    boolean hasVertex(V v);
}
