import java.util.ArrayList;

public class Directory {
    private String directoryName;
    private Directory parent;
    public ArrayList<MyFile> files;
    private ArrayList<Directory> subDirectories;
    private boolean isDeleted = false;

    public Directory(String directoryName) {
        this.directoryName = directoryName;
        this.parent = null;
        files = new ArrayList<MyFile>();
        subDirectories = new ArrayList<Directory>();
        this.isDeleted = false;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    // get parent directory
    public Directory getParent() {
        return parent;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public MyFile getFile(String fileName){
        for(MyFile file : files){
            if(file.getFileName().equals(fileName)){
                return file;
            }
        }
        return null;
    }

    public Directory getSubDirectory(String subdirectoryName){
        for(Directory subdirectory : subDirectories){
            if(subdirectory.getDirectoryName().equals(subdirectoryName)){
                return subdirectory;
            }
        }
        return null;
    }

    public void deleteFile(MyFile fileToDelete){
        for(MyFile file : files){
            if(file == fileToDelete){
                files.remove(file);
                break;
            }
        }
    }

    public void deleteSubDirectory(Directory subdirectoryToDelete){
        for(Directory subdirectory : subDirectories){
            if(subdirectory == subdirectoryToDelete){
                subDirectories.remove(subdirectory);
                break;
            }
        }
    }

    public void printDirectoryStructure(int level) {
        if (!this.isDeleted) {
            System.out.print("<" + directoryName + ">");
            System.out.println("  ");
            for (MyFile file : files) {
                for (int i = 0; i < level + 4; i++) {
                    System.out.print(" ");
                }
                if (file.isDeleted()) {
                    System.out.println(file.getFileName() + " is deleted!");
                } else {
                    System.out.println(file.getFileName());
                }
            }

            for (Directory subDirectory : subDirectories) {
                subDirectory.printDirectoryStructure(level + 3);
            }
        } else {
            System.out.print("<" + directoryName + ">" + " is deleted!");
        }
    }

    public String getDirectoryStructure() {
        String directoryStructure = "";
        for (MyFile file : files) {
            directoryStructure += file.getFileName();
            if (files.get(files.size()-1) != file || subDirectories.size() != 0) {
                directoryStructure += ",";
            }
        }
        for (Directory subDirectory : subDirectories) {
            directoryStructure += subDirectory.getDirectoryName() + "(";
            directoryStructure += subDirectory.getDirectoryStructure() + ")";
            if (subDirectories.get(subDirectories.size()-1) != subDirectory) {
                directoryStructure += ",";
            }
        }
        return directoryStructure;
    }

}
