import java.util.ArrayList;

public class Melody {
    private int SampleRate = 44100;
    private ArrayList<NoteDTO> notes;
    private ArrayList<Double> samples = new ArrayList<>();

    public Melody(ArrayList<NoteDTO> notes) {
        this.notes = notes;
    }

    public ArrayList<Double> getSamples() {
        return samples;
    }

    public int getSampleRate() {
        return SampleRate;
    }

    public void generateFrequency() {
        samples.clear();
        for (NoteDTO x : notes) {
            for (int i = 0; i < (int) (x.length * (double) getSampleRate()); ++i) {
                double newSample = Math.sin(2.0 * Math.PI *
                        (double) x.frequency * ((double) i / (double) getSampleRate()));
                samples.add(newSample);
            }
        }
    }

    public void extendFrequenciesLengthWithZeroTo(int targetFrequenciesLength){
        for(int i = 0; i < targetFrequenciesLength - samples.size(); ++i){
            samples.add(1.0);
        }
    }
}
