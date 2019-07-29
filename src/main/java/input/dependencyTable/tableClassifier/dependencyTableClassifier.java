package input.dependencyTable.tableClassifier;

import input.dependencyTable.TableColumn;
import input.dependencyTable.TableDirection;
import input.dependencyTable.TableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class dependencyTableClassifier implements TableClassifier {

    private final Logger logger = LoggerFactory.getLogger(dependencyTableClassifier.class);
    private TableDirection tableDirection;
    private TableType tableType;

    public dependencyTableClassifier(String[][] dependenciesTable) {
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

    @Override
    public int getColumnNumber(TableColumn tableColumn) {
        if (this.tableDirection == TableDirection.LEFT_TO_RIGHT) {
            return tableColumn.getColumnNumber();
        } else // this.tableDirection == TableDirection.RIGHT_TO_LEFT
            return this.tableType.getNumberOfColumns() - tableColumn.getColumnNumber();
    }

    @Override
    public TableType getTableType() {
        return this.tableType;
    }

    private void setTableType(int numberOfColumns) {
        tableType = TableType.getTableType(numberOfColumns);
        if (tableType == null)
            logger.error("Unhandled table type.");
    }

    @Override
    public boolean hasRequests() {
        return (tableType == TableType.PRE_AND_PARALLEL || tableType == TableType.PRE_PARA_HEARING);
    }

    @Override
    public TableDirection getTableDirection() {
        return this.tableDirection;
    }
}
