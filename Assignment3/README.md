# Assignment 3: Motion Compensation
## Program Usage
        Assignment3 -2D|-s MacroBlockSize SearchWindowRadius <ReferencePGM> <TargetPGM> <OutputPGM>  
For example: 

        Assignment3  -s 16 15 C:\Users\Wen\Desktop\i1.pgm C:\Users\Wen\Desktop\i2.pgm C:\Users\Wen\Desktop\i2p-seq.pgm  
will generate a prediction frame output save to i2p-seq.pgm from i1.pgm and i2.pgm, and show run time and SNR information to stdout.    

      Images in this folder/subfolder are only for education/in-class usage.
      Redistribution of these images are strictly FORBIDDEN, and please respect the right of protraits.

## Results
<table><tr><td></td><td>Sequential</td><td>2D Logarithm</td></tr>
<tr><td>Algorithm run time(milliseconds)</td><td>3277.78</td><td>129.584</td></tr>
<tr><td>SNR</td><td>151.525</td><td>35.5103</td></tr>
<tr><td>Predicted Frame</td><td><img src=https://raw.githubusercontent.com/xilwen/multimedia-systems-assignments/master/Assignment3/images/i2p-seq.jpg>
</td><td><img src=https://raw.githubusercontent.com/xilwen/multimedia-systems-assignments/master/Assignment3/images/i2p-2d.jpg></td></tr></table>  

### Input Images
<table><tr><td></td><td>Reference Frame</td><td>Target Frame</td></tr>
<tr><td>Image</td><td><img src=https://raw.githubusercontent.com/xilwen/multimedia-systems-assignments/master/Assignment3/images/i1.jpg></td><td><img src=https://raw.githubusercontent.com/xilwen/multimedia-systems-assignments/master/Assignment3/images/i2.jpg></td></tr></table>  
  

## Discussions
- 2D Logarithm are far faster than Sequential search, but also make more distortion on the image; although a higher SNR is present on 2D Logarithm algorithm, the output image still looks acceptable from human's respective.

## Experience 
* Usage of "unsigned int" for implying others some variables cannot be negative is very expensive on code readability.    
* Understanding an algorithm precisely before implementing it will reduce the cost of debugging and designing program.


 
 