package Interpreters.DependencyFileReaders;

import CourseManagement.CourseManager;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DocxDependencyFileReader extends DependencyFileReader {
    DocxDependencyFileReader(File file) {
        super(file);
    }

    public void readFile() {
        try {
            logger.info("start of file reading");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            Iterator<IBodyElement> bodyElementIterator = document.getBodyElementsIterator();
            while (bodyElementIterator.hasNext()) {
                IBodyElement element = bodyElementIterator.next();
                if (schoolType == null)
                    schoolType = getSchoolType(element);
                if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
                    List<XWPFTable> tableList = element.getBody().getTables();
                    XWPFTable tableRaw = tableList.get(0);
                    dependenciesTable = new String[tableRaw.getNumberOfRows()][];
                    List<XWPFTableRow> rows = tableRaw.getRows();
                    int rowIndex = 0;
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> tableCells = row.getTableCells();
                        dependenciesTable[rowIndex] = new String[tableCells.size()];

                        for (int i = 0; i < tableCells.size(); i++) {
                            XWPFTableCell tableCell = tableCells.get(i);
                            dependenciesTable[rowIndex][i] = tableCell.getTextRecursively();
                        }
                        rowIndex++;
                    }
                }
            }
            logger.info("end of file reading");
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CourseManager.SchoolType getSchoolType(IBodyElement element) {
        CourseManager.SchoolType schoolType = null;
        logger.info("start schoolType reading");
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
                if (schoolName.contains("מדעי המחשב")) {
                    schoolType = CourseManager.SchoolType.COMPUTER_SCIENCE;
                }
            }
        }
        logger.info("end schoolType reading");

        return schoolType;
    }
}
