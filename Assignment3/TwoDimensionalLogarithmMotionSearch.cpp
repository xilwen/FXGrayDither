#include <limits>
#include <iostream>
#include <cmath>
#include "TwoDimensionalLogarithmMotionSearch.h"
#include "MotionSearchTools.h"

MotionVectorDTO TwoDimensionalLogarithmMotionSearch::searchMotionVector(unsigned int x, unsigned int y) {
    MotionVectorDTO motionVectorDTO;
    int centerX(static_cast<int>(x)), centerY(static_cast<int>(y));
    auto offset = static_cast<int>(ceil(static_cast<double>(getSearchWindowRadius()) / 2.0));
    MotionVectorDTO vectors[9];
    initializeVectorsWithOffsets(vectors);

    bool last = false;
    while (!last) {
        double minMAD = std::numeric_limits<double>::max();
        int currentPositionX(0), currentPositionY(0);
        MotionVectorDTO lastMotionVector;
        for (auto &vector : vectors) {
            currentPositionX = centerX + vector.u * offset;
            currentPositionY = centerY + vector.v * offset;
            if (checkFramePositionRange(static_cast<unsigned int>(centerX), static_cast<unsigned int>(centerY)) &&
                checkFramePositionRange(static_cast<unsigned int>(currentPositionX),
                                        static_cast<unsigned int>(currentPositionY))) {
                double currentMAD = MotionSearchTools::getMeanAbsoluteDifference(*referenceFrame, *targetFrame,
                                                                                 getsizeOfMacroBlock(),
                                                                                 x, y,
                                                                                 static_cast<unsigned int>(
                                                                                         currentPositionX) - x,
                                                                                 static_cast<unsigned int>(
                                                                                         currentPositionY) - y);
                if (currentMAD < minMAD) {
                    minMAD = currentMAD;
                    lastMotionVector.u = vector.u * offset;
                    lastMotionVector.v = vector.v * offset;
                }
            }
        }
        if (offset == 1) {
            last = true;
            motionVectorDTO.u = currentPositionX - static_cast<int>(x);
            motionVectorDTO.v = currentPositionY - static_cast<int>(y);
        }
        offset = static_cast<int>(ceil(static_cast<double>(offset) / 2.0));
        centerX = centerX + lastMotionVector.u;
        centerY = centerY + lastMotionVector.v;
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
