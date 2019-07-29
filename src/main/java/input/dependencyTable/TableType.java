package input.dependencyTable;

import java.util.HashMap;
import java.util.Map;

public enum TableType {
    CODE_AND_NAME(2),
    PRE_AND_PARALLEL(4),
    PRE_PARA_HEARING(5);

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<Integer, TableType> lookup;

    static {
        lookup = new HashMap<>();
        for (TableType tableType : TableType.values()) {
            lookup.put(tableType.getNumberOfColumns(), tableType);
        }
    }

    private final int numberOfColumns;

    // enum constructor - cannot be public or protected
    TableType(int columnNumber) {
        this.numberOfColumns = columnNumber;
    }

    public static TableType getTableType(int numberOfColumns) {
        return lookup.get(numberOfColumns);
    }

    // getter method
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

}
