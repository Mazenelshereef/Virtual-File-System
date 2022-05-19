import java.util.ArrayList;

public class VirtualFileSystem {
    DirectoryFileStructures.Directory systemRoot;
    int systemSizeInKB;
    Allocation allocation;
    ArrayList<Boolean> state;
    ArrayList<DirectoryFileStructures.EmptyBlocks> blocks ;

    public VirtualFileSystem(Allocation _allocation, int _systemSizeInKB) {
       this.allocation=_allocation;
       this.systemSizeInKB=_systemSizeInKB;

        state = new ArrayList<Boolean>(_systemSizeInKB);
        for (int i = 0; i <_systemSizeInKB; i++)
        {
            state.add(false);
        }
        systemRoot = new DirectoryFileStructures.Directory("root");
        blocks = new ArrayList<>();
        blocks.add(new DirectoryFileStructures.EmptyBlocks(0, systemSizeInKB - 1));
    }
}
