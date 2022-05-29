import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public boolean parseCapabilitiesFile() throws Exception {
        VirtualFileSystem system = VirtualFileSystem.getInstance();
        File file = new File("capabilities.txt");
        try {
            FileReader fileReader = new FileReader(file);
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                sb.append((char) c);
            }
            fileReader.close();
            String fileContent = sb.toString();
            // check if it is empty
            if (fileContent.equals("")) {
                return false;
            }
            String[] lines = fileContent.split("\\r?\\n");

            List<String> stringLines = new ArrayList<String>(Arrays.asList(lines));

            for (int i = 0; i < stringLines.size(); i++) {
                String folderPath = stringLines.get(i).substring(0, lines[i].indexOf(","));
                if(folderPath.length()+1 == lines[i].length()){
                    continue;
                }
                Directory folderToManageAccess = system.getDirectory(folderPath);
                String NameCap = stringLines.get(i).substring(lines[i].indexOf(",") + 1);// ignore folder path
                String[] content = NameCap.split(",");// split every word in the line

                List<String> stringContent = new ArrayList<String>(Arrays.asList(content));
                for (int j = 0; j < stringContent.size(); j++) {
                    if (j % 2 == 0 && stringContent.get(j + 1).equals("10")) {
                        folderToManageAccess.addToUsersCanCreate(system.getUser(stringContent.get(j)));
                    } else if (j % 2 == 0 && stringContent.get(j + 1).equals("11")) {
                        folderToManageAccess.addToUsersCanCreate(system.getUser(stringContent.get(j)));
                        folderToManageAccess.addToUsersCanDelete(system.getUser(stringContent.get(j)));
                    } else if (j % 2 == 0 && stringContent.get(j + 1).equals("01")) {
                        folderToManageAccess.addToUsersCanDelete(system.getUser(stringContent.get(j)));
                    } else if (j % 2 == 0 && stringContent.get(j + 1).equals("00")) {
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error parsing capabilities file: " + e.toString()); // 1 2 1 2 1 2
        }
        return true;
    }
    public  boolean updateCapabilitiesFile() throws Exception{
        VirtualFileSystem system = VirtualFileSystem.getInstance();
        File file = new File("capabilities.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < system.getAllDirectories().size(); i++) {
                String  folderPath = "";
                Directory folderToManageAccess = system.getAllDirectories().get(i);
                if(folderToManageAccess== system.getRoot()){
                      folderPath = system.getRoot().getDirectoryName();
                }
                else{// get the path till find the root directory and add it to the path
                    Directory temp = folderToManageAccess;
                    while(temp.getParent()!=null){
                        folderPath = temp.getDirectoryName()+"/"+folderPath;
                        temp = temp.getParent();
                    }
                    folderPath="root/"+folderPath;
                    //remove the last "/"
                    folderPath = folderPath.substring(0,folderPath.length()-1);
                   
                    // folderPath = folderToManageAccess.getParent().getDirectoryName() + "/" + folderToManageAccess.getDirectoryName();
                 }
                String NameCap = "";
                for (int j = 0; j < folderToManageAccess.getUsersCanCreate().size(); j++) {
                    User user = folderToManageAccess.getUsersCanCreate().get(j);
                    if( folderToManageAccess.getUsersCanDelete().contains(user))
                    NameCap += user.getUserName() + "," + "11" + ",";
                    else{
                        NameCap += user.getUserName() + "," + "10" + ",";
                    }
                }
                 for (int j = 0; j < folderToManageAccess.getUsersCanDelete().size(); j++) {
                    User user = folderToManageAccess.getUsersCanDelete().get(j);
                    if( folderToManageAccess.getUsersCanCreate().contains(user)){}
                    else{
                        NameCap += user.getUserName() + "," + "01" + ",";
                    }
                } 
                if( NameCap.equals("")){
                    fileWriter.write(folderPath + "," + NameCap + "\n");
                }
                else{
                 
                    NameCap = NameCap.substring(0, NameCap.length()-1);
                    fileWriter.write(folderPath + "," + NameCap  + "\n");
                }
               
            }
            fileWriter.close();
        } catch (Exception e) {
            throw new Exception("Error updating capabilities file: " + e.toString());
        }
        return true;

    }


        public  boolean parseUsersFile() throws Exception {
            VirtualFileSystem system = VirtualFileSystem.getInstance();
            File file = new File("user.txt");
        try {
                FileReader fileReader = new FileReader(file);
                int c;
                StringBuilder sb = new StringBuilder();
                while ((c = fileReader.read()) != -1) {
                    sb.append((char) c);
                }
                fileReader.close();
                String fileContent = sb.toString();
                // check if it is empty
                if (fileContent.equals("")) {
                    return false;
                }
                String[] lines = fileContent.split("\n");
                List<String> stringLines = new ArrayList<String>(Arrays.asList(lines));
        
                for (int i = 1; i < stringLines.size(); i++) {
                    String[] userNamePassword = stringLines.get(i).split(",");
                    system.addUser(userNamePassword[0], userNamePassword[1]);
        
                }
        } catch (Exception e) {
            throw new Exception("Error parsing Users file: " + e.toString());
        }
            return true;
    
        }
        
        public void updateUsersFile() throws Exception {
            // overwrite file
            VirtualFileSystem system = VirtualFileSystem.getInstance();
            File file = new File("user.txt");
    
            try (FileWriter fileWriter = new FileWriter(file)) {
                for (int i = 0; i < system.getSystemUsers().size(); i++) {
    
                    fileWriter.write(system.getSystemUsers().get(i).getUserName() + ","
                            + system.getSystemUsers().get(i).getPassword() + "\n");
    
                }
            }catch (Exception e) {
                throw new Exception("Error writing User file: " + e.toString());}
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
                    }
                    startIndex = i + 1;
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
