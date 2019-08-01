package business.util;

import java.util.List;

public final class UtilityPrinting {
    private UtilityPrinting () {
    }

    public static String listToString(List<?> list) {
        StringBuilder result = new StringBuilder();
        for (Object o : list) {
            result.append("\n").append(o);
        }
        return result.toString();
    }
}
