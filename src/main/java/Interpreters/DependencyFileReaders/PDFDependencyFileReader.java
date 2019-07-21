package Interpreters.DependencyFileReaders;

//import com.giaybac.traprange.PDFTableExtractor;
//import com.giaybac.traprange.entity.Table;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
//            String html = tables.get(0).toString();//table in html format





            //Closing the document
            logger.info("end of file reading");
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
