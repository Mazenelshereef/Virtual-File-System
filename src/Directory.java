import java.util.ArrayList;

public class Directory {
    private String directoryName;
    private Directory parent;
    public ArrayList<MyFile> files;
    private ArrayList<Directory> subDirectories;
    private ArrayList<User> usersCanCreate;
    private ArrayList<User> usersCanDelete;

    public Directory(String directoryName) {
        this.directoryName = directoryName;
        this.parent = null;
        files = new ArrayList<MyFile>();
        subDirectories = new ArrayList<Directory>();
        usersCanCreate = new ArrayList<User>();
        usersCanDelete = new ArrayList<User>();
        
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public Directory getParent() {
        return parent;
    }

    public void addToUsersCanCreate(User user){
    usersCanCreate.add(user);
   
    }

    public void addToUsersCanDelete(User user){
    usersCanDelete.add(user);
   
    } 

    public ArrayList<User> getUsersCanCreate() {
        return usersCanCreate;
    }

    public ArrayList<User> getUsersCanDelete() {
        return usersCanDelete;
    }

    public boolean checkIfCanCreate(User user){
        for(int i=0; i<usersCanCreate.size(); i++){
            if(user==usersCanCreate.get(i)){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfCanDelete(User user){
        for(int i=0; i<usersCanDelete.size(); i++){
            if(user==usersCanDelete.get(i)){
                return true;
            }
        }
        return false;
    }

    public void addFile(MyFile file) {
        file.setParent(this);
        files.add(file);
    }

    public void addSubDirectory(Directory subdirectory) {
        subdirectory.setParent(this);
        this.subDirectories.add(subdirectory);
    }

    public void setSubDirectories(ArrayList<Directory> subDirectories) {
        this.subDirectories = subDirectories;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public ArrayList<MyFile> getFiles() {
        return files;
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public MyFile getFile(String fileName) {
        for (MyFile file : files) {
            if (file.getFileName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    public Directory getSubDirectory(String subdirectoryName) {
        for (Directory subdirectory : subDirectories) {
            if (subdirectory.getDirectoryName().equals(subdirectoryName)) {
                return subdirectory;
            }
        }
        return null;
    }

    public void deleteFile(MyFile fileToDelete) {
        for (MyFile file : files) {
            if (file == fileToDelete) {
                files.remove(file);
                break;
            }
        }
    }

    public void deleteSubDirectory(Directory subdirectoryToDelete) {
        for (Directory subdirectory : subDirectories) {
            if (subdirectory == subdirectoryToDelete) {
                subDirectories.remove(subdirectory);
                break;
            }
        }
    }

    public void printDirectoryStructure(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println("<" + directoryName + ">");
        for (MyFile file : files) {
            for (int i = 0; i < level + 1; i++) {
                System.out.print("\t");
            }
            System.out.println(file.getFileName());
        }
        for (Directory subDirectory : subDirectories) {
            subDirectory.printDirectoryStructure(level + 1);
        }
    }

    //used in the FileParser class
    public String getDirectoryStructure() {
        String directoryStructure = "";
        for (MyFile file : files) {
            directoryStructure += file.getFileName();
            if (files.get(files.size() - 1) != file || subDirectories.size() != 0) {
                directoryStructure += ",";
            }
        }
        for (Directory subDirectory : subDirectories) {
            directoryStructure += subDirectory.getDirectoryName() + "(";
            directoryStructure += subDirectory.getDirectoryStructure() + ")";
            if (subDirectories.get(subDirectories.size() - 1) != subDirectory) {
                directoryStructure += ",";
            }
        }
        return directoryStructure;
    }

}
