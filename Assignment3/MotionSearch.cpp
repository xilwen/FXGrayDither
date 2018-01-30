#include <stdexcept>
#include <iostream>
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
    if (motionVectors.empty()) {
        throw std::runtime_error("can not get motion vectors while building prediction frame");
    }
    predictionFrame = new PGMImage(targetFrame->getWidth(), targetFrame->getHeight());

    int pixelCount(0);
    for (int x = 0; x < targetFrame->getWidth(); x += sizeOfMacroblock) {
        for (int y = 0; y < targetFrame->getHeight(); y += sizeOfMacroblock) {

            for (int i = 0; i < sizeOfMacroblock; ++i) {
                for (int j = 0; j < sizeOfMacroblock; ++j) {
                    predictionFrame->setPixel(static_cast<unsigned int>(x + i), static_cast<unsigned int>(y + j),
                                              targetFrame->getPixel(
                                                      static_cast<unsigned int>(
                                                              x + motionVectors[pixelCount].u + i),
                                                      static_cast<unsigned int>(
                                                              y + motionVectors[pixelCount].v + j)
                                              )
                    );
                }
            }
            ++pixelCount;

        }
    }
    return *predictionFrame;
}

bool MotionSearch::checkFramePositionRange(unsigned int x, unsigned int y) {
    return !(x >= referenceFrame->getWidth() || y >= referenceFrame->getHeight() ||
             x >= (referenceFrame->getWidth() - sizeOfMacroblock) ||
             y >= (referenceFrame->getHeight() - sizeOfMacroblock));
}
