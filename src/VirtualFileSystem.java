import java.util.ArrayList;

public class VirtualFileSystem {
    private int systemSizeInKB;
    private Allocation allocation;
    private ArrayList<Integer> Memory;
    private Directory root;
    //public ArrayList<Directory> systemDirectories;    //directories of the system
    //public ArrayList<MyFile> systemFiles;              //files of the system

    public VirtualFileSystem() {
        root = new Directory("root");
    }

    public VirtualFileSystem(Allocation _allocation, int _systemSizeInKB) {
       this.allocation=_allocation;
       this.systemSizeInKB=_systemSizeInKB;
        ArrayList<Integer> Storage = new ArrayList<Integer>(_systemSizeInKB);
        ArrayList<Directory> systemDirectories= new ArrayList<Directory>();
        for(int i=0; i<_systemSizeInKB; i++)
        {
            Storage.set(i,0);
        }
        systemDirectories.add(new Directory("root"));
    }

    public void setMemory(ArrayList<Integer> storage) {
        Memory = storage;
    }

    public ArrayList<Integer> getMemory() {
        return Memory;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    public Directory getRoot() {
        return root;
    }

    int findFirstEmpty()
    {
        Integer index = null;
        for(int i=0; i<Memory.size(); i++)
        {
           if(Memory.get(i)==0)
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
        for(int i=1; i<Memory.size(); i++)
        {
            if(counter==numOfBlocks)
            {
                index=i-numOfBlocks;
                break;
            }
            if(Memory.get(i)==0 && Memory.get(i)==Memory.get(i-1))
            {
                counter++;
            }
        }
        return index;

    }

    MyFile returnDesiredFile(String Path)
    {
        MyFile file = null;
        for (int i=0; i<systemFiles.size();i++)
        {
            if(systemFiles.get(i).getFilePath()==Path)
            {
                file=systemFiles.get(i);
            }
        }

        return file;
    }

    Boolean createFile(MyFile file)
    {
        boolean flag = false;
        String filepath = file.getFilePath();
        int lastSlash = filepath.lastIndexOf("/");
        String result = filepath.substring(0, lastSlash);
        for (Directory dir : systemDirectories)
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
    boolean deleteFile(MyFile file)
    {
        boolean flag = false;
        String filepath = file.getFilePath();
        int lastSlash = filepath.lastIndexOf("/");
        String result = filepath.substring(0, lastSlash);
        for (Directory dir : systemDirectories)
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

    boolean createFolder(Directory directory)
    {
        boolean  flag= true;
        String path= directory.getDirectoryPath();
        for (Directory dir : systemDirectories){
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
            for (Directory dir : systemDirectories)//search for its parent
            {
                if (dir.getDirectoryPath() == parent)
                {
                    systemDirectories.add(directory);
                    dir.addDirectory(directory);
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
    boolean deleteFolder(Directory directory)
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
