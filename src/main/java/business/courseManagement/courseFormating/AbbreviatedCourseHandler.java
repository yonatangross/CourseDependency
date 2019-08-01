package business.courseManagement.courseFormating;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//TODO: Change class name
public class AbbreviatedCourseHandler {
    private static int abbreviationSignPosition;

    public AbbreviatedCourseHandler() {

    }

    public static boolean isAbbreviationString(String possibleAbbreviationString) {
        boolean isAbbreviationString = false;
        Set<Character> abbreviationSigns = new HashSet<>();
        abbreviationSigns.add('\"');

        char[] charArray = possibleAbbreviationString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (abbreviationSigns.contains(ch)) {
                abbreviationSignPosition = i;
                isAbbreviationString = true;
                break;
            }
        }
        return isAbbreviationString;
    }

    public String getCourseNameAbbreviated(String abbreviationString, HashMap<String, String> courseNameHashMap) {
        //TODO: finish algorithm or use database fixes.
        String[] words = abbreviationString.split(" ");


        String cleanAbbreviationString = abbreviationString.substring(0, abbreviationSignPosition) + abbreviationString
                .substring(abbreviationSignPosition + 1);

        char[] possibleAbbreviationLetters = cleanAbbreviationString.toCharArray();


        for (String courseName : courseNameHashMap.keySet()) {

        }
        return null;
    }
}
// for example: פת"מ 1