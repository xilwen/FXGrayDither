#ifndef ASSIGNMENT3_PGMIMAGE_H
#define ASSIGNMENT3_PGMIMAGE_H

#include <string>
#include <vector>

class PGMImage {
public:
    PGMImage() : width(0), height(0), maxValue(255) {}
    explicit PGMImage(std::string inputFile);
    PGMImage(unsigned int width, unsigned int height);
    PGMImage(const PGMImage &input);

    short getPixel (unsigned int x, unsigned int y);
    void setPixel(unsigned int x, unsigned int y, short value);
    unsigned int getWidth();
    unsigned int getHeight();
    void saveToPath(std::string outputFile);
    bool checkIfPositionExist(unsigned int x, unsigned int y);

private:
    unsigned int width = 0, height = 0, maxValue = 255;
    std::vector<short> pixelStorage;

    void rangeCheck(unsigned int x, unsigned int y);
};


#endif //ASSIGNMENT3_PGMIMAGE_H
