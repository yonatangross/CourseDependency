package business.algorithms.stringMatchers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class AbbreviationFinder {
    private AbbreviationFinder() {
    }

    public static boolean isAbbreviationString(String possibleAbbreviationString) {
        Set<Character> abbreviationStringsSigns = new HashSet<>();
        boolean isAbbreviationString = false;
        abbreviationStringsSigns.add('\"');

        char[] possibleAbbreviationLetters = possibleAbbreviationString.toCharArray();
        for (char possibleAbbreviationLetter : possibleAbbreviationLetters) {
            if (abbreviationStringsSigns.contains(possibleAbbreviationLetter)) {
                isAbbreviationString = true;
                break;
            }
        }
        return isAbbreviationString;
    }

    public static String getCourseNameAbbreviated(String possibleAbbreviationString, HashMap<String, String> courseNameHashMap) {
        for (String courseName : courseNameHashMap.keySet()) {
            
        }

        for (int ch = 0; ch < possibleAbbreviationString.length(); ch++) {
            if (possibleAbbreviationString)
        }
        return false;
    }
}
// for example: פת"מ 1