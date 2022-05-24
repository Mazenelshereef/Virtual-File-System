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

    public boolean parseFile() throws Exception{
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
            int startIndex = 5;
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
                    startIndex = i + 1;
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
            for (int i = 0; i < emptyBlocks.length() - 1; i++) {
                emptyBlockList.add(Integer.parseInt(emptyBlocks.substring(i, i + 1)));
            }
            emptyBlockList.add(Integer.parseInt(emptyBlocks.substring(emptyBlocks.length() - 1)));
            system.setMemory(emptyBlockList);

            //remaining lines are the files allocated blocks
            for (int i = 2; i < lines.length; i++) {
                String filePath = lines[i].substring(0, lines[i].indexOf(" "));
                lines[i] = lines[i].substring(lines[i].indexOf(" ") + 1);
                String allocationType = lines[i].substring(0, lines[i].indexOf(" "));
                lines[i] = lines[i].substring(lines[i].indexOf(" ") + 1);
                String[] blocks = lines[i].split(" ");
                ArrayList<Integer> fileBlockList = new ArrayList<Integer>();
                AllocationType allocationType2;
                if (allocationType.equals("C")){
                    for (int j = 0; j < blocks.length; j++) {
                        fileBlockList.add(Integer.parseInt(blocks[j]));
                    }
                    allocationType2 = AllocationType.CONTIGUOUS;
                } else if (allocationType.equals("L")){
                    //add the start block
                    fileBlockList.add(Integer.parseInt(blocks[0]));
                    //add the end block
                    fileBlockList.add(Integer.parseInt(blocks[1]));
                    //add the links
                    for (int j = 2; j < blocks.length; j++) {
                        fileBlockList.add(Integer.parseInt(blocks[j].substring(0, blocks[j].indexOf("->"))));
                        fileBlockList.add(Integer.parseInt(blocks[j].substring(blocks[j].indexOf("->") + 2)));
                    }
                    allocationType2 = AllocationType.LINKED;
                } else { //indexed allocation
                    //add the index block
                    fileBlockList.add(Integer.parseInt(blocks[0]));
                    //add the allocated blocks
                    String indexedBlocks = blocks[1].substring(1, blocks[1].length() - 1);
                    String[] allocatedBlocks = indexedBlocks.split(",");
                    for (int j = 0; j < allocatedBlocks.length; j++) {
                        fileBlockList.add(Integer.parseInt(allocatedBlocks[j]));
                    }
                    allocationType2 = AllocationType.INDEXED;
                }
                //get the file with path
                MyFile fileToAdd = system.getFile(filePath);
                //add the blocks to the file
                if (fileToAdd != null)
                {
                    fileToAdd.setAllocatedBlocks(fileBlockList);
                    fileToAdd.setAllocationType(allocationType2);
                }      
            }
        } catch (Exception e) {
            throw new Exception("Error parsing file: " + e.toString());
        }
        return true;
    }

    public void updateFile() throws Exception {
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
            for (Integer block : system.getMemory()) {
                fileWriter.write(block.toString());
            }
            fileWriter.write('\n');
            //write files allocated blocks
            for (MyFile fileToWrite : system.getAllFiles()) {
                fileWriter.write(fileToWrite.getFilePath() + " ");
                if (fileToWrite.getAllocationType() == AllocationType.CONTIGUOUS)
                {
                    fileWriter.write("C ");
                    for (Integer block : fileToWrite.getAllocatedBlocks()) {
                        fileWriter.write(block.toString() + " ");
                    }
                }
                else if (fileToWrite.getAllocationType() == AllocationType.LINKED)
                {
                    fileWriter.write("L ");
                    fileWriter.write(fileToWrite.getAllocatedBlocks().get(0).toString() + " ");
                    fileWriter.write(fileToWrite.getAllocatedBlocks().get(1).toString() + " ");
                    for (int i = 2; i < fileToWrite.getAllocatedBlocks().size(); i += 2) {
                        fileWriter.write(fileToWrite.getAllocatedBlocks().get(i).toString() + "->" + fileToWrite.getAllocatedBlocks().get(i + 1).toString() + " ");
                    }
                }
                else if (fileToWrite.getAllocationType() == AllocationType.INDEXED)
                {
                    fileWriter.write("I ");
                    fileWriter.write(fileToWrite.getAllocatedBlocks().get(0).toString() + " [");
                    for (int i = 1; i < fileToWrite.getAllocatedBlocks().size(); i++) {
                        fileWriter.write(fileToWrite.getAllocatedBlocks().get(i).toString());
                        if (i != fileToWrite.getAllocatedBlocks().size() - 1)
                        {
                            fileWriter.write(",");
                        }
                    }
                    fileWriter.write("]");
                }
                fileWriter.write('\n');
            }
            fileWriter.close();
        } catch (Exception e) {
            throw new Exception("Error writing file: " + e.toString());
        }
    }

}
