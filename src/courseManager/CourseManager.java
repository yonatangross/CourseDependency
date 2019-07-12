package courseManager;

import Algorithms.StringMatchers.LevenshteinDistance;

import java.util.*;
import java.util.stream.Collectors;

public class CourseManager {
    private static final int CODE_COL_NUMBER = 0;
    private static final int NAME_COL_NUMBER = 1;
    private static final int PREREQUISITE_COL_NUMBER = 2;
    private static final int PARALLELREQUESTS_COL_NUMBER = 3;
    private HashMap<String, Course> courseHashMap = new HashMap<>();
    private HashMap<String, String> courseNameHashMap = new HashMap<>();
    private SchoolType schoolType;

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
        //TODO: foreach school make dictionary of spelling erros and one for the abstract class.
    }
    //TODO: Create dictionary of spelling errors.
    //TODO: Go over the array of illegal courses and name and change them with levinstein distance algo and compare them
    // to the courses in the hashmap.
    //TODO: Replace the errors.

    public HashMap<String, Course> getCourseHashMap() {
        return courseHashMap;
    }

    public void readCourseTable(String[][] dependenciesTable) {
        //TODO: Change from reading in rows to reading in columns
        // only read courseCode and courseName
        // create map. and then read the rest
        // change course with using composite design pattern.
        for (int i = 1; i < dependenciesTable.length; i++) {
            String[] courseRow = dependenciesTable[i];
            List<Course> currentCourses = readBasicDetailsFromTableRow(courseRow);
            assert currentCourses != null;
            for (Course course : currentCourses) {
                courseNameHashMap.put(course.getName(), course.getCode());
                courseHashMap.put(course.getCode(), course);
            }
        }

        for (int i = 1; i < dependenciesTable.length; i++) {
            String[] tableRow = dependenciesTable[i];
            List<String> coursesCodes = getCourseDetail(tableRow, CODE_COL_NUMBER);


            List<List<Course>> coursePreRequests = readRequestsArray(tableRow[PREREQUISITE_COL_NUMBER]);
            List<List<Course>> courseParallelRequests = readRequestsArray(tableRow[PARALLELREQUESTS_COL_NUMBER]);
            try {
                for (String courseCode : coursesCodes) {
                    Course course = courseHashMap.get(courseCode);
                    course.setCoursePrerequisites(coursePreRequests);
                    course.setCourseParallelRequests(courseParallelRequests);
                }
            } catch (NullPointerException e) {
                System.out.println(e.getClass() + " Exception" + e.getCause());
                //TODO: print list of empty lists.

                e.printStackTrace();
            }
        }
    }

    private List<Course> readBasicDetailsFromTableRow(String[] tableRow) {
        List<Course> rowCourses = new LinkedList<>();
        try {
            List<String> codes = getCourseDetail(tableRow, CODE_COL_NUMBER);
            List<String> names = getCourseDetail(tableRow, NAME_COL_NUMBER);
            for (int courseIndex = 0; courseIndex < codes.size(); courseIndex++) {
                Course course = new Course(codes.get(courseIndex).trim(), names.get(courseIndex).trim());
                rowCourses.add(course);
            }
            return rowCourses;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        return null; // error.
    }

    private List<String> getCourseDetail(String[] tableRow, int codeColNumber) {
        String cleanString = cleanErrors(tableRow[codeColNumber]);
        String[] split = cleanString.split("[\\t\\n/]");

        List<String> collect = Arrays.stream(split).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        return collect.stream().map(String::trim).collect(Collectors.toList());
    }

    private String cleanErrors(String string) {
        String cleanString;
        cleanString = string.trim().replace("  ", " ");
        return spellingFixer(cleanString);
    }

    private String spellingFixer(String cleanString) {
        //TODO: haven't started yet.
        return cleanString;
    }

    private List<List<Course>> readRequestsArray(String courseRequestsString) {
        String cleanRequests = cleanErrors(courseRequestsString); // remove empty lines and leading/ending spaces.
        if (cleanRequests.contentEquals("-------------------"))
            return null;
        List<List<String>> courseRequests = parseRequestsList(cleanRequests);

        return getCourseRequestsList(courseRequests);
    }

    private List<List<Course>> getCourseRequestsList(List<List<String>> courseRequests) {
        List<List<Course>> courseRequestsList = new LinkedList<>();

        for (List<String> identicalCourseRequestListString : courseRequests) {
            List<Course> identicalRequestList = new LinkedList<>();
            for (String courseRequestString : identicalCourseRequestListString) {
                String courseCode = courseNameHashMap.get(courseRequestString);
                if (courseCode == null) { //Calculates closest course name using Levenshtein's Distance algorithm.
                    System.out.println("Illegal course name: " + courseRequestString);
                    String closestCourseName = findClosestCourseName(courseRequestString);
                    courseCode = courseNameHashMap.get(closestCourseName);
                    System.out.println("found closest name: " + closestCourseName);
                }
                Course course = courseHashMap.get(courseCode);
                identicalRequestList.add(course);
            }
            courseRequestsList.add(identicalRequestList);
        }
        return courseRequestsList;
    }

    private String findClosestCourseName(String courseRequestString) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        String closestCourseName = null;
        int minLevensteinDistance = Integer.MAX_VALUE;
        for (String courseName : courseNameHashMap.keySet()) {
            int currentLevensteinDistance = levenshteinDistance.calculate(courseRequestString, courseName);
            if (currentLevensteinDistance < minLevensteinDistance) {
                closestCourseName = courseName;
                minLevensteinDistance = currentLevensteinDistance;
            }
        }
        return closestCourseName;
    }

    // למידה חישובית וכריית נתונים (למידה ממוחשבת) 600089
    //תכנות אלגוריתמי ב- java  או	פתוח תכנה מתקדם II
    private List<List<String>> parseRequestsList(String cleanRequests) {
        String[] splitWordsArray = {" או ", "/", " או\t", "\\n", "\\t", ",", "."};
        List<List<String>> courseRequests = new LinkedList<>();
        String[] requests = cleanRequests.split("[\\n\\t,.]| או\t");

        List<String> requestsList = Arrays.stream(requests).filter(s -> !s.isEmpty()).map(String::trim).collect(Collectors.toList());

        requestsList.forEach(request -> {
            List<String> list = new LinkedList<>();
            if (request.contains("/") || request.contains(" או ")) {
                list = new LinkedList<>(Arrays.asList(request.split("/|( או )")));
                list.replaceAll(String::trim);
                list.forEach(s -> {
                    int len = s.length();
                    if (len > 50) {
                        List<String> coursesNames = getCourses(s).stream().map(String::trim).collect(Collectors.toList());
                        System.out.println((char) 27 + "[31mlong string contains courses: = " + coursesNames);
                        System.out.print((char) 27 + "[30m");

                        //TODO: what to do with it? need to move first course back to prior list and add second to the current list.
                    }
                });
                courseRequests.add(list);
            } else {
                list.add(request);
                courseRequests.add(list);
            }
        });
        return courseRequests;
    }

    private List<String> getCourses(String longCourseNameString) {
        //TODO: need fix, split the longCourseNameString to the courses from namehashmap.
        //
        int numberOfCourses = 0;
        List<String> courses = new LinkedList<>();
        StringBuilder sb = new StringBuilder(longCourseNameString);
        StringBuilder fixedSb;

        for (String courseName : courseNameHashMap.keySet()) {

            int startIndex = sb.toString().indexOf(courseName);
            if (startIndex != -1) {
                numberOfCourses++;
                int courseNameLength = courseName.length();
                fixedSb = sb.delete(startIndex, startIndex + courseNameLength);
                courses.add(courseName);
                sb = fixedSb;
                if (sb.length() < 2)
                    break;
            }
        }
        return courses;
    }

    public enum SchoolType {
        COMPUTER_SCIENCE, PSYCHOLOGY;
    }
}
