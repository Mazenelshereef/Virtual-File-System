import java.util.ArrayList;

public class VirtualFileSystem {
    int systemSizeInKB;
    Allocation allocation;
    public ArrayList<Integer> Storage;


    public VirtualFileSystem(Allocation _allocation, int _systemSizeInKB) {
       this.allocation=_allocation;
       this.systemSizeInKB=_systemSizeInKB;
        ArrayList<Integer> Storage = new ArrayList<Integer>(_systemSizeInKB);
        for(int i=0; i<_systemSizeInKB; i++)
        {
            Storage.set(i,0);
        }
    }

    public void setStorage(ArrayList<Integer> storage) {
        Storage = storage;
    }

    public ArrayList<Integer> getStorage() {
        return Storage;
    }

    int findFirstEmpty()
    {
        int index = 0;
        for(int i=0; i<Storage.size(); i++)
        {
           if(Storage.get(i)==0)
           {
               index= i;
               break;
           }
        }
        return index;
    }

    int findContiguousBlocks(int numOfBlocks)
    {
        int index=0;
        int counter=1;
        for(int i=1; i<Storage.size(); i++)
        {
            if(counter==numOfBlocks)
            {
                index=i-numOfBlocks;
                break;
            }
            if(Storage.get(i)==0 && Storage.get(i)==Storage.get(i-1))
            {
                counter++;
            }
        }
        return index;

    }


}
