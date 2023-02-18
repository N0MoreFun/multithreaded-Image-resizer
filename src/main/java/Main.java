import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.Arrays;
public class Main {

    public static void main(String[] args) {
        String srcFolder = "/users/�����/Desktop/src";
        String dstFolder = "/users/�����/Desktop/dst";
        int coreCount = getNumCores();
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        long start = System.currentTimeMillis();
        File[][] splitFiles = splitFiles(files,coreCount);
        for (File[] splitedFiles : splitFiles) {
            new Thread( new Resizer(dstFolder,splitedFiles,start,3000)).start();
        }



    }
    public static int getNumCores() {
        return Runtime.getRuntime().availableProcessors();
    }
    public static File[][] splitFiles(File[] files, int n) {
        int len = files.length;
        File[][] result = new File[n][];

        if (n > len) {
            throw new IllegalArgumentException("���������� ������ ������ ��� ������ �������");
        }

        int remainder = len % n;
        int size = len / n;

        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < n; i++) {
            endIndex = startIndex + size + (remainder-- > 0 ? 1 : 0);
            result[i] = Arrays.copyOfRange(files, startIndex, endIndex);
            startIndex = endIndex;
        }

        return result;
    }
}
