import org.opencv.core.Mat;

import java.util.Scanner;

import static org.opencv.core.CvType.CV_8UC1;

public class SimpleDitherProcessor extends ImageHolder implements ImageProcessor {
    boolean alreadyProcessed;

    public SimpleDitherProcessor(Mat inputImage) {
        this.image = inputImage;
        alreadyProcessed = false;
    }

    @Override
    public void doProcess() {
        ImageHolder tempImage = new ImageHolder(image.rows() * 2, image.cols() * 2, CV_8UC1);

        for (int i = 0; i < image.rows(); ++i) {
            for (int j = 0; j < image.cols(); ++j) {
                double grayscale = this.getPixelGray(i, j);
                if (grayscale >= 201) {
                    tempImage.setPixelGray(2 * i, 2 * j, 255);
                    tempImage.setPixelGray((2 * i) + 1, (2 * j) + 1, 255);
                    tempImage.setPixelGray(2 * i, (2 * j) + 1, 255);
                    tempImage.setPixelGray((2 * i) + 1, 2 * j, 255);
                } else if (grayscale >= 151) {
                    tempImage.setPixelGray(2 * i, 2 * j, 255);
                    tempImage.setPixelGray((2 * i) + 1, (2 * j) + 1, 255);
                    tempImage.setPixelGray(2 * i, (2 * j) + 1, 255);
                    tempImage.setPixelGray((2 * i) + 1, 2 * j, 0);
                } else if (grayscale >= 101) {
                    tempImage.setPixelGray(2 * i, 2 * j, 255);
                    tempImage.setPixelGray((2 * i) + 1, (2 * j) + 1, 255);
                    tempImage.setPixelGray(2 * i, (2 * j) + 1, 0);
                    tempImage.setPixelGray((2 * i) + 1, 2 * j, 0);
                } else if (grayscale >= 511) {
                    tempImage.setPixelGray(2 * i, 2 * j, 255);
                    tempImage.setPixelGray((2 * i) + 1, (2 * j) + 1, 0);
                    tempImage.setPixelGray(2 * i, (2 * j) + 1, 0);
                    tempImage.setPixelGray((2 * i) + 1, 2 * j, 0);
                } else {
                    tempImage.setPixelGray(2 * i, 2 * j, 0);
                    tempImage.setPixelGray((2 * i) + 1, (2 * j) + 1, 0);
                    tempImage.setPixelGray(2 * i, (2 * j) + 1, 0);
                    tempImage.setPixelGray((2 * i) + 1, 2 * j, 0);
                }
            }
        }

        image = tempImage.getMat();
        alreadyProcessed = true;
    }

    @Override
    public boolean processed() {
        return alreadyProcessed;
    }

    public static void main(String args[]) {
        functionTest();
    }

    public static void functionTest() {
        Scanner input = new Scanner(System.in);
        String inputPath = input.nextLine();
        InputImageLoader inputImageLoader = new InputImageLoader(inputPath);

        GrayLevelProcessor grayLevelProcessor = new GrayLevelProcessor(inputImageLoader.getMat());
        grayLevelProcessor.doProcess();

        SimpleDitherProcessor simpleDitherProcessor = new SimpleDitherProcessor(grayLevelProcessor.getMat());
        simpleDitherProcessor.doProcess();
        simpleDitherProcessor.saveImage(inputPath + "_simpleDither.jpg");
    }

}
