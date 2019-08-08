package business.util;

import java.util.List;

public final class UtilityPrinting {
    private UtilityPrinting() {
    }

    public static String listToString(List<?> list) {
        StringBuilder result = new StringBuilder();
        for (Object o : list) {
            result.append("\n").append(o);
        }
        return result.toString();
    }

    public static String formattedListToString(List<?> list) {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        for (Object o : list) {
            result.append("\t").append(o.toString());
        }

        return result.toString();
    }
}
