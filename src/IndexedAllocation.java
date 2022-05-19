import java.util.ArrayList;

public class IndexedAllocation implements Allocation {


    @Override
    public boolean createFile(DirectoryFileStructures.Directory directory, String filePath, int sizeInKB, ArrayList<DirectoryFileStructures.EmptyBlocks> emptyblocks) {
        return false;
    }

    @Override
    public int deleteFile(DirectoryFileStructures.Directory directory, String filePath, ArrayList<DirectoryFileStructures.EmptyBlocks> emptyblocks) {
        return 0;
    }

    @Override
    public boolean createDirectory(DirectoryFileStructures.Directory directory, String directoryPath) {
        return false;
    }

    @Override
    public int deleteDirectory(DirectoryFileStructures.Directory directory, String directoryPath, ArrayList<DirectoryFileStructures.EmptyBlocks> emptyblocks, ArrayList<Boolean> state) {
        return 0;
    }
}
