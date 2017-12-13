import java.util.ArrayList;

public class FrequencyModulator {
    ArrayList<Double> samples;

    public FrequencyModulator(ArrayList<Double> samples) {
        this.samples = samples;
    }

    public ArrayList<Double> getResult(int frequency) {
        ArrayList<Double> result = new ArrayList<>();
        int songLength = samples.size() / Melody.getSampleRate();
        for (int i = 0; i < samples.size(); ++i) {
            double newSample = samples.get(i) *
                    Math.cos(2.0 * Math.PI * frequency * ((double) (i /*% (double) Melody.getSampleRate()*/) / (double) Melody.getSampleRate()));
            if(newSample > 1.0){
                System.out.println("WTF");
            }
            result.add(newSample);
        }
        return result;
    }

}
