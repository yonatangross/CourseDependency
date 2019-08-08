package business.entity;

import business.util.UtilityPrinting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(Course.class);
    private String name;
    //    private Set<String> synonyms;
    private String code;
    private List<List<Course>> prerequisites;
    private List<List<Course>> parallelRequests;
    private List<List<Course>> hearRequests;



    public List<List<Course>> getHearRequests() {
        return hearRequests;
    }

    public Course() {
    }

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public void setHearRequests(List<List<Course>> hearRequests) {
        this.hearRequests = hearRequests;
    }

    public void setPrerequisites(List<List<Course>> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setParallelRequests(List<List<Course>> parallelRequests) {
        this.parallelRequests = parallelRequests;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {/*
        String courseBase = "Course{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'';

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
            }*/

        String courseOutput = "Course: " + name;
        StringBuilder stringBuilder2 = new StringBuilder(courseOutput);
        try {

            if (prerequisites != null && prerequisites.size() != 0) {
                stringBuilder2.append("\n\tPrerequisites: ");
                stringBuilder2.append(UtilityPrinting.formattedListToString(prerequisites));

//                stringBuilder2.append(prerequisites.toString());
            }
            if (parallelRequests != null && parallelRequests.size() != 0) {
                stringBuilder2.append("\n\tParallel Requests: ");
                stringBuilder2.append(UtilityPrinting.formattedListToString(parallelRequests));

//                stringBuilder2.append(parallelRequests.toString());
            }
            if (hearRequests != null && hearRequests.size() != 0) {
                stringBuilder2.append("\n\tHearing Requests ");
                stringBuilder2.append(UtilityPrinting.formattedListToString(hearRequests));
//                stringBuilder2.append(hearRequests.toString());
            }

        } catch (Exception e) {
            logger.error("exception while second type printing {}.", this.getName());
            e.printStackTrace();
        }
       /* } catch (Exception e) {
            logger.error("exception while printing {}.", this.getName());
            e.printStackTrace();
        }
*/
        return stringBuilder2.toString();
//       return courseBase;
    }
}
