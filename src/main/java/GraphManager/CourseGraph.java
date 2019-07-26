package GraphManager;

import CourseManagement.Course;

import java.util.*;

public class CourseGraph implements Iterable, Graph<Course> {
    private Map<Course, Set<Course>> verticesMap;

    private int edgesCount;

    public CourseGraph(Collection<Course> courses) {
        verticesMap = new HashMap<>();
        buildGraph(courses);
    }

    private void buildGraph(Collection<Course> courses) {
        for (Course course : courses) {

        }
    }



/*
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("D", "G");
        graph.addEdge("E", "G");
        graph.addVertex("H");

        System.out.println(graph);

        System.out.println("Vertices: " + graph.getNumVertices());
        System.out.println("Edges: " + graph.getNumEdges());
    }*/


    public int getNumVertices() {
        return verticesMap.size();
    }

    public int getNumEdges() {
        return edgesCount;
    }


    public int degree(Course course) {
        validateVertex(course);
        return verticesMap.get(course).size();
    }

    public void addEdge(Course c1, Course c2) {
        if (!hasVertex(c1)) addVertex(c1);
        if (!hasVertex(c2)) addVertex(c2);
        if (!hasEdge(c1, c2)) edgesCount++;
        verticesMap.get(c1).add(c2);
        verticesMap.get(c2).add(c1);
    }

    public void addVertex(Course course) {
        if (!hasVertex(course)) verticesMap.put(course, new HashSet<>());
    }

    public boolean hasEdge(Course c1, Course c2) {
        validateVertex(c1);
        validateVertex(c2);
        return verticesMap.get(c1).contains(c2);
    }

    public boolean hasVertex(Course course) {
        return verticesMap.containsKey(course);
    }

    @Override
    public Iterator<Course> iterator() {
        return verticesMap.keySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Course course : verticesMap.keySet()) {
            builder.append(course.toString()).append(": ");
            for (Course w : verticesMap.get(course)) {
                builder.append(w.toString()).append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}