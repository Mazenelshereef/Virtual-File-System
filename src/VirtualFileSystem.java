import java.util.ArrayList;

public class VirtualFileSystem {
    private static VirtualFileSystem instance;
    private int systemSizeInKB;
    private Allocator allocator;
    private ArrayList<Integer> memory;
    private ArrayList<User> systemUsers;
    private Directory root;
    private User admin;
    private User currentUser;

    private VirtualFileSystem() {
        root = new Directory("root");
        memory = new ArrayList<Integer>();
        systemUsers = new ArrayList<User>();
        admin = new User("admin", "admin");
        systemUsers.add(admin);
        currentUser = admin;

    }

    public static VirtualFileSystem getInstance() {
        if (instance == null) {
            instance = new VirtualFileSystem();
        }
        return instance;
    }

    public ArrayList<User> getSystemUsers() {
        return systemUsers;
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

    public User getCurrentUser() {
        return currentUser;
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
    public ArrayList <Directory> getAllDirectories(Directory directory){
        ArrayList<Directory> directories = new ArrayList<Directory>();
        directories.add(directory);
        for (int i = 0; i < root.getSubDirectories().size(); i++) {
            directories.addAll(getAllDirectories(directory.getSubDirectories().get(i)));
        }
        return directories;
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
    public ArrayList<Directory> getAllDirectories() {
        ArrayList<Directory> allDirectories = new ArrayList<Directory>();
        ArrayList<Directory> systemDirectories = new ArrayList<Directory>();
        systemDirectories.add(root);
        while (systemDirectories.size() > 0) {
            Directory currentDirectory = systemDirectories.get(0);
            systemDirectories.remove(0);
            for (int i = 0; i < currentDirectory.getSubDirectories().size(); i++) {
                systemDirectories.add(currentDirectory.getSubDirectories().get(i));
            }
            allDirectories.add(currentDirectory);
        }
        return allDirectories;
    }
       
    
    

    public Directory getDirectory(String directoryPath) {
        if (directoryPath.equals("root"))
            return root;
        if (!directoryPath.contains("/"))
            return null;
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
        // check that no file exists with the same path
        if (getFile(filePath) != null)
            throw new Exception("ERROR: File already exists with the same path!");
        Directory parentDirectory = getDirectory(filePath.substring(0, filePath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        else if (!parentDirectory.checkIfCanCreate(currentUser) && currentUser != admin)
            throw new Exception("ERROR: This user can not create files in this directory!");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        ArrayList<Integer> allocatedBlocks = allocator.allocate(fileSize);// may throw an exception
        // set the allocation type of the file
        AllocationType allocationType;
        if (allocator instanceof ContiguousAllocator)
            allocationType = AllocationType.CONTIGUOUS;
        else if (allocator instanceof LinkedAllocator)
            allocationType = AllocationType.LINKED;
        else
            allocationType = AllocationType.INDEXED;
        // allocate the memory to the file
        SpaceManager.getInstance().allocateMemory(allocatedBlocks, allocationType);
        // create the file
        MyFile file = new MyFile(fileName, allocatedBlocks, allocationType);
        // add the file to the parent directory
        parentDirectory.addFile(file);
    }

    void deleteFile(String filePath) throws Exception {
        Directory parentDirectory = getDirectory(filePath.substring(0, filePath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        else if (!parentDirectory.checkIfCanDelete(currentUser) && currentUser != admin)
            throw new Exception("ERROR: This user can not delete files in this directory!");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        MyFile file = parentDirectory.getFile(fileName);
        if (file == null)
            throw new Exception("ERROR: File does not exist!");
        SpaceManager.getInstance().deallocateMemory(file.getAllocatedBlocks(), file.getAllocationType());
        parentDirectory.deleteFile(file);
    }

    void createFolder(String directoryPath) throws Exception {
        // check that no folder exists with the same path
        if (getDirectory(directoryPath) != null)
            throw new Exception("ERROR: Folder already exists with the same path!");
        Directory parentDirectory = getDirectory(directoryPath.substring(0, directoryPath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        else if (!parentDirectory.checkIfCanCreate(currentUser) && currentUser != admin)
            throw new Exception("ERROR: This user can not create folder in this directory!");
        String folderName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1);
        Directory folder = new Directory(folderName);
        folder.setParent(parentDirectory);
        parentDirectory.addSubDirectory(folder);
    }

    void deleteFolder(String directoryPath) throws Exception {
        Directory parentDirectory = getDirectory(directoryPath.substring(0, directoryPath.lastIndexOf("/")));
        if (parentDirectory == null)
            throw new Exception("ERROR: Parent directory does not exist!");
        else if (!parentDirectory.checkIfCanDelete(currentUser) && currentUser != admin)
            throw new Exception("ERROR: This user can not delete directories in this directory!");
        String folderName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1);
        Directory folder = parentDirectory.getSubDirectory(folderName);
        if (folder == null)
            throw new Exception("ERROR: Folder does not exist!");
        // delete all the files in the folder
        for (int i = 0; i < folder.getFiles().size(); i++) {
            MyFile file = folder.getFiles().get(i);
            SpaceManager.getInstance().deallocateMemory(file.getAllocatedBlocks(), file.getAllocationType());
        }
        // delete all the sub-folders in the folder
        for (int i = 0; i < folder.getSubDirectories().size(); i++) {
            Directory subFolder = folder.getSubDirectories().get(i);
            deleteFolder(directoryPath + "/" + subFolder.getDirectoryName());
        }
        parentDirectory.deleteSubDirectory(folder);
    }

    public void displayDiskStatus() {
        System.out.println("Disk Status:");
        ArrayList<Integer> freeBlocks = SpaceManager.getInstance().getFreeBlocks();
        System.out.println("Empty space: " + freeBlocks.size() + " blocks");
        for (int i = 0; i < freeBlocks.size(); i++) {
            System.out.print(freeBlocks.get(i) + " ");
        }
        System.out.println();
        ArrayList<Integer> usedBlocks = SpaceManager.getInstance().getUsedBlocks();
        System.out.println("Allocated space: " + usedBlocks.size() + " blocks");
        for (int i = 0; i < usedBlocks.size(); i++) {
            System.out.print(usedBlocks.get(i) + " ");
        }
        System.out.println();
    }

    public boolean checkIfUserNameExists(String user) {
        for (int i = 0; i < systemUsers.size(); i++) {
            if (systemUsers.get(i).getUserName().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String userName) {

        for (int i = 0; i < systemUsers.size(); i++) {
            if (systemUsers.get(i).getUserName().equals(userName)) {
                return systemUsers.get(i);
            }
        }
        return null;
    }

    public void addUser(String userName, String password) throws Exception {
        if (checkIfUserNameExists(userName) != false)
            throw new Exception("ERROR: User already exists!");
        if (currentUser != admin)
            throw new Exception("ERROR: this command is specified for Admin!");
        else {
            systemUsers.add(new User(userName, password));
        }

    }

    public void loginUser(String userName, String password) throws Exception {
        if (getUser(userName) == null || !getUser(userName).getPassword().equals(password))
            throw new Exception("ERROR: incorrect Username or password!");
        else {
            currentUser = getUser(userName);
        }
    }

    public void grantAccess(String userName, String directoryPath, String access) throws Exception {
        if (currentUser != admin)
            throw new Exception("ERROR: this command is specified for Admin!");
        else {
            Directory directory = getDirectory(directoryPath);
            User user = getUser(userName);
            if (access.equals("10")) {
                directory.addToUsersCanCreate(user);
            } else if (access.equals("01")) {
                directory.addToUsersCanDelete(user);
            } else if (access.equals("11")) {
                directory.addToUsersCanCreate(user);
                directory.addToUsersCanDelete(user);
            } else if (access.equals("00")) {
            } else {
                throw new Exception("ERROR: Invalid Access respresentaion!");
            }
        }
    }

    public void displayDiskStructure() {
        root.printDirectoryStructure(0);
    }
}
