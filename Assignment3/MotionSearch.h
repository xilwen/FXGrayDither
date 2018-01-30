#ifndef ASSIGNMENT3_MOTIONSEARCH_H
#define ASSIGNMENT3_MOTIONSEARCH_H

#include "PGMImage.h"
#include "MotionVectorDTO.h"

class MotionSearch {
public:
    MotionSearch() = delete;

    MotionSearch(PGMImage *referenceFrame, PGMImage *targetFrame, unsigned int sizeOfMacroblock,
                 unsigned int searchWindowRadius);

    void FindMotionVectors();

    PGMImage getResultImage();

protected:
    PGMImage *referenceFrame = nullptr;
    PGMImage *targetFrame = nullptr;
    PGMImage *predictionFrame = nullptr;

    virtual MotionVectorDTO searchMotionVector(unsigned int x, unsigned int y);

    bool checkFramePositionRange(unsigned int x, unsigned int y);

    unsigned int getSearchWindowRadius() { return searchWindowRadius; }

    unsigned int getsizeOfMacroBlock() { return sizeOfMacroblock; }

private:
    std::vector<MotionVectorDTO> motionVectors;
    unsigned int sizeOfMacroblock = 0;
    unsigned int searchWindowRadius = 0;
};


#endif //ASSIGNMENT3_MOTIONSEARCH_H
