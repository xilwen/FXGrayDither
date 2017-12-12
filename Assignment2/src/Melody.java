import java.util.ArrayList;

public class Melody {
    private int SampleRate = 44100;
    private ArrayList<NoteDTO> notes;
    private ArrayList<Integer> frequencies = new ArrayList<>();

    public Melody(ArrayList<NoteDTO> notes) {
        this.notes = notes;
    }

    public ArrayList<Integer> getFrequencies() {
        return frequencies;
    }

    public int getSampleRate() {
        return SampleRate;
    }

    public void generateFrequency() {
        frequencies.clear();
        for (NoteDTO x : notes) {
            for (int i = 0; i < (int) (x.length * (double) getSampleRate()); ++i) {
//                TODO data structure change
//                int newFrequency = (int) Math.sin(2.0 * Math.PI *
//                        (double) x.note.frequency * ((double) i / (double) getSampleRate()));
//                frequencies.add(newFrequency);
            }
        }
    }

    public void extendFrequenciesLengthWithZeroTo(int targetFrequenciesLength){
        for(int i = 0; i < targetFrequenciesLength - frequencies.size(); ++i){
            frequencies.add(1);
        }
    }
}
