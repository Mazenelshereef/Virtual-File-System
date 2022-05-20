import java.util.ArrayList;

public class LinkedAllocation implements Allocation{
    VirtualFileSystem System;
    ArrayList<Integer> arrOfLinkedBlocks;

    public LinkedAllocation() {
        ArrayList<Integer> arrOfLinkedBlocks= new ArrayList<Integer>() ;
    }

    public ArrayList<Integer> Allocate(int fileSize)
  {
      for(int i=0; i<fileSize; i++)
      {
         int index = System.findFirstEmpty();
          arrOfLinkedBlocks.add(index);
      }
      return arrOfLinkedBlocks;
  }
}
