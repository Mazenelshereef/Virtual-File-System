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

    public boolean parseFile(){
        VirtualFileSystem system = VirtualFileSystem.getInstance();
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
            //check if it is empty
            if (fileContent.equals("")) {
                return false;
            }
            //parse file
            String[] lines = fileContent.split("\n");
            //first line is the directory structure
            String directoryStructure = lines[0];
            Directory currentDirectory = system.getRoot();
            int startIndex = 1;
            for (int i = 5; i < directoryStructure.length(); i++) {
                if (directoryStructure.charAt(i) == '(')
                {
                    String folderName = directoryStructure.substring(startIndex, i);
                    Directory directory = new Directory(folderName);
                    currentDirectory.addSubDirectory(directory);
                    currentDirectory = currentDirectory.getSubDirectories().get(currentDirectory.getSubDirectories().size() - 1);
                    startIndex = i + 1;
                }
                else if (directoryStructure.charAt(i) == ')')
                {
                    if (i > startIndex)
                    {
                        String fileName = directoryStructure.substring(startIndex, i);
                        MyFile fileToAdd = new MyFile(fileName);
                        currentDirectory.addFile(fileToAdd);
                    }
                    currentDirectory = currentDirectory.getParent();
                }
                else if(directoryStructure.charAt(i) == ',')
                {
                    if (directoryStructure.charAt(i-1) != ')')
                    {
                        String fileName = directoryStructure.substring(startIndex, i);
                        MyFile fileToAdd = new MyFile(fileName);
                        currentDirectory.addFile(fileToAdd);
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

            //remaining lines are the files allocated blocks
            for (int i = 2; i < lines.length; i++) {
                String filePath = lines[i].substring(0, lines[i].indexOf(" "));
                String fileBlocks = lines[i].substring(lines[i].indexOf(" ") + 1);
                ArrayList<Integer> fileBlockList = new ArrayList<Integer>();
                for (int j = 0; j < fileBlocks.length(); j+=2) {
                    fileBlockList.add(Integer.parseInt(fileBlocks.substring(j, j + 1)));
                }
                //get the file with path
                MyFile fileToAdd = system.getFile(filePath);
                //add the blocks to the file
                if (fileToAdd != null)
                    fileToAdd.setAllocatedBlocks(fileBlockList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateFile(){
        //overwrite file
        VirtualFileSystem system = VirtualFileSystem.getInstance();
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
            //write files allocated blocks
            for (MyFile fileToWrite : system.getAllFiles()) {
                fileWriter.write(fileToWrite.getFilePath() + " ");
                for(int i = 0; i < fileToWrite.getAllocatedBlocks().size(); i++) {
                    fileWriter.write(fileToWrite.getAllocatedBlocks().get(i).toString());
                    if (i < fileToWrite.getAllocatedBlocks().size() - 1)
                        fileWriter.write(" ");
                }
                fileWriter.write('\n');
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
