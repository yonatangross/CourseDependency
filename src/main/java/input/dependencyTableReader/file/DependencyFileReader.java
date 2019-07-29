package input.dependencyTableReader.file;

import business.courseManagement.DefaultCourseRepository;
import input.dependencyTableReader.DependencyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class DependencyFileReader implements DependencyReader {
    final Logger logger;
    File file;

    DefaultCourseRepository.SchoolType schoolType = null;
    String[][] dependenciesTable = null;

    DependencyFileReader(File file) {
        this.file = file;
        logger = LoggerFactory.getLogger(getClass());
    }

    public DefaultCourseRepository.SchoolType getSchoolType() {
        return schoolType;
    }

    public String[][] getDependenciesTable() {
        return dependenciesTable;
    }

    public abstract void readFile();
}


