package TableManager.DependencyFileReaders;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;

public class DependencyFileReaderFactory {
    private final Logger logger = LoggerFactory.getLogger(DependencyFileReaderFactory.class);
    private HashMap<String, Creator> dependencyFileReaderCreator;

    public DependencyFileReaderFactory() {
        dependencyFileReaderCreator = new HashMap<>();
        dependencyFileReaderCreator.put("docx", DocxDependencyFileReader::new);
        dependencyFileReaderCreator.put("pdf", PDFDependencyFileReader::new);
    }

    public DependencyFileReader readFileType(String filePathString) {
        try {
            File file = new File(filePathString);
            if (file.exists() && file.canRead() && !file.isDirectory()) {
                String extension = "";
                int i = filePathString.lastIndexOf('.');
                if (i > 0) {
                    extension = filePathString.substring(i + 1);
                }

                return createDependencyFileReader(extension, file);
            } else {
                logger.error("File {} doesn't exist or not readable or is a directory.", filePathString);
            }
        } catch (Exception e) {
            logger.error("{}", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    private DependencyFileReader createDependencyFileReader(String fileTypeString, File file) {
        Creator creator = dependencyFileReaderCreator.get(fileTypeString);
        if (creator != null)
            return creator.create(file);
        else {
            logger.error("{} file type is not handled.", fileTypeString);
            return null;
        }
    }

    private interface Creator {
        DependencyFileReader create(File file);
    }
}

