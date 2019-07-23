package TableManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableClassifier {

    private final Logger logger = LoggerFactory.getLogger(TableClassifier.class);
    private TableDirection tableDirection;
    private TableType tableType;

    TableClassifier(String[][] dependenciesTable) {
        final int FIRST_DATA_ROW = 1;
        calculateTableDirection(dependenciesTable[FIRST_DATA_ROW]);
        setTableType(dependenciesTable[FIRST_DATA_ROW].length);
    }

    private void calculateTableDirection(String[] tableRow) {
        if (tableRow[0].matches("[0-9]+")) {
            tableDirection = TableDirection.LEFT_TO_RIGHT;
        } else {
            tableDirection = TableDirection.RIGHT_TO_LEFT;
        }
    }

    int getColumnNumber(TableColumn tableColumn) {
        if (this.tableDirection == TableDirection.LEFT_TO_RIGHT) {
            return tableColumn.getColumnNumber();
        } else // this.tableDirection == TableDirection.RIGHT_TO_LEFT
            return this.tableType.getNumberOfColumns() - tableColumn.getColumnNumber();
    }

    public TableType getTableType() {
        return this.tableType;
    }

    private void setTableType(int numberOfColumns) {
        tableType = TableType.getTableType(numberOfColumns);
        if (tableType == null)
            logger.error("Unhandled table type.");
    }

    public TableDirection getTableDirection() {
        return this.tableDirection;
    }
}
