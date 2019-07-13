package CourseManagement;

import Algorithms.StringMatchers.LevenshteinDistance;
import Algorithms.StringMatchers.StringMatcher;
import Algorithms.StringMatchers.WordLevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseManager {
    private static final int CODE_COL_NUMBER = 0;
    private static final int NAME_COL_NUMBER = 1;
    private static final int PREREQUISITE_COL_NUMBER = 2;
    private static final int PARALLELREQUESTS_COL_NUMBER = 3;
    private final Logger logger = LoggerFactory.getLogger(CourseManager.class);
    private final int courseNameLengthThreshold = 50;
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
        readBasicDetailsFromTable(dependenciesTable);
        fillRequestsColumnsFromTable(dependenciesTable);
    }

    private void readBasicDetailsFromTable(String[][] dependenciesTable) {
        for (int i = 1; i < dependenciesTable.length; i++) {
            String[] courseRow = dependenciesTable[i];
            List<Course> currentCourses = readBasicDetailsFromTableRow(courseRow);
            assert currentCourses != null;
            for (Course course : currentCourses) {
                courseNameHashMap.put(course.getName(), course.getCode());
                courseHashMap.put(course.getCode(), course);
            }
        }
    }

    private void fillRequestsColumnsFromTable(String[][] dependenciesTable) {
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
                logger.error(e.toString() + " " + e.getCause());
                e.printStackTrace();
            }
        }
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
                Course course = courseHashMap.get(courseCode);
                identicalRequestList.add(course);
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
        final int courseNameLengthThreshold = 50;
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
                    if (courseStringLength > courseNameLengthThreshold) { // can't happen to more than two courses at the same time.
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


    public enum SchoolType {
        COMPUTER_SCIENCE, PSYCHOLOGY
    }
}


