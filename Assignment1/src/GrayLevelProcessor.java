import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;

import static org.opencv.core.CvType.CV_8UC1;

public class GrayLevelProcessor extends ImageHolder implements ImageProcessor{
    boolean alreadyProcessed;

    public GrayLevelProcessor(Mat mat) {
        this.alreadyProcessed = false;
        this.image = mat;
    }

    @Override
    public void doProcess() {
        ColorPixelDTO colorPixelDTO;
        ImageHolder tempImage = new ImageHolder(image.rows(), image.cols(), CV_8UC1);

        for(int i = 0; i < image.rows(); ++i){
            for(int j = 0; j < image.cols(); ++j){
                colorPixelDTO = this.getPixelRGB(i, j);
                tempImage.setPixelGray(i, j, (colorPixelDTO.Red + colorPixelDTO.Green + colorPixelDTO.Blue) / 3);
            }
        }

        image = tempImage.getMat();

        this.alreadyProcessed = true;
    }

    @Override
    public boolean processed() {
        return alreadyProcessed;
    }

    public static void main(String args[]){
        functionTest();
    }

    public static void functionTest(){
        Scanner input = new Scanner(System.in);
        String inputPath = input.nextLine();
        InputImageLoader inputImageLoader = new InputImageLoader(inputPath);

        GrayLevelProcessor grayLevelProcessor = new GrayLevelProcessor(inputImageLoader.getMat());
        grayLevelProcessor.doProcess();
        grayLevelProcessor.saveImage(inputPath + "_grayscale.jpg");
    }

}
