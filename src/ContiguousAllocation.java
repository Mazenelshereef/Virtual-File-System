import java.util.ArrayList;

public class ContiguousAllocation implements Allocation{
    VirtualFileSystem System;
    DirectoryFileStructures.Directory rootDirectory;
    ArrayList<Integer> arrOfContiguousBlocks;

    public ContiguousAllocation() {
        ArrayList<Integer> arrOfContiguousBlocks= new ArrayList<Integer>() ;
    }

    public ArrayList<Integer> Allocate(int fileSize)
    {
        for(int i=0; i<fileSize; i++)
        {
            Integer index = System.findContiguousBlocks(fileSize);
            if(index==null)//check if the is not a free contiguous space
            {
                return null;
            }
            arrOfContiguousBlocks.set(i,index+i);
        }
        return arrOfContiguousBlocks;//return array that contains the contiguous memory indexes to be allocated.
    }

    @Override
    public ArrayList<Integer> ToDeallocate(MyFile.File file) {
        return file.getAllocatedBlocks();
    }
}
