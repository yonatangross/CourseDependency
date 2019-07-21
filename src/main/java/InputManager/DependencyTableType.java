package InputManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DependencyTableType {
    private final Logger logger = LoggerFactory.getLogger(DependencyTableType.class);

    private DependenciesTableType dependenciesTableType = null;


    public DependenciesTableType getDependencyTableType(String[][] dependenciesTable) {
        int numberOfColumns = dependenciesTable[0].length;
        if (numberOfColumns == 2) {
            dependenciesTableType = DependenciesTableType.CODE_AND_NAME;
        } else if (numberOfColumns == 4) {
            dependenciesTableType = DependenciesTableType.PRE_AND_PARALLEL;

        } else if (numberOfColumns == 5)
            dependenciesTableType = DependenciesTableType.PRE_PARA_HEARING;

        else {
            logger.error("Unhandled dependency table.");
        }

        return  dependenciesTableType;
    }


    private enum DependenciesTableType {
        CODE_AND_NAME, PRE_AND_PARALLEL, PRE_PARA_HEARING
    }
}
