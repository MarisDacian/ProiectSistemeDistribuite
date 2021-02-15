package fileexplorer;


/**
 * Has the role of exploring the contents of a predefined directory and managing the files stored within.
 *
 * @author Leni
 */
public interface FileExplorer {

    String readFileContent(String name);
    
    String listAllFiles();
    
    String deleteFile(String name);
    
    String createFile(String name);
    
    String writeToFile(String name,String message);
    
    String createAndWriteToFile(String name,String message);
}
