import java.util.ArrayList;

public interface Allocation {

    public boolean createFile(DirectoryFileStructures.Directory directory, String filePath, int sizeInKB, ArrayList<DirectoryFileStructures.groupOfBlocks>emptyblocks);
    public int deleteFile(DirectoryFileStructures.Directory directory, String filePath, ArrayList<DirectoryFileStructures.groupOfBlocks>emptyblocks);
    public boolean createDirectory(DirectoryFileStructures.Directory directory, String directoryPath);
    public int deleteDirectory(DirectoryFileStructures.Directory directory, String directoryPath,ArrayList<DirectoryFileStructures.groupOfBlocks>emptyblocks, ArrayList<Boolean> state);
   /* public void write(system sys,String filePath);
    public void readTree(system sys, ObjectInputStream os, int currentSize, int sizeKB) throws ClassNotFoundException, IOException;*/
}
