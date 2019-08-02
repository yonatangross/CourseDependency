package test;

import business.courseManagement.CourseRepository;
import business.courseManagement.DefaultCourseRepository;
import business.entity.Course;
import business.graph.CourseGraph;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import input.dependencyTableReader.file.DependencyFileReader;
import input.dependencyTableReader.file.DependencyFileReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MainTrain {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(MainTrain.class);
        logger.info("program started.");

        CourseRepository defaultCourseRepository = new DefaultCourseRepository();
        DependencyFileReaderFactory dependencyFileReaderFactory = new DependencyFileReaderFactory();

        String filePath = "C:\\Users\\yonat\\Documents\\CourseDependency\\src\\main\\resources\\tables\\NewTable.docx";
        //TODO: change to generic reader(file/DB)...
        DependencyFileReader dependencyFileReader = dependencyFileReaderFactory.readFileType(filePath);
        dependencyFileReader.readFile();
        //defaultCourseRepository.setSchoolType(dependencyFileReader.getSchoolType());
        defaultCourseRepository.readCourseTable(dependencyFileReader.getDependenciesTable());
        HashMap<String, Course> courseHashMap = defaultCourseRepository.getCourseHashMap();
        /*TODO features
           - get specified courses list for necessary for a taking a class, from user by courseID/courseName(auto complete if frontend)
           - get all course necessary for a taking a class, from user by courseID/courseName(auto complete if frontend)
           - use Observable to visualize data:
                -each course will be displayed by his requests.
                -differnt color for each type of requests.
                -modeling requires edges representation of a course: name and children/ or change the front. maybe create adapter.
                -
           - convert course to JSON what is the required fields?
         */

        ObjectMapper objectMapper= new ObjectMapper();
        try {
            String jsonStr = objectMapper.writeValueAsString(courseHashMap.get("636014"));
            System.out.print("\n" + jsonStr + "\n");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        CourseGraph courseGraph = new CourseGraph(courseHashMap.values());


        // TODO: move from hashMap to graph and use topological sort
     /*   try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Test.xml")))) {
            Course c1 = courseHashMap.getTableType("636016");
            Course c2 = courseHashMap.getTableType("600093");
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


