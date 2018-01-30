#ifndef ASSIGNMENT3_MOTIONVECTORDTO_H
#define ASSIGNMENT3_MOTIONVECTORDTO_H


struct MotionVectorDTO {
public:
    int u = 0, v = 0;

    MotionVectorDTO() = default;

    MotionVectorDTO(int u, int v) : u(u), v(v) {}

};


#endif //ASSIGNMENT3_MOTIONVECTORDTO_H
