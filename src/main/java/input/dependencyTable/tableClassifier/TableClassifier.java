package input.dependencyTable.tableClassifier;

import input.dependencyTable.TableColumn;
import input.dependencyTable.TableDirection;
import input.dependencyTable.TableType;

public interface TableClassifier {

    int getColumnNumber(TableColumn tableColumn);

    TableType getTableType();

    boolean hasRequests();

    TableDirection getTableDirection();
}
