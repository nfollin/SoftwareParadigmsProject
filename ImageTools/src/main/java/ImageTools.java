import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by NathanFollin on 6/29/14.
 */
public class ImageTools {

    public static byte[] imageToArray(String imageLocation) throws IOException{
        BufferedImage img = ImageIO.read(new File(imageLocation));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        baos.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }
    public static byte[] bufferedImageToByteArray(BufferedImage image) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }
    public static BufferedImage byteArrayToBufferedImage(byte [] byteArray) throws IOException{
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArray));
        return image;
    }
    public static File byteArrayToImage(byte[] byteArray,String outputLocation) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArray));
        File output = new File(outputLocation);
        ImageIO.write(image, "jpg", output);
        return output;
    }
}
