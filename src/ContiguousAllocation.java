import java.util.ArrayList;

public class ContiguousAllocation implements Allocation{
/*

    @Override
    public boolean createFile(DirectoryFileStructures.Directory directory, String filePath, int sizeInKB, ArrayList<DirectoryFileStructures.groupOfBlocks> groupsOfBlocks) {
            for (DirectoryFileStructures.groupOfBlocks groupOfBlocks : groupsOfBlocks)
            {
                if (sizeInKB <= groupOfBlocks.noOfBlocks && groupOfBlocks.free) {
                    ArrayList<Integer> allocatedBlocks = new ArrayList<>();
                    allocatedBlocks.add(groupOfBlocks.startBlock);
                    allocatedBlocks.add(groupOfBlocks.startBlock + sizeInKB - 1);
                    DirectoryFileStructures.File file = new DirectoryFileStructures.File(filePath, allocatedBlocks);
                    dir.files.add(file);
                    for (int i = groupOfBlocks.startBlock; i < groupOfBlocks.startBlock + sizeInKB; i++) {
                        free.set(i, true);
                    }
                    if (sizeInKB < groupOfBlocks.noOfBlocks)
                    {
                        DirectoryFileStructures.groupOfBlocks newS = new DirectoryFileStructures.groupOfBlocks(groupOfBlocks.startBlock + sizeInKB, groupOfBlocks.endBlock, false);
                        groupsOfBlocks.add(newS);
                        groupOfBlocks.endBlock = groupOfBlocks.startBlock + sizeInKB - 1;
                    }
                    groupOfBlocks.free = true;
                   // Collections.sort(groupOfBlocks, new Compare());
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public int deleteFile(DirectoryFileStructures.Directory directory, String filePath, ArrayList<DirectoryFileStructures.groupOfBlocks> emptyblocks) {
        return 0;
    }

    @Override
    public boolean createDirectory(DirectoryFileStructures.Directory directory, String directoryPath) {
        return false;
    }

    @Override
    public int deleteDirectory(DirectoryFileStructures.Directory directory, String directoryPath, ArrayList<DirectoryFileStructures.groupOfBlocks> emptyblocks, ArrayList<Boolean> state) {
        return 0;
    }
    */

}
