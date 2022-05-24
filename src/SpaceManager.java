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

    public ArrayList<Integer> getUsedBlocks(){
        ArrayList<Integer> usedBlocks = new ArrayList<Integer>();
        for (int i = 0; i < VirtualFileSystem.getInstance().getMemory().size(); i++) {
            if (VirtualFileSystem.getInstance().getMemory().get(i) != 0) {
                usedBlocks.add(i);
            }
        }
        return usedBlocks;
    }

    public void allocateMemory(ArrayList<Integer> blocks, AllocationType allocationType) throws Exception {
        //get the actual blocks to allocate
        ArrayList<Integer> blocksToAllocate = new ArrayList<Integer>();
        if (allocationType == AllocationType.CONTIGUOUS) {
            //blocks.get(0) is the start of the contiguous block
            //blocks.get(1) is the length of the contiguous block
            for (int i = blocks.get(0); i < blocks.get(1) + blocks.get(0); i++) {
                blocksToAllocate.add(i);
            }
        } else if (allocationType == AllocationType.LINKED) {
            //blocks.get(0) is the start block
            //blocks.get(1) is the end block
            //remaing blocks are the links
            for (int i = 2; i < blocks.size(); i+=2) {
                blocksToAllocate.add(blocks.get(i));
            }
        } else { //indexed allocation
            //blocks.get(0) is the index block
            //remaing blocks are the allocated blocks
            for (int i = 0; i < blocks.size(); i++) {
                blocksToAllocate.add(blocks.get(i));
            }
        }
        //allocate the blocks
        for (int i = 0; i < blocksToAllocate.size(); i++) {
            if (VirtualFileSystem.getInstance().getMemory().get(blocksToAllocate.get(i)) != 0) {
                throw new Exception("ERROR: Block " + blocksToAllocate.get(i) + " is already allocated!");
            }
            VirtualFileSystem.getInstance().getMemory().set(blocksToAllocate.get(i), 1);
        }
    }

    public void deallocateMemory(ArrayList<Integer> blocks, AllocationType allocationType) throws Exception {
        //get the actual blocks to deallocate
        ArrayList<Integer> blocksToDeallocate = new ArrayList<Integer>();
        if (allocationType == AllocationType.CONTIGUOUS) {
            //blocks.get(0) is the start of the contiguous block
            //blocks.get(1) is the length of the contiguous block
            for (int i = blocks.get(0); i < blocks.get(1) + blocks.get(0); i++) {
                blocksToDeallocate.add(i);
            }
        } else if (allocationType == AllocationType.LINKED) {
            //blocks.get(0) is the start block
            //blocks.get(1) is the end block
            //remaing blocks are the links
            for (int i = 2; i < blocks.size(); i+=2) {
                blocksToDeallocate.add(blocks.get(i));
            }
        } else { //indexed allocation
            //blocks.get(0) is the index block
            //remaing blocks are the allocated blocks
            for (int i = 0; i < blocks.size(); i++) {
                blocksToDeallocate.add(blocks.get(i));
            }
        }
        //deallocate the blocks
        for (int i = 0; i < blocksToDeallocate.size(); i++) {
            if (VirtualFileSystem.getInstance().getMemory().get(blocksToDeallocate.get(i)) == 0) {
                throw new Exception("ERROR: Block " + blocksToDeallocate.get(i) + " is already deallocated!");
            }
            VirtualFileSystem.getInstance().getMemory().set(blocksToDeallocate.get(i), 0);
        }
    }
}
