package Interpreters;

import courseManager.CourseManager;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class dependencyFileReader {
    private CourseManager.SchoolType schoolType;
    private String[][] dependenciesTable;
    private File file = null;

    public dependencyFileReader() {
        schoolType = null;
    }

    public CourseManager.SchoolType getSchoolType() {
        return schoolType;
    }

    public String[][] getDependenciesTable() {
        return dependenciesTable;
    }

    public static void readFileType(String filePath) {
        file = new File(filePath);
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        if (extension.equals("docx"))
            dependencyFileReader docxDependencyFileReader = new DocxDependencyFileReader();


        else {
            System.out.println("unhandled file type" + extension);
        }
    }



    private CourseManager.SchoolType getSchoolType(IBodyElement element) {
        List<XWPFParagraph> paragraphs = element.getBody().getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getText();
            String[] split = text.split("\n");
            List<String> collect = Arrays.stream(split).filter(s -> s.contains("בית הספר")).collect(Collectors.toList());
            if (collect.size() != 1) {
                System.out.println("Error parsing school name");
            } else {
                String schoolName = collect.get(0).replace("בית ספר ל", "");
                //TODO:  schoolNamesMap.get(schoolName);
                if (schoolName.contains("מדעי המחשב"))
                    return CourseManager.SchoolType.COMPUTER_SCIENCE;
            }
        }
        return null;
    }


}


