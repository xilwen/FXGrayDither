#include "PGMImage.h"
#include <fstream>
#include <iostream>

PGMImage::PGMImage(std::string inputFile) {
    std::ifstream ifstream(inputFile);
    if (!ifstream.is_open()) {
        throw std::runtime_error(inputFile + " can not be opened.");
    }

    std::string formatCheckString;
    ifstream >> formatCheckString;
    if (formatCheckString != "P2" && formatCheckString != "p2") {
        throw std::runtime_error(inputFile + " is not PGM formatted.");
    }

    ifstream >> width >> height >> maxValue;
    short buffer;
    while (ifstream >> buffer) {
        pixelStorage.push_back(buffer);
    }
    ifstream.close();

    if (pixelStorage.size() != width * height) {
        throw std::runtime_error(inputFile + " is corrupted.");
    }
}

PGMImage::PGMImage(unsigned int width, unsigned int height) : width(width), height(height), maxValue(255) {
    for(int i = 0; i < width; ++i){
        for(int j = 0; j < height; ++j){
            pixelStorage.push_back(0);
        }
    }
}

short PGMImage::getPixel(unsigned int x, unsigned int y) {
    rangeCheck(x, y);
    return pixelStorage.at(y * width + x);
}

void PGMImage::setPixel(unsigned int x, unsigned int y, short value) {
    rangeCheck(x, y);
    pixelStorage.at(y * width + x) = value;
}

unsigned int PGMImage::getWidth() {
    return width;
}

unsigned int PGMImage::getHeight() {
    return height;
}

void PGMImage::rangeCheck(unsigned int x, unsigned int y) {
    if (x >= width || y >= height) {
        std::string errorMessage = std::to_string(x) + "," + std::to_string(y) + "out of image range";
        throw std::range_error(errorMessage);
    }
}

void PGMImage::saveToPath(std::string outputFile) {
    std::ofstream ofstream(outputFile);
    if (ofstream.is_open()) {
        throw std::runtime_error(outputFile + " can not be opened for writing.");
    }

    try {
        rangeCheck(width - 1, height - 1);
    } catch (std::range_error &re) {
        std::cout << re.what() << std::endl
                  << "file saving failed due to image corrupted";
    }

    ofstream << "P2 " << width << " " << height << " " << maxValue;
    for(short x : pixelStorage){
        ofstream << x;
    }
    ofstream.close();
}

bool PGMImage::checkIfPositionExist(unsigned int x, unsigned int y) {
    return !(x >= width || y >= height);
}
