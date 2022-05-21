import java.util.ArrayList;

public class VirtualFileSystem {
    int systemSizeInKB;
    Allocation allocation;
    public ArrayList<Integer> Storage;
    public ArrayList<DirectoryFileStructures.Directory> systemDirectories;    //directories of the system


    public VirtualFileSystem(Allocation _allocation, int _systemSizeInKB) {
       this.allocation=_allocation;
       this.systemSizeInKB=_systemSizeInKB;
        ArrayList<Integer> Storage = new ArrayList<Integer>(_systemSizeInKB);
        ArrayList<DirectoryFileStructures.Directory> systemDirectories= new ArrayList<DirectoryFileStructures.Directory>();
        for(int i=0; i<_systemSizeInKB; i++)
        {
            Storage.set(i,0);
        }
        systemDirectories.add(new DirectoryFileStructures.Directory("root"));
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

    Boolean createFile(DirectoryFileStructures.File file)
    {
        //takes name of directory from filepath and check if it exists or not
        // if it exists it will add file to its directory (in the arraylist of files)
        // and if not it will return false
        String path= file.getFilePath();

      return true;
    }
    void deleteFile(DirectoryFileStructures.File file)
    {
        //check if file exist then remove it from list of files in its directory
        String path= file.getFilePath();
    }
    void createFolder(DirectoryFileStructures.Directory directory)
    {
        //(if exist print file already exist) if the path of it not exists check if parent exist in array    ma/ahmed/nour
        //if parent not exists return false path.
        // if its parent exist add it to the system directories list and to subdirectories list of its parent directory(path)
        String path= directory.getDirectoryPath();
    }
    void deleteFolder(DirectoryFileStructures.Directory directory)
    {
        //remove it from list of directories and remove it from its parent subdirectory
        String path= directory.getDirectoryPath();
    }


}
