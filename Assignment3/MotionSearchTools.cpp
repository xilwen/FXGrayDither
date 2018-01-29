#include "MotionSearchTools.h"
#include <cmath>
#include <stdexcept>

double MotionSearchTools::getMeanAbsoluteDifference(PGMImage &referenceFrame, PGMImage &targetFrame,
                                                    unsigned int sizeOfBlock,
                                                    unsigned int x,
                                                    unsigned int y,
                                                    unsigned int iHorizontalDisplacement,
                                                    unsigned int jVerticalDisplacement) {
    double meanAbsoluteDifference(0);
    for (auto kIndexOfPixelInBlock = 0; kIndexOfPixelInBlock < sizeOfBlock; ++kIndexOfPixelInBlock) {
        for (auto lIndexOfPixelInBlock = 0; lIndexOfPixelInBlock < sizeOfBlock; ++lIndexOfPixelInBlock) {
            auto pixelInTargetFrame = static_cast<double>(
                    targetFrame.getPixel(x + kIndexOfPixelInBlock, y + lIndexOfPixelInBlock));
            auto pixelInReferenceFrame = static_cast<double>(
                    referenceFrame.getPixel(
                            x + iHorizontalDisplacement + kIndexOfPixelInBlock,
                            y + jVerticalDisplacement + lIndexOfPixelInBlock));

            meanAbsoluteDifference += std::abs(pixelInTargetFrame - pixelInReferenceFrame);
        }
    }
    meanAbsoluteDifference /= pow(sizeOfBlock, 2);
    return meanAbsoluteDifference;
}

double MotionSearchTools::getSignalNoiseRatio(PGMImage &targetFrame, PGMImage &predictedFrame) {
    if ((targetFrame.getWidth() != predictedFrame.getWidth()) ||
        (targetFrame.getHeight() != targetFrame.getWidth())) {
        throw std::runtime_error("Not the same image, can not get SNR");
    }
    double dividend(0), divisor(0);
    for (unsigned int x = 0; x < predictedFrame.getWidth(); ++x) {
        for (unsigned int y = 0; y < predictedFrame.getHeight(); ++y) {
            dividend += pow(predictedFrame.getPixel(x, y), 2);
            divisor += pow(targetFrame.getPixel(x, y) - predictedFrame.getPixel(x, y), 2);
        }
    }
    double snr = dividend / divisor;
    return snr;
}
