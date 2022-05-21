import java.util.ArrayList;
import java.util.Scanner;

public class DirectoryFileStructures{
    public static class File {
        private String filePath;
        private String filename;
        private ArrayList<Integer> allocatedBlocks;
        private boolean isDeleted;
        public File(String filePath, ArrayList<Integer> allocatedBlocks) {
            this.filePath = filePath;
            this.filename = filePath.substring(filePath.lastIndexOf("/")+1);
            this.allocatedBlocks = allocatedBlocks;
            this.isDeleted=false;
        }

        public ArrayList<Integer> getAllocatedBlocks() {
            return allocatedBlocks;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setAllocatedBlocks(ArrayList<Integer> allocatedBlocks) {
            this.allocatedBlocks = allocatedBlocks;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public void setDeleted(boolean deleted) {
            this.isDeleted = deleted;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Directory {
        private String directoryPath;
        private String directoryName;
        public ArrayList<File> files;
        private ArrayList<Directory> subDirectories;
        private boolean isDeleted = false;
        public Directory(String _directoryPath) {
            this.directoryPath = _directoryPath;
            this.directoryName = _directoryPath.substring(_directoryPath.lastIndexOf("/")+1);
            ArrayList<File> files= new ArrayList<File> ();
            this.isDeleted=false;
        }

        public void setDirectoryName(String directoryName) {
            this.directoryName = directoryName;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public void setDirectoryPath(String directoryPath) {
            this.directoryPath = directoryPath;
        }

        public void addFile(File file) {
            this.files.add(file);
        }

        public void setSubDirectories(ArrayList<Directory> subDirectories) {
            this.subDirectories = subDirectories;
        }

        public String getDirectoryName() {
            return directoryName;
        }

        public ArrayList<File> getFiles() {
            return files;
        }

        public ArrayList<Directory> getSubDirectories() {
            return subDirectories;
        }

        public String getDirectoryPath() {
            return directoryPath;
        }

        public void printDirectoryStructure(int level) {
            if (!this.isDeleted)
            {
                System.out.print("<"+ directoryName +">");
                System.out.println("  ");
                for (File file : files)
                {
                    for (int i= 0; i< level + 4; i++)
                    {
                        System.out.print(" ");
                    }
                    if(file.isDeleted) {System.out.println(file.filename + " is deleted!");}
                    else {System.out.println(file.filename);}
                }

                for (Directory subDirectory : subDirectories)
                {
                    subDirectory.printDirectoryStructure(level + 3);
                }
            }
            else {System.out.print("<" + directoryName + ">" + " is deleted!");}
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

}
