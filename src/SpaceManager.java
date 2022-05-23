import java.util.ArrayList;

public class SpaceManager {

    public static class ContiguousFreeBlocks {
        int start;
        int length;

        public ContiguousFreeBlocks(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    private static SpaceManager instance;

    private SpaceManager() {
    }

    public static SpaceManager getInstance() {
        if (instance == null) {
            instance = new SpaceManager();
        }
        return instance;
    }

    public ArrayList<ContiguousFreeBlocks> getContiguousFreeBlocks() {
        ArrayList<ContiguousFreeBlocks> contiguousFreeBlocks = new ArrayList<ContiguousFreeBlocks>();
        int start = 0;
        int length = 0;
        for (int i = 0; i < VirtualFileSystem.getInstance().getMemory().size(); i++) {
            if (VirtualFileSystem.getInstance().getMemory().get(i) == 0) {
                length++;
            } else {
                if (length != 0) {
                    contiguousFreeBlocks.add(new ContiguousFreeBlocks(start, length));
                }
                start = i + 1;
                length = 0;
            }
        }
        if (length != 0) {
            contiguousFreeBlocks.add(new ContiguousFreeBlocks(start, length));
        }
        return contiguousFreeBlocks;
    }

    public ArrayList<Integer> getFreeBlocks() {
        ArrayList<Integer> freeBlocks = new ArrayList<Integer>();
        for (int i = 0; i < VirtualFileSystem.getInstance().getMemory().size(); i++) {
            if (VirtualFileSystem.getInstance().getMemory().get(i) == 0) {
                freeBlocks.add(i);
            }
        }
        return freeBlocks;
    }

    public void allocateMemory(ArrayList<Integer> blocks) throws Exception {
        for (int i = 0; i < blocks.size(); i++) {
            if (VirtualFileSystem.getInstance().getMemory().get(blocks.get(i)) != 0) {
                throw new Exception("ERROR: Block " + blocks.get(i) + " is already allocated!");
            }
            VirtualFileSystem.getInstance().getMemory().set(blocks.get(i), 1);
        }
    }
}
