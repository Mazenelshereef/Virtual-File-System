public class VirtualFileSystem {
    DirectoryFileStructures.Directory systemRoot;
    int systemSizeInKB;
    Allocation allocation;

    public VirtualFileSystem(Allocation _allocation, int _systemSizeInKB) {
       this.allocation=_allocation;
       this.systemSizeInKB=_systemSizeInKB;

        state = new ArrayList<>(sizeKB);
        for (int i = 0; i < sizeKB; i++) {
            state.add(false);
        }
        root = new DirectoryFileStructures.Directory("root");
        spaces = new ArrayList<>();
        spaces.add(new Space(0, sizeKB - 1, false));
    }
}
