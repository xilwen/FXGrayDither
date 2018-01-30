#include "SequentialMotionSearch.h"
#include "MotionSearchTools.h"
#include <limits>

MotionVectorDTO SequentialMotionSearch::searchMotionVector(unsigned int x, unsigned int y) {
    double minMAD = std::numeric_limits<double>::max();
    MotionVectorDTO motionVectorDTO;
    for (int i = -static_cast<int>(getSearchWindowRadius()); i < getSearchWindowRadius(); ++i) {
        for (int j = -static_cast<int>(getSearchWindowRadius()); j < getSearchWindowRadius(); ++j) {
            //TODO find a better cast way
            if (checkFramePositionRange(x, y) &&
                checkFramePositionRange(static_cast<unsigned int>(static_cast<int>(x)) + i,
                                        static_cast<unsigned int>(static_cast<int>(y) + j))) {
                double currentMAD = MotionSearchTools::getMeanAbsoluteDifference(*referenceFrame, *targetFrame,
                                                                                 getsizeOfMacroBlock(),
                                                                                 x, y, static_cast<unsigned int>(i),
                                                                                 static_cast<unsigned int>(j));
                if(currentMAD < minMAD){
                    minMAD = currentMAD;
                    motionVectorDTO.u = i;
                    motionVectorDTO.v = j;
                }
            }
        }
    }
    return motionVectorDTO;
}
