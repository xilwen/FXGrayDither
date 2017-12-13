import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SongReader {
    private Scanner scanner;
    private File file;
    private Melody altoTrack, bassoTrack;
    private String altoString = "alto:",
            bassoString = "basso:";

    private enum Track {BASSO, ALTO}

    private enum ReaderStatus {LEVEL, INTONATION, NOTE, LENGTH}

    private Track trackStatus = Track.ALTO;
    private ReaderStatus readerStatus = ReaderStatus.LEVEL;
    private ArrayList<NoteDTO> altoNotes = new ArrayList<>(),
            bassoNotes = new ArrayList<>();

    public SongReader(String filePath) throws Exception {
        file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("file did not exist!");
        }
        parseSong();
    }

    private void parseSong() throws Exception {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new Exception("file can not be scanned!");
        }

        while (scanner.hasNext()) {
            String parseString = scanner.nextLine();

            int altoKeywordPosition = parseString.indexOf(altoString),
                    bassoKeywordPosition = parseString.indexOf(bassoString),
                    noteStartPosition;

            //TODO extract
            if (altoKeywordPosition >= 0) {
                trackStatus = Track.ALTO;
                noteStartPosition = altoKeywordPosition + altoString.length();
            } else if (bassoKeywordPosition >= 0) {
                trackStatus = Track.BASSO;
                noteStartPosition = bassoKeywordPosition + bassoString.length();
            } else {
                throw new Exception("file format error. should have track name at the begin of file.");
            }

            //TODO extract
            NoteDTO newNote = new NoteDTO();
            int level = 0, note = 0;
            boolean sharp = true;
            for (int i = noteStartPosition; i < parseString.length(); ++i) {
                if (Character.isDigit(parseString.charAt(i))) {
                    switch (readerStatus) {
                        case LEVEL:
                            level = parseString.charAt(i) - '0';
                            readerStatus = ReaderStatus.INTONATION;
                            break;
                        case INTONATION:
                            throw new Exception("file format error. Intonation should be _ or +.");
                        case NOTE:
                            note = parseString.charAt(i) - '0';
                            readerStatus = ReaderStatus.LENGTH;
                            break;
                        case LENGTH:
                            i = parseLength(parseString, newNote, i);
                            break;
                    }
                } else if (parseString.charAt(i) == ' ') {
                } else if (parseString.charAt(i) == '(' && readerStatus == ReaderStatus.LENGTH) {
                } else if (parseString.charAt(i) == ')' && readerStatus == ReaderStatus.LENGTH) {
                    writeFrequency(newNote, level, note, sharp);
                    newNote = new NoteDTO();
                    readerStatus = ReaderStatus.LEVEL;
                } else if (parseString.charAt(i) == '+') {
                    sharp = true;
                    readerStatus = ReaderStatus.NOTE;
                } else if (parseString.charAt(i) == '_') {
                    sharp = false;
                    readerStatus = ReaderStatus.NOTE;
                } else {
                    throw new Exception("Unknown parsing error");
                }
            }
        }

    }

    private int parseLength(String parseString, NoteDTO newNote, int i) {
        if (parseString.charAt(i + 1) == '.') {
            int j = i + 2;
            for (; j < parseString.length(); ++j) {
                if (!Character.isDigit(parseString.charAt(j))) {
                    break;
                }
            }
            newNote.length = Double.valueOf(parseString.substring(i, j));
            i = j - 1;
        } else if (Character.isDigit(parseString.charAt(i + 1))) {
            int j = i + 2;
            for (; j < parseString.length(); ++j) {
                if (!Character.isDigit(parseString.charAt(j))) {
                    break;
                }
            }
            newNote.length = Integer.valueOf(parseString.substring(i, j));
            i = j - 1;
        } else {
            newNote.length = parseString.charAt(i) - '0';
        }
        return i;
    }

    private void writeFrequency(NoteDTO newNote, int level, int note, boolean sharp) {
        int targetNote = -1;
        if (sharp) {
            switch (note) {
                case 1:
                    targetNote = NotesFrequenciesMapping.CSharp;
                    break;
                case 2:
                    targetNote = NotesFrequenciesMapping.DSharp;
                    break;
                case 4:
                    targetNote = NotesFrequenciesMapping.FSharp;
                    break;
                case 5:
                    targetNote = NotesFrequenciesMapping.GSharp;
                    break;
                case 6:
                    targetNote = NotesFrequenciesMapping.ASharp;
                    break;
            }
        } else {
            switch (note) {
                case 1:
                    targetNote = NotesFrequenciesMapping.C;
                    break;
                case 2:
                    targetNote = NotesFrequenciesMapping.D;
                    break;
                case 3:
                    targetNote = NotesFrequenciesMapping.E;
                    break;
                case 4:
                    targetNote = NotesFrequenciesMapping.F;
                    break;
                case 5:
                    targetNote = NotesFrequenciesMapping.G;
                    break;
                case 6:
                    targetNote = NotesFrequenciesMapping.A;
                    break;
                case 7:
                    targetNote = NotesFrequenciesMapping.B;
                    break;
            }
        }
        if (targetNote == -1) {
            newNote.frequency = 1;
        } else {
            newNote.frequency = NotesFrequenciesMapping.freqencies[level][targetNote];
        }
        if (trackStatus == Track.ALTO) {
            altoNotes.add(newNote);
        } else if (trackStatus == Track.BASSO) {
            bassoNotes.add(newNote);
        }
    }

    public Melody getAltoTrack() {
        altoTrack = new Melody(altoNotes);
        return altoTrack;
    }

    public Melody getBassoTrack() {
        bassoTrack = new Melody(bassoNotes);
        return bassoTrack;
    }

    public static void main(String[] args) throws Exception{
        SongReader songReader = new SongReader("D:\\test.txt");
        System.out.println(songReader.altoNotes.size());
        System.out.println(songReader.bassoNotes.size());
        Song song = new Song(songReader.getBassoTrack(), songReader.getAltoTrack());
        song.generateSynthesizedFrequencies();
        WavFileProcessor wavFileProcessor = new WavFileProcessor();
        wavFileProcessor.outputWaveFile("D:\\test.wav", song.getSynthesizedSamples());
        FrequencyModulator frequencyModulator = new FrequencyModulator(song.getSynthesizedSamples());
        wavFileProcessor.outputWaveFile("D:\\fmtest.wav", frequencyModulator.getResult(800));
    }
}
