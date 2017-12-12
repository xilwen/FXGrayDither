import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class WavFileProcessor {
    private RandomAccessFile randomAccessFile;
    private final int sampleRate = 44100,
            bitDepth = 16,
            channels = 1;
    private int byteWritten = 0;
    private final double amplifier = Math.pow(2, 15) - 1;

    public void outputWaveFile(String filePath, ArrayList<Double> samples) throws Exception {
        initializeWaveFormat(filePath);
        writeSamples(samples);
        closeFile();
    }

    private void closeFile() throws IOException {
        randomAccessFile.seek(4);
        randomAccessFile.writeInt(Integer.reverseBytes(byteWritten + 36));
        randomAccessFile.seek(40);
        randomAccessFile.writeInt(Integer.reverseBytes(byteWritten));
        randomAccessFile.close();
    }

    private void writeSamples(ArrayList<Double> samples) throws IOException {
        for (double x : samples) {
            short hexSample = (short)(x * amplifier);
            randomAccessFile.writeShort(Short.reverseBytes(hexSample));
            byteWritten += 2;
        }
    }

    private void initializeWaveFormat(String filePath) throws IOException {
        randomAccessFile = new RandomAccessFile(filePath, "rw");
        randomAccessFile.setLength(0);
        randomAccessFile.writeBytes("RIFF");
        randomAccessFile.writeInt(0);
        randomAccessFile.writeBytes("WAVE");
        randomAccessFile.writeBytes("fmt ");
        randomAccessFile.writeInt(Integer.reverseBytes(16));
        randomAccessFile.writeShort(Short.reverseBytes((short) 1));
        randomAccessFile.writeShort(Short.reverseBytes((short) channels));
        randomAccessFile.writeInt(Integer.reverseBytes(sampleRate));
        randomAccessFile.writeInt(Integer.reverseBytes(sampleRate * bitDepth * channels / 8)); //Byte rate
        randomAccessFile.writeShort(Short.reverseBytes((short) (channels * bitDepth / 8))); // Block align
        randomAccessFile.writeShort(Short.reverseBytes((short) bitDepth)); // Bit Depth
        randomAccessFile.writeBytes("data");
        randomAccessFile.writeInt(0);//temporary
    }


}
