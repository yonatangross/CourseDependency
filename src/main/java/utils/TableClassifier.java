package utils;

import TableManager.TableColumn;
import TableManager.TableDirection;
import TableManager.TableType;

public interface TableClassifier {

    int getColumnNumber(TableColumn tableColumn);

    TableType getTableType();

    boolean hasRequests();

    TableDirection getTableDirection();
}
