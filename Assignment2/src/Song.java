import java.util.ArrayList;

public class Song {
    private ArrayList<Double> synthesizedSamples = new ArrayList<>();
    private Melody bassoTrack, altoTrack;

    public Song(Melody bassoTrack, Melody altoTrack){
        this.bassoTrack = bassoTrack;
        this.altoTrack = altoTrack;
    }

    public void generateSynthesizedFrequencies() throws Exception {
        checkSampleRate(bassoTrack, altoTrack);
        prepareTracksFrequencies(bassoTrack, altoTrack);
        alignTracks(bassoTrack, altoTrack);

        firstFrequencyCompensation(bassoTrack);
        for (int i = 1; i < bassoTrack.getSamples().size(); ++i) {
            if (i < altoTrack.getSamples().size()) {
                double synthesizedSample = bassoTrack.getSamples().get(i) + altoTrack.getSamples().get(i - 1);
                synthesizedSamples.add(synthesizedSample);
            }
        }
        lastFrequencyCompensation(bassoTrack, altoTrack);
    }

    private void prepareTracksFrequencies(Melody track0, Melody track1) throws Exception {
        for (int i = 0; track0.getSamples().size() == 0 || track1.getSamples().size() == 0; ++i) {
            if (i > 0) {
                throw new Exception("one of the melody track are empty!");
            }
            track0.generateFrequency();
            track1.generateFrequency();
        }
    }

    private void lastFrequencyCompensation(Melody track0, Melody track1) {
        if (track1.getSamples().size() == track0.getSamples().size()) {
            synthesizedSamples.add(track1.getSamples().get(track1.getSamples().size() - 1));
        }
    }

    private void firstFrequencyCompensation(Melody track0) {
        if (track0.getSamples().size() > 0) {
            synthesizedSamples.add(track0.getSamples().get(0));
        }
    }

    private void alignTracks(Melody track0, Melody track1) {
        track0.extendFrequenciesLengthWithZeroTo(track1.getSamples().size());
        track1.extendFrequenciesLengthWithZeroTo(track0.getSamples().size());
    }

    private void checkSampleRate(Melody track0, Melody track1) throws Exception {
        if (track0.getSampleRate() != track1.getSampleRate()) {
            throw new Exception("Sample Rate of the input tracks are not the same!");
        }
    }
}
