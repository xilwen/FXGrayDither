public class DitheringProcessor extends ImageHolder implements ImageProcessor {
    protected boolean alreadyProcessed;
    protected int ditheringMatrixSize = 0;
    protected int ditheringMatrixSqrt;
    protected int ditheringMatrix[][];
    protected boolean randomDitherMatrix = false;

    @Override
    public void doProcess() {
        throw new RuntimeException("This function should be overrided and set alreadyProcessed flag before return.");
    }

    @Override
    public boolean processed() {
        return alreadyProcessed;
    }

    protected void setDitherMatrix() {
        if (randomDitherMatrix) {
            setRandomDitherMatrix();
            return;
        }
        switch (ditheringMatrixSize) {
            case 4:
                ditheringMatrix = new int[][]{{0, 2}, {3, 1}};
                break;
            case 9:
                ditheringMatrix = new int[][]{{0, 7, 3}, {6, 5, 2}, {4, 1, 8}};
                break;
            case 16:
                ditheringMatrix = new int[][]{{0, 8, 2, 10}, {12, 4, 14, 6}, {3, 11, 1, 9}, {15, 7, 13, 5}};
                break;
            case 64:
                ditheringMatrix = new int[][]{{0, 48, 12, 60, 3, 51, 15, 63}, {32, 16, 44, 28, 35, 19, 47, 31},
                        {8, 56, 4, 52, 11, 59, 7, 55}, {40, 24, 36, 20, 43, 27, 39, 23},
                        {2, 50, 14, 62, 1, 49, 13, 61}, {34, 18, 46, 30, 33, 17, 45, 29},
                        {10, 58, 6, 54, 9, 57, 5, 53}, {42, 26, 38, 22, 41, 25, 37, 21}};
                break;
            default:
                throw new RuntimeException("Matrix size invalid or setDitheringMatrixSize() has not been called.");
        }
    }

    protected void setRandomDitherMatrix() {
        ditheringMatrix = new int[ditheringMatrixSqrt][ditheringMatrixSqrt];

        for (int i = 0; i < ditheringMatrixSqrt; ++i) {
            for (int j = 0; j < ditheringMatrixSqrt; ++j) {
                boolean repetitionFlag = false;
                do {
                    ditheringMatrix[i][j] = (int) (Math.random() * ditheringMatrixSize + 1);
                    repetitionFlag = checkRepetition(i, j, repetitionFlag);
                } while (repetitionFlag);
            }
        }

        for (int i = 0; i < ditheringMatrixSqrt; ++i) {
            for (int j = 0; j < ditheringMatrixSqrt; ++j) {
                --ditheringMatrix[i][j];
            }
        }
    }

    public void setRandomFlag(boolean flag) {
        randomDitherMatrix = flag;
    }

    private boolean checkRepetition(int i, int j, boolean repetitionFlag) {
        repetitionFlag = false;
        for (int k = 0; k <= i; ++k) {
            for (int m = 0; m < ditheringMatrixSqrt && !(k == i && m == j); ++m) {
                if (ditheringMatrix[i][j] == ditheringMatrix[k][m])
                    repetitionFlag = true;
            }
        }
        return repetitionFlag;
    }

    public void setDitheringMatrixSize(int ditheringMatrixSize) {
        this.ditheringMatrixSize = ditheringMatrixSize;
        this.ditheringMatrixSqrt = (int) Math.sqrt(ditheringMatrixSize);
    }

    public String getDitheringMatrixString() {
        StringBuilder stringBuilder = new StringBuilder(((Integer) ditheringMatrixSqrt).toString() + " x " +
                ((Integer) ditheringMatrixSqrt).toString() + " Matrix\n");
        for(int i = 0; i < ditheringMatrixSqrt; ++i){
            for(int j = 0; j < ditheringMatrixSqrt; ++j){
                stringBuilder.append(((Integer)ditheringMatrix[i][j]).toString() + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
