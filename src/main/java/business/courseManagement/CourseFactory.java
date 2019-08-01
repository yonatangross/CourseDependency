package business.courseManagement;

import business.entity.Course;

import java.util.HashMap;

public class CourseFactory {
    private static HashMap<String, Course> courseHashMap=new HashMap<>();

    // Method to getTableType a player
    public static Course getCourse(String type)
    {
        Course course = null;

        /* If an object for TS or CT has already been
           created simply return its reference */
        if (courseHashMap.containsKey(type))
            course = courseHashMap.get(type);
        else
        {
            switch(type)
            {

                default :   
                    System.out.println("Unreachable code!");
            }

            // Once created insert it into the HashMap
            courseHashMap.put(type, course);
        }
        return course;
    }
}