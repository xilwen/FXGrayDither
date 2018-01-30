#include <iostream>
#include <chrono>
#include "PGMImage.h"
#include "SequentialMotionSearch.h"
#include "TwoDimensionalLogarithmMotionSearch.h"
#include "MotionSearchTools.h"

int main(int argc, char **argv) {
    if (argc != 7) {
        std::cout << "usage: -2D|-s MacroBlockSize SearchWindowRadius <ReferencePGM> <TargetPGM> <OutputPGM> "
                  << std::endl <<
                  "For example, ThisProgram -s 16 15 i1.pgm i2.pgm i2p.pgm" << std::endl;
        exit(EXIT_FAILURE);
    }

    PGMImage *referenceImage = nullptr, *targetImage = nullptr, *predictedImage = nullptr;
    MotionSearch *motionSearch = nullptr;
    unsigned int macroBlockSize(0), searchWindowRadius(0);

    try {
        referenceImage = new PGMImage(std::string(argv[4]));
        targetImage = new PGMImage(std::string(argv[5]));
        macroBlockSize = static_cast<unsigned int>(std::stoi(std::string(argv[2])));
        searchWindowRadius = static_cast<unsigned int>(std::stoi(std::string(argv[3])));

        if (std::string(argv[1]) == "-2D" || std::string(argv[1]) == "-2d") {
            motionSearch = new TwoDimensionalLogarithmMotionSearch(referenceImage, targetImage, macroBlockSize,
                                                                   searchWindowRadius);
        } else if (std::string(argv[1]) == "-s") {
            motionSearch = new SequentialMotionSearch(referenceImage, targetImage, macroBlockSize,
                                                      searchWindowRadius);
        } else {
            std::cout << "Invalid argument. use -2D|-s to set algorithm." << std::endl;
            exit(EXIT_FAILURE);
        }

        auto searchStartTime = std::chrono::high_resolution_clock::now();
        motionSearch->FindMotionVectors();
        auto searchEndTime = std::chrono::high_resolution_clock::now();
        std::cout << "search for motion used "
                  << std::chrono::duration<double, std::milli>(searchEndTime - searchStartTime).count() << "ms"
                  << std::endl;

        predictedImage = new PGMImage( motionSearch->getResultImage() );
        std::cout << "SNR = " << MotionSearchTools::getSignalNoiseRatio(*targetImage, *predictedImage) << std::endl;
        predictedImage->saveToPath(std::string(argv[6]));
    } catch (std::exception &e) {
        std::cout << e.what() << std::endl;
        exit(EXIT_FAILURE);
    }

    return 0;
}