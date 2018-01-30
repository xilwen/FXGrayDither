#ifndef ASSIGNMENT3_SEQUENTIALMOTIONSEARCH_H
#define ASSIGNMENT3_SEQUENTIALMOTIONSEARCH_H

#include "MotionSearch.h"

class SequentialMotionSearch : public MotionSearch {
public:
    SequentialMotionSearch(PGMImage *referenceFrame, PGMImage *targetFrame, unsigned int sizeOfMacroblock,
                           unsigned int searchWindowRadius) : MotionSearch(referenceFrame, targetFrame,
                                                                           sizeOfMacroblock, searchWindowRadius) {}

protected:
    MotionVectorDTO searchMotionVector(unsigned int x, unsigned int y) override;

};

#endif //ASSIGNMENT3_SEQUENTIALMOTIONSEARCH_H
