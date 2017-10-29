import org.opencv.core.Mat;

import java.util.Scanner;

import static org.opencv.core.CvType.CV_8UC1;

public class SimpleDitherProcessor extends DitheringProcessor {

    public SimpleDitherProcessor(Mat inputImage) {
        this.image = inputImage;
        alreadyProcessed = false;
    }

    @Override
    public void doProcess() {
        setDitherMatrix();

        ImageHolder tempImage = new ImageHolder(image.rows() * ditheringMatrixSqrt,
                image.cols() * ditheringMatrixSqrt, CV_8UC1);

        for (int i = 0; i < image.rows(); ++i) {
            for (int j = 0; j < image.cols(); ++j) {
                double grayscale = this.getPixelGray(i, j);

                //per (original) pixel process
                for (int k = 0; k < ditheringMatrixSqrt; ++k) {
                    for (int m = 0; m < ditheringMatrixSqrt; ++m) {
                        tempImage.setPixelGray(ditheringMatrixSqrt * i + k, ditheringMatrixSqrt * j + m,
                                (grayscale >= 255 / (ditheringMatrixSize + 1) * ditheringMatrix[k][m]) ? 255 : 0);
                    }
                }
            }
        }

        image = tempImage.getMat();
        alreadyProcessed = true;
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
        simpleDitherProcessor.setDitheringMatrixSize(16);
        simpleDitherProcessor.doProcess();
        simpleDitherProcessor.saveImage(inputPath + "_simpleDither.jpg");
    }
}
