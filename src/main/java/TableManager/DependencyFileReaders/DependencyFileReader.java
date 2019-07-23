package TableManager.DependencyFileReaders;

import CourseManagement.CourseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public abstract class DependencyFileReader {
    final Logger logger;
    File file;

    CourseManager.SchoolType schoolType = null;
    String[][] dependenciesTable = null;

    DependencyFileReader(File file) {
        this.file = file;
        logger = LoggerFactory.getLogger(getClass());
    }

    public CourseManager.SchoolType getSchoolType() {
        return schoolType;
    }

    public String[][] getDependenciesTable() {
        return dependenciesTable;
    }

    public abstract void readFile();
}


