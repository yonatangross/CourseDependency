package input.dependencyTable;

public enum TableColumn {
    CODE(0),
    NAME(1),
    PRE_REQUESTS(2),
    PARALLEL_REQUESTS(3),
    HEAR_REQUESTS(4);

    // declaring private variable for getting values
    private int columnNumber;

    // enum constructor - cannot be public or protected
    TableColumn(int columnNumber) {

        this.columnNumber = columnNumber;
    }

    // getter method
    public int getColumnNumber() {
        return this.columnNumber;
    }
}