import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Resizer  implements Runnable{

    private String dstFolder;
    private File[] files;
    private long start;
    private int newWidth;

    private static int count;

    public Resizer(String dstFolder, File[] files, long start, int newWidth) {
        this.dstFolder = dstFolder;
        this.files = files;
        this.start = start;
        this.newWidth = newWidth;
    }

    @Override
    public void run() {
        int newCount = ++count;
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = Scalr.resize(image, Scalr.Mode.AUTOMATIC, newWidth, newHeight);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Thread #" + newCount + " - "+ (System.currentTimeMillis() - start));
    }
}
