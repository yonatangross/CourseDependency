package business.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Course implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(Course.class);
    private String name;
    private Set<String> synonyms;
    private String code;
    private List<List<Course>> prerequisites;
    private List<List<Course>> parallelRequests;
    private List<List<Course>> hearRequests;
    private LinkedList<Course> prerequisitesEdges;
    private LinkedList<Course> parallelRequestsEdges;
    private LinkedList<Course> hearRequestsEdges;

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

       /* Field[] fields = getClass().getDeclaredFields();
        List<Field> listFields = new LinkedList<>();
        for (Field field : fields) {
            if (field.getType() == java.util.List.class) {
                listFields.add(field);
            }
        }

        for (Field listField : listFields) {

        }
*/
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder(courseBase);
            if (prerequisites != null && prerequisites.size() != 0) {
                stringBuilder.append(prerequisites.toString());
            }
            if (parallelRequests != null && parallelRequests.size() != 0) {
                stringBuilder.append(parallelRequests.toString());
            }
            if (hearRequests != null && hearRequests.size() != 0) {
                stringBuilder.append(hearRequests.toString());
            }
        } catch (Exception e) {
            logger.error("exception while printing {}.", this.getName());
            e.printStackTrace();
        }

        if (stringBuilder == null) {
            logger.error("{} stringBuilder is null", this.getName());
        }
        return stringBuilder.toString();
//        return courseBase;
    }
}