package Interpreters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DocxDependencyFileReader extends dependencyFileReader {

    public void readFile() {
        try {
            file = new File();
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
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
