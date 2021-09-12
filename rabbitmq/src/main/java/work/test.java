package work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Description:
 * @Author : fys
 * @Date : 2020/7/13
 */
public class test {
    public static void main(String[] args) throws FileNotFoundException {
        File file=new File("");
        RandomAccessFile rAccessFile=new RandomAccessFile(file,"r");
        FileChannel channel = rAccessFile.getChannel();

    }
}
