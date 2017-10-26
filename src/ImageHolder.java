import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;


public class ImageHolder {
    protected Mat image;

    public ImageHolder() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public ImageHolder(int row, int col, int type) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        image = new Mat(row, col, type);
    }

    public ImageHolder(Mat inputImage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.image = inputImage.clone();
    }

    public ColorPixelDTO getPixelRGB(int row, int col) {
        double[] pixelBGR = image.get(row, col);
        ColorPixelDTO colorPixelDTO = new ColorPixelDTO();
        colorPixelDTO.Blue = pixelBGR[0];
        colorPixelDTO.Green = pixelBGR[1];
        colorPixelDTO.Red = pixelBGR[2];
        return colorPixelDTO;
    }

    public Mat getMat() {
        return image.clone();
    }

    public void setPixelRGB(int row, int col, ColorPixelDTO colorPixelDTO) {
        image.put(row, col, colorPixelDTO.Blue, colorPixelDTO.Green, colorPixelDTO.Red);
    }

    public void setPixelGray(int row, int col, double grayscale) {
        image.put(row, col, grayscale);
    }

    public Image getJavafxImage() {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".bmp", image, matOfByte);
        return new Image(new ByteArrayInputStream(matOfByte.toArray()));
    }

    public void saveImage(String path) {
        Imgcodecs.imwrite(path, image);
    }
}
