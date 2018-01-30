#ifndef ASSIGNMENT3_TWODIMENSIONALLOGARITHMMOTIONSEARCH_H
#define ASSIGNMENT3_TWODIMENSIONALLOGARITHMMOTIONSEARCH_H

#include "MotionSearch.h"

class TwoDimensionalLogarithmMotionSearch : protected MotionSearch {
public:
    TwoDimensionalLogarithmMotionSearch(PGMImage *referenceFrame, PGMImage *targetFrame, unsigned int sizeOfMacroblock,
                                        unsigned int searchWindowRadius) :
            MotionSearch(referenceFrame, targetFrame, sizeOfMacroblock, searchWindowRadius) {}

protected:
    MotionVectorDTO searchMotionVector(unsigned int x, unsigned int y) override;

private:
    void initializeVectorsWithOffsets(MotionVectorDTO *vectors);
};


#endif //ASSIGNMENT3_TWODIMENSIONALLOGARITHMMOTIONSEARCH_H
