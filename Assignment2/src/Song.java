import java.util.ArrayList;

public class Song {
    private ArrayList<Integer> synthesizedFrequencies = new ArrayList<>();

    public void generateSynthesizedFrequencies(Melody track0, Melody track1) throws Exception {
        checkSampleRate(track0, track1);
        prepareTracksFrequencies(track0, track1);
        swapWhenTrack1Longer(track0, track1);

        firstFrequencyCompensation(track0);
        for (int i = 1; i < track0.getFrequencies().size(); ++i) {
            if (i > track1.getFrequencies().size()) {
                int synthesizedFrequency = 0; //TODO Frequency Modulation
                synthesizedFrequencies.add(synthesizedFrequency);
            }
        }
        lastFrequencyCompensation(track0, track1);
    }

    private void prepareTracksFrequencies(Melody track0, Melody track1) throws Exception {
        for (int i = 0; track0.getFrequencies().size() == 0 || track1.getFrequencies().size() == 0; ++i) {
            if (i > 0) {
                throw new Exception("one of the melody track are empty!");
            }
            track0.generateFrequency();
            track1.generateFrequency();
        }
    }

    private void lastFrequencyCompensation(Melody track0, Melody track1) {
        if (track1.getFrequencies().size() == track0.getFrequencies().size()) {
            synthesizedFrequencies.add(track1.getFrequencies().get(track1.getFrequencies().size() - 1))
        }
    }

    private void firstFrequencyCompensation(Melody track0) {
        if (track0.getFrequencies().size() > 0) {
            synthesizedFrequencies.add(track0.getFrequencies().get(0));
        }
    }

    private void swapWhenTrack1Longer(Melody track0, Melody track1) {
        if (track0.getFrequencies().size() < track1.getFrequencies().size()) {
            Melody temp = track0;
            track0 = track1;
            track1 = temp;
        }
    }

    private void checkSampleRate(Melody track0, Melody track1) throws Exception {
        if (track0.getSampleRate() != track1.getSampleRate()) {
            throw new Exception("Sample Rate of the input tracks are not the same!");
        }
    }
}
