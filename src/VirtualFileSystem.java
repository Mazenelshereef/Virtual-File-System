import java.util.ArrayList;

public class VirtualFileSystem {
    DirectoryFileStructures.Directory systemRoot;
    int systemSizeInKB;
    Allocation allocation;
    ArrayList<Boolean> state;
    ArrayList<DirectoryFileStructures.groupOfBlocks> groupsOfBlocks ;

    public VirtualFileSystem(Allocation _allocation, int _systemSizeInKB) {
       this.allocation=_allocation;
       this.systemSizeInKB=_systemSizeInKB;
       state = new ArrayList<Boolean>(_systemSizeInKB);
       systemRoot = new DirectoryFileStructures.Directory("systemRoot");
       groupsOfBlocks = new ArrayList<>();
       DirectoryFileStructures.groupOfBlocks groupOfBlocks = new DirectoryFileStructures.groupOfBlocks(0, systemSizeInKB - 1, true);
       groupsOfBlocks.add(groupOfBlocks);
       for (int i= 0; i< _systemSizeInKB; i++)
        {
            state.add(false);
        }
    }
}
