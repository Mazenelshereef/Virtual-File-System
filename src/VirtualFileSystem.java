import java.security.interfaces.ECKey;
import java.util.ArrayList;

public class VirtualFileSystem {
    private static VirtualFileSystem instance;
    private int systemSizeInKB;
    private Allocator allocator;
    private ArrayList<Integer> memory;
    private Directory root;

    private VirtualFileSystem() {
        root = new Directory("root");
        memory = new ArrayList<Integer>();
    }

    public static VirtualFileSystem getInstance() {
        if (instance == null) {
            instance = new VirtualFileSystem();
        }
        return instance;
    }

    public void setSystemSizeInKB(int systemSizeInKB) {
        this.systemSizeInKB = systemSizeInKB;
        for (int i = 0; i < this.systemSizeInKB; i++) {
            memory.add(0);
        }
    }

    public void setMemory(ArrayList<Integer> storage) {
        memory = storage;
    }

    public ArrayList<Integer> getMemory() {
        return memory;
    }

    public void setAllocation(Allocator allocation) {
        this.allocator = allocation;
    }

    public Allocator getAllocation() {
        return allocator;
    }

    public Directory getRoot() {
        return root;
    }

    public MyFile getFile(String filePath) {
        // check that the main folder is "root"
        if (!filePath.substring(0, filePath.indexOf("/")).equals("root"))
            return null;
        else
            filePath = filePath.substring(filePath.indexOf("/") + 1);
        // get to the file
        Directory currentDirectory = root;
        for (int i = 0; i < filePath.length(); i++) {
            if (filePath.charAt(i) == '/') {
                String folderName = filePath.substring(0, i);
                currentDirectory = currentDirectory.getSubDirectory(folderName);
                if (currentDirectory == null)
                    return null;
                filePath = filePath.substring(i + 1);
            }
        }
        return currentDirectory.getFile(filePath);
    }

    public ArrayList<MyFile> getAllFiles() {
        ArrayList<MyFile> allFiles = new ArrayList<MyFile>();
        ArrayList<Directory> systemDirectories = new ArrayList<Directory>();
        systemDirectories.add(root);
        while (systemDirectories.size() > 0) {
            Directory currentDirectory = systemDirectories.get(0);
            systemDirectories.remove(0);
            for (int i = 0; i < currentDirectory.getSubDirectories().size(); i++) {
                systemDirectories.add(currentDirectory.getSubDirectories().get(i));
            }
            for (int i = 0; i < currentDirectory.getFiles().size(); i++) {
                allFiles.add(currentDirectory.getFiles().get(i));
            }
        }
        return allFiles;
    }

    public Directory getDirectory(String directoryPath) {
        if (directoryPath.equals("root"))
            return root;
        // check that the main folder is "root"
        if (!directoryPath.substring(0, directoryPath.indexOf("/")).equals("root"))
            return null;
        else
            directoryPath = directoryPath.substring(directoryPath.indexOf("/") + 1);
        // get to the folder
        Directory currentDirectory = root;
        for (int i = 0; i < directoryPath.length(); i++) {
            if (directoryPath.charAt(i) == '/') {
                String folderName = directoryPath.substring(0, i);
                currentDirectory = currentDirectory.getSubDirectory(folderName);
                if (currentDirectory == null)
                    return null;
                directoryPath = directoryPath.substring(i + 1);
            }
        }
        return currentDirectory.getSubDirectory(directoryPath);
    }

    void createFile(String filePath, int fileSize) throws Exception {
        //check that no file exists with the same path
        if (getFile(filePath) != null)
            throw new Exception("ERROR: File already exists with the same path!");
        Directory parentDirectory = getDirectory(filePath.substring(0, filePath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        ArrayList<Integer> allocatedBlocks = allocator.allocate(fileSize);//may throw an exception
        SpaceManager.getInstance().allocateMemory(allocatedBlocks);
        MyFile file = new MyFile(fileName, allocatedBlocks);
        parentDirectory.addFile(file);
    }

    void deleteFile(String filePath) throws Exception {
        Directory parentDirectory = getDirectory(filePath.substring(0, filePath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        MyFile file = parentDirectory.getFile(fileName);
        if (file == null)
            throw new Exception("ERROR: File does not exist!");
        for (int i = 0; i < file.getAllocatedBlocks().size(); i++) {
            memory.set(file.getAllocatedBlocks().get(i), 0);
        }
        parentDirectory.deleteFile(file);
    }

    void createFolder(String directoryPath) throws Exception {
        //check that no folder exists with the same path
        if (getDirectory(directoryPath) != null)
            throw new Exception("ERROR: Folder already exists with the same path!");
        Directory parentDirectory = getDirectory(directoryPath.substring(0, directoryPath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        String folderName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1);
        Directory folder = new Directory(folderName);
        parentDirectory.addSubDirectory(folder);
    }

    void deleteFolder(String directoryPath) throws Exception {
        Directory parentDirectory = getDirectory(directoryPath.substring(0, directoryPath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        String folderName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1);
        Directory folder = parentDirectory.getSubDirectory(folderName);
        if (folder == null)
            throw new Exception("ERROR: Folder does not exist!");
        parentDirectory.deleteSubDirectory(folder);
    }

}
