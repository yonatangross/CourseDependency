package Interpreters;

import Algorithms.StringMatchers.LevenshteinDistance;
import Algorithms.StringMatchers.StringMatcher;
import Algorithms.StringMatchers.WordLevenshteinDistance;
import CourseManagement.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDataFormatter {
    private static final int CODE_COL_NUMBER = 0;
    private static final int NAME_COL_NUMBER = 1;
    private static final int PREREQUISITE_COL_NUMBER = 2;
    private static final int PARALLELREQUESTS_COL_NUMBER = 3;
    private final Logger logger = LoggerFactory.getLogger(CourseDataFormatter.class);
    private final String[][] dependenciesTable;
    private HashMap<String, String> courseNameHashMap = new HashMap<>();

    private HashMap<String, Course> courseHashMap = null;

    public CourseDataFormatter(String[][] dependenciesTable) {
        this.dependenciesTable = dependenciesTable;
    }

    public HashMap<String, String> getCourseNameHashMap() {
        return courseNameHashMap;
    }

    public HashMap<String, Course> readHashMapFromDependencyTable() {
        // class for reading basic columns.
        String[][] clearedTable=clearHeadersFromTable(dependenciesTable);
        readBasicDetailsFromTable(clearedTable);

        // class for reading requests columns.
        fillRequestsColumnsFromTable(clearedTable);
        return courseHashMap;
    }

    private String[][] clearHeadersFromTable(String[][] dependenciesTable) {
        List<String[]> dataTableRows=new LinkedList<>();
        for (String[] tableRow : dependenciesTable) {
            if(tableRow[CODE_COL_NUMBER].matches("[0-9]+")){
                dataTableRows.add(tableRow);
            }
        }
        return dataTableRows.toArray(new String[0][]);
    }

    private void readBasicDetailsFromTable(String[][] dependenciesTable) {
        courseHashMap = new HashMap<>();
        Arrays.stream(dependenciesTable).forEach(courseRow -> {
            List<Course> currentCourses = readBasicDetailsFromTableRow(courseRow);
            if (currentCourses != null) {
                currentCourses.forEach(course -> {
                    courseNameHashMap.put(course.getName(), course.getCode());
                    courseHashMap.put(course.getCode(), course);
                });
            } else {
                logger.error("{} courseRow is null", Arrays.toString(courseRow));
            }
        });
    }

    private List<Course> readBasicDetailsFromTableRow(String[] tableRow) {
        List<Course> rowCourses = new LinkedList<>();
        try {
            List<String> codes = getCourseDetail(tableRow, CODE_COL_NUMBER);
            List<String> names = getCourseDetail(tableRow, NAME_COL_NUMBER);
            if (codes.size() != names.size()) {
                List<String> fixedNames = getFixedNames(codes, names);
                addCoursesInRow(rowCourses, codes, fixedNames);
            } else
                addCoursesInRow(rowCourses, codes, names);
            return rowCourses;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        return null; // error.
    }

    private List<String> getFixedNames(List<String> codes, List<String> names) {
        List<String> updatedNames = new LinkedList<>();
        if (codes.size() == 1 && 1 < names.size())
            // local fix 2 lines to 1.
            updatedNames.add(names.get(0) + " " + names.get(1));
        else {
            logger.error("Unhandled situation!\n course input invalid {}", names.toString());
        }
        return updatedNames;
    }

    private void addCoursesInRow(List<Course> rowCourses, List<String> codes, List<String> names) {
        for (int courseIndex = 0; courseIndex < codes.size(); courseIndex++) {
            Course course = new Course(codes.get(courseIndex).trim(), names.get(courseIndex).trim());
            rowCourses.add(course);
        }
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

    private void fillRequestsColumnsFromTable(String[][] dependenciesTable) {
        for (int i = 1; i < dependenciesTable.length; i++) {
            String[] tableRow = dependenciesTable[i];
            //TODO: Create flywight pattern for course.
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
                logger.error(e.toString() + " " + e.getCause());
                e.printStackTrace();
            }
        }
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
                if (courseCode == null) {
                    courseCode = getClosestCourseName(courseRequestString);
                }
                if (courseCode != null) {
                    Course course = courseHashMap.get(courseCode);
                    identicalRequestList.add(course);
                }
            }
            courseRequestsList.add(identicalRequestList);
        }
        return courseRequestsList;
    }

    private String getClosestCourseName(String courseRequestString) {
        String courseCode;
        String closestCourseName = findClosestCourseName(courseRequestString);
        courseCode = courseNameHashMap.get(closestCourseName);
        return courseCode;
    }

    private String findClosestCourseName(String courseRequestString) {
        StringMatcher stringMatcher = new LevenshteinDistance();
        StringMatcher checker = new WordLevenshteinDistance();
        String closestCourseName = null;
        String closestCourseNameWords = null;
        final int minThresholdWordChecker = 12;
        int minDistance = Integer.MAX_VALUE;
        int minDistanceWords = Integer.MAX_VALUE;


        for (String courseName : courseNameHashMap.keySet()) {
            int currentDistance = stringMatcher.calculate(courseRequestString, courseName);
            if (currentDistance <= minThresholdWordChecker) { // inconsistent course description
                if (currentDistance < minDistance) { // update
                    closestCourseName = courseName;
                    minDistance = currentDistance;
                }
            } else { //distance is too big for spelling error.
                int wordsCurrentDistance = checker.calculate(courseRequestString, courseName);
                if (wordsCurrentDistance < minDistanceWords) {
                    int numOfEquals = getNumberOfEqualWords(courseRequestString, courseName);
                    if (numOfEquals > 1) { // at least 2 words are the same. => common grounds.
                        closestCourseNameWords = courseName;
                        minDistanceWords = wordsCurrentDistance;
                    }
                }
            }
        }
        if (closestCourseName != null && minDistance < minThresholdWordChecker) {
            logger.warn("course doesn't exist:\n" +
                    "{}\n" +
                    "{}\n" +
                    "is the closest\n" +
                    "in distance: {} by {} algorithm.\n", courseRequestString, closestCourseName, minDistance, stringMatcher.getClass().getSimpleName());
        }
        if (closestCourseNameWords != null && minDistance > minThresholdWordChecker) {
            logger.debug("course doesn't exist:\n" +
                    "{}\n" +
                    "{}\n" +
                    "is the closest\n" +
                    "in distance: {} by {} algorithm.\n", courseRequestString, closestCourseNameWords, minDistanceWords, checker.getClass().getSimpleName());
            return closestCourseNameWords;
        }
        if (closestCourseName == null && closestCourseNameWords == null) {
            logger.error("closest course name wasn't found for {}.", courseRequestString);
        }
        return closestCourseName;
    }

    private int getNumberOfEqualWords(String closestCourseNameWords, String courseName) {
        int equalWords = 0;
        String connectors = "\\(|\\)|ו";
        //TODO: can be improved by removing prefixes and suffixes.('ו'/...)
        try {
            if (!courseName.isEmpty() && !closestCourseNameWords.isEmpty()) {
                String[] courseWords = courseName.split(" ");
                for (String word : courseWords) {
                    if (closestCourseNameWords.contains(word))
                        equalWords++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return equalWords;

    }

    //תכנות אלגוריתמי ב- java  או	פתוח תכנה מתקדם II
    private List<List<String>> parseRequestsList(String cleanRequests) {
        String[] splitWordsArray = {" או ", "/", " או\t", "\\n", "\\t", ",", "."};
        LinkedList<List<String>> courseRequests = new LinkedList<>();
        String[] requests = cleanRequests.split("[\\n\\t,.]| או\t");

        return getCourseRequestsList(courseRequests, requests);
    }

    private List<List<String>> getCourseRequestsList(LinkedList<List<String>> courseRequests, String[] requests) {
        List<String> requestsList = Arrays.stream(requests).filter(s -> !s.isEmpty()).map(String::trim).collect(Collectors.toList());
        int requestListIndex = 0;
        while (requestListIndex < requestsList.size()) {
            String requestString = requestsList.get(requestListIndex);
            LinkedList<String> currentRequestList = new LinkedList<>();
            if (requestString.contains("/") || requestString.contains(" או ")) {
                currentRequestList = new LinkedList<>(Arrays.asList(requestString.split("/|( או )")));
                currentRequestList.replaceAll(String::trim);
                for (int i = 0; i < currentRequestList.size(); i++) {
                    String courseString = currentRequestList.get(i);
                    int courseStringLength = courseString.length();
                    int COURSE_NAME_LENGTH_THRESHOLD = 50;
                    if (courseStringLength > COURSE_NAME_LENGTH_THRESHOLD) { // can't happen to more than two courses at the same time.
                        LinkedList<String> resultCourses = getLongCourses(courseString).stream().map(String::trim).collect(Collectors.toCollection(LinkedList::new));
                        currentRequestList.addFirst(resultCourses.get(resultCourses.size() - 1)); // add correct course
                        currentRequestList.remove(1); // remove long string course.

                        List<String> misplacedCourseList = new LinkedList<>();
                        misplacedCourseList.add(resultCourses.getFirst());
                        logger.debug("long string contains courses: = " + resultCourses);
                        if (requestListIndex == 0) {
                            courseRequests.addFirst(misplacedCourseList);
                        } else {
                            courseRequests.add(requestListIndex, misplacedCourseList);
                        }
                    }
                }
            } else {
                currentRequestList.add(requestString);
            }
            courseRequests.add(currentRequestList);

            requestListIndex++;
        }
        return courseRequests;
    }

    private List<String> getLongCourses(String longCourseNameString) {
        //TODO: need fix, split the longCourseNameString to the courses from namehashmap.
        LinkedList<String> courses = new LinkedList<>();
        StringBuilder sb = new StringBuilder(longCourseNameString);
        StringBuilder fixedSb;

        for (String courseName : courseNameHashMap.keySet()) {

            int startIndex = sb.toString().indexOf(courseName);
            if (startIndex != -1) {
                int courseNameLength = courseName.length();
                fixedSb = sb.delete(startIndex, startIndex + courseNameLength);
                if (startIndex != 0)
                    courses.addLast(courseName);
                else
                    courses.addFirst(courseName);
                sb = fixedSb;
                if (sb.length() < 2)
                    break;
            }
        }
        return courses;
    }

}
