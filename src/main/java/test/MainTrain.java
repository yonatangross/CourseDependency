package test;

import Interpreters.DependencyFileReaderFactory;
import Interpreters.dependencyFileReader;
import CourseManagement.Course;
import CourseManagement.CourseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

public class MainTrain {

    public static void main(String[] args) {
        //logger = LoggerFactory.getLogger(MainTrain.class);
        Logger logger = LoggerFactory.getLogger(MainTrain.class);

        CourseManager courseManager = new CourseManager();
        String filePath = "C:\\Users\\yonat\\eclipse-workspace\\CourseDependency\\Table.docx";
        DependencyFileReaderFactory dependencyFileReaderFactory = new DependencyFileReaderFactory();
        logger.info("program started.");
        dependencyFileReader dependencyFileReader = dependencyFileReaderFactory.readFileType(filePath);
        dependencyFileReader.readFile();
        courseManager.setSchoolType(dependencyFileReader.getSchoolType());
        courseManager.readCourseTable(dependencyFileReader.getDependenciesTable());
        //TODO: go over all course parallelRequests & preRequests and change name for unique Code.
        // change before the creating map in order the save complexity
        //
        HashMap<String, Course> courseHashMap = courseManager.getCourseHashMap();
        // TODO: move from hashmap to graph and use topological sort
        // TODO:
        // TODO: paint the graph using GraphStream Lib.

        System.out.println(courseHashMap);
        try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Test.xml")))) {
            Course c1 = courseHashMap.get("636016");
            Course c2 = courseHashMap.get("600093");

            e.writeObject(c1);
            e.writeObject(c2);
//            e.writeObject(courseHashMap);
        } catch (IllegalArgumentException | FileNotFoundException e) {
            logger.error("{} exception",e.toString());
            e.printStackTrace();
        }


        logger.info("Ending program.");
    }

}


