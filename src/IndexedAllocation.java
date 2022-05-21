import java.sql.Struct;
import java.util.ArrayList;

public class IndexedAllocation implements Allocation {
    VirtualFileSystem System;
    DirectoryFileStructures.Directory rootDirectory;
     class IndexBlock
    {
        int value;
        ArrayList<Integer> contentsOfBlock;
    }

    public IndexedAllocation() {
        ArrayList<Integer> arrOfIndexedBlocks = new ArrayList<Integer>();
        ArrayList<Integer> contentsOfBlock= new ArrayList<Integer>();
    }


    @Override
    public ArrayList<Integer> Allocate(int fileSize) {
        return null;
    }

    public IndexBlock AllocateIndexed(int fileSize) {
        int index = System.findFirstEmpty();
        IndexBlock indexblock = new IndexBlock();
        indexblock.value =index;
        for(int i=0; i<fileSize; i++)
        {
            int index2 = System.findFirstEmpty();
            indexblock.contentsOfBlock.add(index2);
        }
        return indexblock;//return  index block that contains array of the actual blocks to be allocated.
        // and the index of it in the storage
    }
}
