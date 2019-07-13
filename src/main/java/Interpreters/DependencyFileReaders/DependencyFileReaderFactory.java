package Interpreters.DependencyFileReaders;


import java.io.File;

public class DependencyFileReaderFactory {
    public DocxDependencyFileReader readFileType(String filePathString) {
        try {
            File file = new File(filePathString);
            if (file.exists() && file.canRead() && !file.isDirectory()) {
                String extension = "";
                int i = filePathString.lastIndexOf('.');
                if (i > 0) {
                    extension = filePathString.substring(i + 1);
                }
                if (extension.equals("docx")) {
                    return new DocxDependencyFileReader(file);
                } else {
                    System.out.println("unhandled file type" + extension);
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
