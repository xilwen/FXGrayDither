# Assignment 1
 
Execuatable JAR is downloadable at https://github.com/xilwen/FXGrayDither/releases  

## About the Program and Algorithm
  
  This program implements a gray level and two dithering algorithm, with four threshold maps option (2\*2, 3\*3, 4\*4 and 8\*8 matrices, using random values or values from https://en.wikipedia.org/wiki/Ordered_dithering) available.  
  A simple JavaFX GUI is implemented to make algorithm configuration,image import and export easier.

## Test Results and Discussions  
### Overview
  Input Image size are 1600\*900, but downscaled to 480\*270 in this preview.  
  Simple dithering with 8*8 threshold map produced best graylevel color feeling, but it costs 8 times of resolution to represent the image, with an unacceptable file size.  
  Inplace dithering with 4\*4 threshold map produced the most balanced output on both file size and image feeling.  

  ![alt tag](https://raw.githubusercontent.com/xilwen/FXGrayDither/master/Assignment1/images/Overview.png)  

### Image Detail 
  Simple dithering with 3\*3 threshold map make the most detailed image with great gray level feeling unexpectedly. 4*4 version of this algorithm produced far better gray level feeling, but failed keeping image details.  
  All dithering outputs failed to present texts in the runtime error window of input image, so it is not a good idea using dithering to process images that details are required.  

  ![alt tag](https://raw.githubusercontent.com/xilwen/FXGrayDither/master/Assignment1/images/Details.png)  


### Dither Matrix Arrangement Modifications
  Modifying arrangement of the values in the matrix did not affect the overall feeling of the output image.  
  The difference can be seen in the shadow (or where with deeper color); the arrangement of black pixels are obviously well-distributed in the standard Beyer/Index matrices than random generated matrices.  
  The full size image of outputs below can be found at https://github.com/xilwen/FXGrayDither/tree/master/Assignment1/images/MatrixArrangements/ .  

  ![alt tag](https://raw.githubusercontent.com/xilwen/FXGrayDither/master/Assignment1/images/ArrangementDifferences.png)


### Program Screenshot
![alt tag](https://raw.githubusercontent.com/xilwen/FXGrayDither/master/Screenshot.jpg)  

![alt tag](https://raw.githubusercontent.com/xilwen/FXGrayDither/master/Screenshot1.jpg)

## What I have learned in this assignment
* Using native libraries(OpenCV dll) in Java
* OpenCV Mat/Imgcodecs basics
* Dithering techniques
* "release" feature in GitHub
* Markdown All in One extension of Visual Studio Code for offline markdown preview
