import org.opencv.core.Mat;

import java.util.Scanner;

import static org.opencv.core.CvType.CV_8UC1;

public class InplaceDitherProcessor extends DitheringProcessor {

    public InplaceDitherProcessor(Mat inputImage) {
        this.image = inputImage;
        alreadyProcessed = false;
    }

    @Override
    public void doProcess() {
        setDitherMatrix();
        ImageHolder tempImage = new ImageHolder(image.rows(), image.cols(), CV_8UC1);

        for (int i = 0; i < image.rows(); ++i) {
            for (int j = 0; j < image.cols(); ++j) {
                if ((getPixelGray(i, j) / ditheringMatrixSize) > ditheringMatrix[i % ditheringMatrixSqrt][j % ditheringMatrixSqrt]) {
                    tempImage.setPixelGray(i, j, 255);
                } else {
                    tempImage.setPixelGray(i, j, 0);
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

        InplaceDitherProcessor inplaceDitherProcessor = new InplaceDitherProcessor(grayLevelProcessor.getMat());
        inplaceDitherProcessor.setDitheringMatrixSize(16);
        inplaceDitherProcessor.doProcess();
        inplaceDitherProcessor.saveImage(inputPath + "_InplaceDither.jpg");
    }
}
