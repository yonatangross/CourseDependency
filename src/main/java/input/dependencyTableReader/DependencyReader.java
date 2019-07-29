package input.dependencyTableReader;

import business.courseManagement.DefaultCourseRepository;

public interface DependencyReader {
    DefaultCourseRepository.SchoolType getSchoolType();

    String[][] getDependenciesTable();
}
