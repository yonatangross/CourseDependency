package test;

import CourseManagement.Course;
import CourseManagement.CourseManager;
import InputManager.DependencyFileReaders.DependencyFileReader;
import InputManager.DependencyFileReaders.DependencyFileReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MainTrain {

    public static void main(String[] args) {
        //logger = LoggerFactory.getLogger(MainTrain.class);
        Logger logger = LoggerFactory.getLogger(MainTrain.class);
        logger.info("program started.");

        CourseManager courseManager = new CourseManager();
        DependencyFileReaderFactory dependencyFileReaderFactory = new DependencyFileReaderFactory();

        String filePath = "C:\\Users\\yonat\\Documents\\CourseDependency\\src\\main\\resources\\tables\\NewTable.docx";
        DependencyFileReader dependencyFileReader = dependencyFileReaderFactory.readFileType(filePath);
        dependencyFileReader.readFile();
        courseManager.setSchoolType(dependencyFileReader.getSchoolType());
        courseManager.readCourseTable(dependencyFileReader.getDependenciesTable());
        HashMap<String, Course> courseHashMap = courseManager.getCourseHashMap();



        // TODO: move from hashMap to graph and use topological sort
        // TODO: paint the graph using GraphStream Lib.
//        System.out.println(courseHashMap);
     /*   try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Test.xml")))) {
            Course c1 = courseHashMap.get("636016");
            Course c2 = courseHashMap.get("600093");
            e.writeObject(c1);
            e.writeObject(c2);
//            e.writeObject(courseHashMap);
        } catch (IllegalArgumentException | FileNotFoundException e) {
            logger.error("{} exception", e.toString());
            e.printStackTrace();
        }*/
        logger.info("Ending program.");
    }

}


