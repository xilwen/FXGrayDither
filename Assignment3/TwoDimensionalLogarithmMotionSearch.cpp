#include <limits>
#include "TwoDimensionalLogarithmMotionSearch.h"
#include "MotionSearchTools.h"

MotionVectorDTO TwoDimensionalLogarithmMotionSearch::searchMotionVector(unsigned int x, unsigned int y) {
    MotionVectorDTO motionVectorDTO;
    int centerX(x), centerY(y);
    auto offset = static_cast<int>(getSearchWindowRadius());
    MotionVectorDTO vectors[9];
    initializeVectorsWithOffsets(vectors);

    bool last = false;
    while (!last) {
        double minMAD = std::numeric_limits<double>::max();
        int currentPositionX(0), currentPositionY(0);
        for (auto &vector : vectors) {
            currentPositionX = centerX + vector.u * offset;
            currentPositionY = centerY + vector.v * offset;
            if (checkFramePositionRange(static_cast<unsigned int>(centerX), static_cast<unsigned int>(centerY)) &&
                checkFramePositionRange(static_cast<unsigned int>(currentPositionX),
                                        static_cast<unsigned int>(currentPositionY))) {
                double currentMAD = MotionSearchTools::getMeanAbsoluteDifference(*referenceFrame, *targetFrame,
                                                                                 getsizeOfMacroBlock(),
                                                                                 static_cast<unsigned int>(centerX),
                                                                                 static_cast<unsigned int>(centerY),
                                                                                 static_cast<unsigned int>(
                                                                                         currentPositionX - centerX),
                                                                                 static_cast<unsigned int>(
                                                                                         currentPositionY - centerY));
                if (currentMAD < minMAD) {
                    minMAD = currentMAD;
                }
            }
        }
        if (offset == 1) {
            last = true;
            motionVectorDTO.u = currentPositionX - x;
            motionVectorDTO.v = currentPositionY - y;
        }
        offset = static_cast<int>(static_cast<double>(offset) / 2.0);
        centerX = currentPositionX;
        centerY = currentPositionY;
    }
    return motionVectorDTO;
}

void TwoDimensionalLogarithmMotionSearch::initializeVectorsWithOffsets(MotionVectorDTO *vectors) {
    for (int index = 0, i = -1; i <= 1; ++i) {
        for (int j = -1; j <= 1; ++j) {
            vectors[index].u = i;
            vectors[index].v = j;
            ++index;
        }
    }
}
