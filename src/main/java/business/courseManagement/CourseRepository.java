package business.courseManagement;

import business.entity.Course;

import java.util.HashMap;

public interface CourseRepository {
    void readCourseTable(String[][] dependenciesTable);

    HashMap<String, Course> getCourseHashMap();
}
