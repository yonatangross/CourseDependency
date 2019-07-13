package CourseManagement;

import Interpreters.CourseDataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CourseManager {

    private final Logger logger = LoggerFactory.getLogger(CourseManager.class);
    private HashMap<String, Course> courseHashMap = new HashMap<>();
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
        CourseDataFormatter courseDataFormatter = new CourseDataFormatter(dependenciesTable);
        courseHashMap = courseDataFormatter.readHashMapFromDependencyTable();
        courseNameHashMap = courseDataFormatter.getCourseNameHashMap();
    }


    public enum SchoolType {
        COMPUTER_SCIENCE, PSYCHOLOGY
    }
}


