import java.util.ArrayList;

public class Directory {
    private String directoryPath;
    private String directoryName;
    private Directory parent;
    public ArrayList<MyFile> files;
    private ArrayList<Directory> subDirectories;
    private boolean isDeleted = false;

    public Directory(String _directoryPath) {
        this.directoryPath = _directoryPath;
        this.directoryName = _directoryPath.substring(_directoryPath.lastIndexOf("/") + 1);
        this.parent = null;
        ArrayList<MyFile> files = new ArrayList<MyFile>();
        ArrayList<Directory> subDirectories = new ArrayList<Directory>();
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

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void addFile(String fileName) {
        MyFile file = new MyFile(fileName);
        file.setParent(this);
        files.add(file);
    }

    public void addDirectory(String subdirectoryName) {
        Directory subdirectory = new Directory(this.directoryPath + "/" + subdirectoryName);
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

    public String getDirectoryPath() {
        return directoryPath;
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
                    System.out.println(file.getFilename() + " is deleted!");
                } else {
                    System.out.println(file.getFilename());
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
            directoryStructure += file.getFilename() + ",";
        }
        for (Directory subDirectory : subDirectories) {
            directoryStructure += subDirectory.getDirectoryName() + "(";
            directoryStructure += subDirectory.getDirectoryStructure() + ")";
        }
        return directoryStructure;
    }

}
