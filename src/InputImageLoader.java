import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.Scanner;

public class InputImageLoader extends ImageHolder {

    private String imagePath;

    public InputImageLoader(String imagePath) {
        this.imagePath = imagePath;
        image = Imgcodecs.imread(imagePath);
    }

    public static void main(String args[]){
        functionTest();
    }

    public static void functionTest(){
        Scanner input = new Scanner(System.in);
        String inputPath = input.nextLine();
        InputImageLoader inputImageLoader = new InputImageLoader(inputPath);
        ColorPixelDTO colorPixelDTO = inputImageLoader.getPixelRGB(0,0);
        System.out.println(colorPixelDTO.Red + " " + colorPixelDTO.Green + " " + colorPixelDTO.Blue);
    }
}
