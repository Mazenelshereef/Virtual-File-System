import java.sql.Struct;
import java.util.ArrayList;

public class IndexedAllocation implements Allocation {
    VirtualFileSystem System;
    DirectoryFileStructures.Directory rootDirectory;
    ArrayList<Integer> arrOfIndexedBlocks;

    public IndexedAllocation() {
        ArrayList<Integer> arrOfIndexedBlocks = new ArrayList<Integer>();
        ArrayList<Integer> contentsOfBlock= new ArrayList<Integer>();
    }


    @Override
    public ArrayList<Integer> Allocate(int fileSize) {
        Integer index = System.findFirstEmpty();
        if(index==null)//there is no empty block
        {
            return null;
        }
        arrOfIndexedBlocks.set(0,index);
        for(int i=1; i<fileSize; i++)
        {
            Integer index2 = System.findFirstEmpty();
            if(index2==null)//there is no empty block
            {
                return null;
            }
            //handle if there is no remaining block
            arrOfIndexedBlocks.set(0,index2);
        }
        return arrOfIndexedBlocks;//return arraylist which its first element is considered (indexblock)
        // and the rest elements of the array contains the actual blocks that (indexblock) points to it.
    }

    @Override
    public ArrayList<Integer> ToDeallocate(DirectoryFileStructures.File file) {
        return file.getAllocatedBlocks();
    }


}
