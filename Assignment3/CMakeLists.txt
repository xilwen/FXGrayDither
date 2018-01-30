cmake_minimum_required(VERSION 3.9)
project(Assignment3)

set(CMAKE_CXX_STANDARD 11)

add_executable(Assignment3 main.cpp PGMImage.cpp PGMImage.h MotionSearchTools.cpp MotionSearchTools.h MotionSearch.cpp MotionSearch.h SequentialMotionSearch.cpp SequentialMotionSearch.h TwoDimensionalLogarithmMotionSearch.cpp TwoDimensionalLogarithmMotionSearch.h MotionVectorDTO.h)
set_target_properties(Assignment3 PROPERTIES LINK_FLAGS "-static" )