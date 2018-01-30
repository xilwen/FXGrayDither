#include <iostream>
#include "PGMImage.h"

int main(int argc, char **argv) {

    if (argc != 2) {
        std::cout << "usage: <PGMInput>" << std::endl;
    }
    PGMImage *pgmImage = nullptr;
    try {
        pgmImage = new PGMImage(std::string(argv[1]));
        std::cout << pgmImage->getWidth() << "X" << pgmImage->getHeight();
    } catch (std::exception &e) {
        std::cout << e.what();
    }
    return 0;
}