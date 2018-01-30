#include <stdexcept>
#include "MotionSearch.h"

MotionSearch::MotionSearch(PGMImage *referenceFrame, PGMImage *targetFrame, unsigned int sizeOfMacroblock,
                           unsigned int searchWindowRadius) : referenceFrame(referenceFrame),
                                                              targetFrame(targetFrame),
                                                              sizeOfMacroblock(sizeOfMacroblock),
                                                              searchWindowRadius(searchWindowRadius) {
    if (!referenceFrame || !targetFrame) {
        throw std::runtime_error("frame(s) are not ready (nullptr passed)");
    }
}

void MotionSearch::FindMotionVectors() {
    if (referenceFrame->getHeight() != targetFrame->getHeight() ||
        referenceFrame->getWidth() != referenceFrame->getWidth()) {
        throw std::runtime_error("reference frame size are not the same as target frame");
    }
    unsigned int rowCount(referenceFrame->getWidth()),
            columnCount(referenceFrame->getHeight());

    for (unsigned int x = 0; x < rowCount; x += sizeOfMacroblock) {
        for (unsigned int y = 0; y < columnCount; y += sizeOfMacroblock) {
            motionVectors.push_back(searchMotionVector(x, y));
        }
    }
}

MotionVectorDTO MotionSearch::searchMotionVector(unsigned int x, unsigned int y) {
    throw std::runtime_error("Search Method has not been initialized, use derived class with implementation");
}

PGMImage MotionSearch::getResultImage() {

}
