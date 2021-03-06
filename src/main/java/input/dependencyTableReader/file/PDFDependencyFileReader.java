package input.dependencyTableReader.file;

//import com.giaybac.traprange.PDFTableExtractor;
//import com.giaybac.traprange.business.entity.Table;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PDFDependencyFileReader extends DependencyFileReader{
    PDFDependencyFileReader(File file) {
        super(file);
    }

    public void readFile() {
        try {
            //Loading an existing document
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());


//            PDFTableExtractor extractor = new PDFTableExtractor();
//            List<Table> tables = extractor.setSource(file)
//                    .extract();
//            String html = tables.getTableType(0).toString();//table in html format





            //Closing the document
            logger.info("end of file reading");
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
