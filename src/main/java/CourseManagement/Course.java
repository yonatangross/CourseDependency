package CourseManagement;

import TableManager.TableClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(Course.class);
    private String name;
    private String code;
    private List<List<Course>> prerequisites;
    private List<List<Course>> parallelRequests;
    private List<List<Course>> hearRequests;

    public Course() {
    }

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public List<List<Course>> getHearRequests() {
        return hearRequests;
    }

    public void setHearRequests(List<List<Course>> hearRequests) {
        this.hearRequests = hearRequests;
    }

    public List<List<Course>> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<List<Course>> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<List<Course>> getParallelRequests() {
        return parallelRequests;
    }

    public void setParallelRequests(List<List<Course>> parallelRequests) {
        this.parallelRequests = parallelRequests;
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
        String courseBase = "Course{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'';


    /*    StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder(courseBase);
            if (prerequisites != null) {
                stringBuilder.append(prerequisites.toString());
            }
            if (parallelRequests != null) {
                stringBuilder.append(parallelRequests.toString());
            }
            if (hearRequests != null) {
                stringBuilder.append(hearRequests.toString());
            }
        } catch (Exception e) {
            logger.
            e.printStackTrace();
        }

        return stringBuilder.toString();*/
    return courseBase;
    }
}
