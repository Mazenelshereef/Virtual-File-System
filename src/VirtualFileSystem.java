import java.util.ArrayList;

public class VirtualFileSystem {
    int systemSizeInKB;
    Allocation allocation;
    public ArrayList<Integer> Storage;
    public ArrayList<DirectoryFileStructures.Directory> systemDirectories;    //directories of the system
    public ArrayList<DirectoryFileStructures.File> systemFiles;              //files of the system


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
        Integer index = null;
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
        Integer index=null;
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

    DirectoryFileStructures.File returnDesiredFile(String Path)
    {
        DirectoryFileStructures.File file = null;
        for (int i=0; i<systemFiles.size();i++)
        {
            if(systemFiles.get(i).getFilePath()==Path)
            {
                file=systemFiles.get(i);
            }
        }

        return file;
    }

    Boolean createFile(DirectoryFileStructures.File file)
    {
        boolean flag = false;
        String filepath = file.getFilePath();
        int lastSlash = filepath.lastIndexOf("/");
        String result = filepath.substring(0, lastSlash);
        for (DirectoryFileStructures.Directory dir : systemDirectories)
        {
            if (dir.getDirectoryPath() == result)
            {
                dir.addFile(file);
                flag = true;
                break;
            }
        }
        return flag;
    // takes name of directory from filepath and check if it exists or not
    // if it exists it will add file to its directory (in the arraylist of files of its directory) and in system arraylist of files
    // and if not it will return false
    }
    boolean deleteFile(DirectoryFileStructures.File file)
    {
        boolean flag = false;
        String filepath = file.getFilePath();
        int lastSlash = filepath.lastIndexOf("/");
        String result = filepath.substring(0, lastSlash);
        for (DirectoryFileStructures.Directory dir : systemDirectories)
        {
            if (dir.getDirectoryPath() == result) //if file parent exists
            {
                for(int i=0;i< dir.getFiles().size();i++){ //iterate in directory that contains the file
                    if (dir.getFiles().get(i).getFilePath()==file.getFilePath()){
                        dir.getFiles().remove(i);
                        flag=true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    boolean createFolder(DirectoryFileStructures.Directory directory)
    {
        boolean  flag= true;
        String path= directory.getDirectoryPath();
        for (DirectoryFileStructures.Directory dir : systemDirectories){
            if (dir.getDirectoryPath() == path){
                //dir found
                flag=false;
                break;
            }
        }

        if (flag==true){ //dir not found
            flag=false;
            int lastSlash = path.lastIndexOf("/");
            String parent = path.substring(0, lastSlash);
            for (DirectoryFileStructures.Directory dir : systemDirectories)//search for its parent
            {
                if (dir.getDirectoryPath() == parent)
                {
                    systemDirectories.add(directory);
                    dir.AddDirectorytoSubDirectoryList(directory);
                    flag = true;
                    break;
                }
            }
        }
        return flag;
        //false return if directory path found or directory parent not found



        //(if exist print file already exist) if the path of it not exists check if parent exist in array    ma/ahmed/nour
        //if parent not exists return false path.
        // if its parent exist add it to the system directories list and to subdirectories list of its parent directory(path)
    }
    boolean deleteFolder(DirectoryFileStructures.Directory directory)
    {
        boolean  flag= false;
        String path= directory.getDirectoryPath();
        for (int i=0;i>systemDirectories.size();i++){
            if (systemDirectories.get(i).getDirectoryPath()== path){//dir found

                systemDirectories.remove(i);
                flag=true;
                break;
            }
        }

        if (flag==true){//dir found

            int lastSlash = path.lastIndexOf("/");
            String parent = path.substring(0, lastSlash);
            for (int i=0;i>systemDirectories.size();i++)
            {
                if (systemDirectories.get(i).getDirectoryPath() == parent)//get it from its parent subdirectories
                {
                    for(int j=0;j>systemDirectories.get(i).getSubDirectories().size();i++){
                        if(systemDirectories.get(i).getSubDirectories().get(j).getDirectoryPath()==directory.getDirectoryPath()){

                            systemDirectories.get(i).getSubDirectories().remove(j);
                        }
                    }
                }
            }
        }


        //remove it from list of directories and remove it from its parent subdirectory
        return flag;
    }


}
