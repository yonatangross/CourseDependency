package business.courseManagement.courseFormating;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ClosestCourseFinder {
    // stores map from bad name to good name.
    HashMap<String, String> correctionCourseNameMap = new HashMap<>();

    static <K, V> String mapToString(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .map(
                        entry -> {
                            return "\n" + (entry.getKey() == map ? "(this Map)" : entry.getKey())
                                    + " :\n"
                                    + (entry.getValue() == map ? "(this Map)" : entry.getValue()) + "\n";
                        })
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
