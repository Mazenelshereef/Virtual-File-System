import java.io.File;
import java.io.FileReader;

public class FileParser {
    private static FileParser instance;

    private FileParser() {
    }

    public static FileParser getInstance() {
        if (instance == null) {
            instance = new FileParser();
        }
        return instance;
    }

    public void parseFile(){
        VirtualFileSystem system = new VirtualFileSystem();
        File file = new File("DiskStructure.txt");
        //read file
        try {
            FileReader fileReader = new FileReader(file);
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                sb.append((char) c);
            }
            fileReader.close();
            String fileContent = sb.toString();
            //parse file
            String[] lines = fileContent.split("\n");
            //first line is the directory structure
            String directoryStructure = lines[0];
            Directory currentDirectory = system.getRoot();
            int startIndex = 0;
            for (int i = 1; i < directoryStructure.length(); i++) {
                if (directoryStructure.charAt(i) == '(')
                {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
