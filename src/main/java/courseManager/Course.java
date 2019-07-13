package courseManager;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable {
    private String name;
    private String code;
    private List<List<Course>> coursePrerequisites;
    private List<List<Course>> courseParallelRequests;

    public Course() {
    }

    Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public List<List<Course>> getCoursePrerequisites() {
        return coursePrerequisites;
    }

    public void setCoursePrerequisites(List<List<Course>> coursePrerequisites) {
        this.coursePrerequisites = coursePrerequisites;
    }

    public List<List<Course>> getCourseParallelRequests() {
        return courseParallelRequests;
    }

    public void setCourseParallelRequests(List<List<Course>> courseParallelRequests) {
        this.courseParallelRequests = courseParallelRequests;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nCourse{" + "code='" + code + '\'' + ", name='" + name + '\'' +
                "\n, coursePrerequisites=" + coursePrerequisites +
                "\n, courseParallelRequests=" + courseParallelRequests +
                '}' + "\n";
    }
}
