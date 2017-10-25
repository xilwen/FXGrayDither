import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;

public class InputImageLoader {
    private Mat imageFile;
    private String imagePath;

    public InputImageLoader(String imagePath) {
        this.imagePath = imagePath;
        imageFile = Imgcodecs.imread(imagePath);
    }

    public ColorPixelDTO getPixelRGB(int row, int col){
        double[] pixelBGR = imageFile.get(row,col);
        ColorPixelDTO colorPixelDTO = new ColorPixelDTO();
        colorPixelDTO.Blue = pixelBGR[0];
        colorPixelDTO.Green = pixelBGR[1];
        colorPixelDTO.Red = pixelBGR[2];
        return colorPixelDTO;
    }

    public static void main(String args[]){
        functionTest();
    }

    public static void functionTest(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Scanner input = new Scanner(System.in);
        String inputPath = input.nextLine();
        InputImageLoader inputImageLoader = new InputImageLoader(inputPath);
        ColorPixelDTO colorPixelDTO = inputImageLoader.getPixelRGB(0,0);
        System.out.println(colorPixelDTO.Red + " " + colorPixelDTO.Green + " " + colorPixelDTO.Blue);
    }
}
