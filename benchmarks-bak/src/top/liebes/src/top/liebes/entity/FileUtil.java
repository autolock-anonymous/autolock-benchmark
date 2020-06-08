package top.liebes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileUtil {

    private Queue<String> fileList;

    private List<String> processedFileList;

    public FileUtil() {
        this.fileList = new LinkedBlockingQueue<>();
        this.processedFileList = new ArrayList<>();
    }

    /**
     * get file from url and store the file to local
     * file name will be stored into fileList
     * @param url absolute path of file eg. https://pic.com/pic.jpeg
     */
    public void getFile(String url){
        try{
            // simulate process of fetching files from website
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        fileList.add(url);
    }

    /**
     * processed first file obtained from function getFile()
     */
    public boolean handle(){
        if(fileList.size() > 0){
            String file = fileList.remove();
            file = file + " processed";
            processedFileList.add(file);
            return true;
        }
        return false;
    }

    public void showFiles(){
        for(int i = 0; i < processedFileList.size(); i ++){
            System.out.println(String.format("File %d: %s", i, processedFileList.get(i)));
        }
    }
}
