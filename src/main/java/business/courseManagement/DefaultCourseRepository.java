package business.courseManagement;

import business.courseManagement.courseFormating.CourseDataFormatter;
import business.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class DefaultCourseRepository implements CourseRepository {

    private final Logger logger = LoggerFactory.getLogger(DefaultCourseRepository.class);
    private HashMap<String, Course> courseHashMap = new HashMap<>();
    //TODO: remove or move to other class single responsibility is invalid.
    //TODO: maybe split to clientHandler and courseRepository or something.
    private HashMap<String, String> courseNameHashMap = new HashMap<>();
    private SchoolType schoolType;

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
        //TODO: foreach school make dictionary of spelling errors and one for the abstract class.
    }

    public HashMap<String, Course> getCourseHashMap() {
        return courseHashMap;
    }

    public void readCourseTable(String[][] dependenciesTable) {
        CourseDataFormatter courseDataFormatter = new CourseDataFormatter(dependenciesTable,"he");
        courseHashMap = courseDataFormatter.readHashMapFromDependencyTable();
        courseNameHashMap = courseDataFormatter.getCourseNameHashMap();
    }


    public enum SchoolType {
        COMPUTER_SCIENCE, PSYCHOLOGY
    }
}


