#ifndef ASSIGNMENT3_MOTIONSEARCHTOOLS_H
#define ASSIGNMENT3_MOTIONSEARCHTOOLS_H


#include "PGMImage.h"

class MotionSearchTools {

    static double getMeanAbsoluteDifference
            (PGMImage &referenceFrame, PGMImage &targetFrame, unsigned int sizeOfBlock,
             unsigned int x, unsigned int y,
             unsigned int iHorizontalDisplacement, unsigned int jVerticalDisplacement);
    static double getSignalNoiseRatio(PGMImage &targetFrame, PGMImage &predictedFrame);

};


#endif //ASSIGNMENT3_MOTIONSEARCHTOOLS_H
