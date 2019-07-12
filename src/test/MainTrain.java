package test;

import courseManager.*;
import Interpreters.dependencyFileReader;

import java.util.HashMap;

public class MainTrain {
    public static void main(String[] args) {
        dependencyFileReader dependencyFileReader;
        CourseManager courseManager = new CourseManager();
        String filePath = "C:\\Users\\yonat\\eclipse-workspace\\CourseDependency\\Table.docx";
        dependencyFileReader.readFileType("C:\\Users\\yonat\\eclipse-workspace\\CourseDependency\\Table.docx");
        dependencyFileReader.readDocxFile();

        courseManager.setSchoolType(dependencyFileReader.getSchoolType());
        String[][] dependenciesTable = dependencyFileReader.getDependenciesTable();
        courseManager.readCourseTable(dependenciesTable);
        //TODO: go over all course parallelRequests & preRequests and change name for unique Code.
        // change before the creating map in order the save complexity
        //
        HashMap<String, Course> courseHashMap = courseManager.getCourseHashMap();

        // TODO: move from hashmap to graph and use topological sort
        // TODO:
        // TODO: paint the graph using GraphStream Lib.

        courseHashMap.toString();
    }

    public static void main1(String[] args) {
        CourseManager courseManager = new CourseManager();
        String filePath = "C:\\Users\\yonat\\eclipse-workspace\\CourseDependency\\Table.docx";

        dependencyFileReader dependencyFileReader=

    }

}


