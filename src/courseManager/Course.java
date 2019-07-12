package courseManager;

import java.util.*;

public class Course {
    private final String code;
    private final String name;

    private List<List<Course>> coursePrerequisites;
    private List<List<Course>> courseParallelRequests;

    Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    void setCoursePrerequisites(List<List<Course>> coursePrerequisites) {
        this.coursePrerequisites = coursePrerequisites;
    }

    void setCourseParallelRequests(List<List<Course>> courseParallelRequests) {
        this.courseParallelRequests = courseParallelRequests;
    }
}
