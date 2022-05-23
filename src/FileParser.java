import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

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
            int startIndex = 1;
            for (int i = 1; i < directoryStructure.length(); i++) {
                if (directoryStructure.charAt(i) == '(')
                {
                    String folderName = directoryStructure.substring(startIndex, i);
                    currentDirectory.addDirectory(folderName);
                    currentDirectory = currentDirectory.getSubDirectories().get(currentDirectory.getSubDirectories().size() - 1);
                    startIndex = i + 1;
                }
                else if (directoryStructure.charAt(i) == ')')
                {
                    if (i > startIndex)
                    {
                        String fileName = directoryStructure.substring(startIndex, i);
                        currentDirectory.addFile(fileName);
                    }
                    currentDirectory = currentDirectory.getParent();
                }
                else if(directoryStructure.charAt(i) == ',')
                {
                    if (directoryStructure.charAt(i-1) != ')')
                    {
                        String fileName = directoryStructure.substring(startIndex, i);
                        currentDirectory.addFile(fileName);
                        startIndex = i + 1;
                    }
                }
            }

            //second line is the empty blocks of the virtual disk
            String emptyBlocks = lines[1];
            ArrayList<Integer> emptyBlockList = new ArrayList<Integer>();
            for (int i = 0; i < emptyBlocks.length(); i++) {
                emptyBlockList.add(Integer.parseInt(emptyBlocks.substring(i, i + 1)));
            }
            system.setMemory(emptyBlockList);

            //remaining lines are the files

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFile(){
        //overwrite file
        VirtualFileSystem system = new VirtualFileSystem();
        File file = new File("DiskStructure.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            //write directory structure
            fileWriter.write(system.getRoot().getDirectoryName() + "(");
            fileWriter.write(system.getRoot().getDirectoryStructure());
            fileWriter.write(")\n");
            //write empty blocks
            fileWriter.write(system.getMemory().toString());
            fileWriter.write('\n');
            //write files
            
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
