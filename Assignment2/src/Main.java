import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;

import java.io.File;

public class Main extends Application {
    private SongReader songReader;
    private Song song;
    private File file;
    private Label filePathLabel;
    private Button saveFileButton;
    private Button playFileButton;
    private Button fmSaveFileButton;
    private Button fmPlayFileButton;
    private Scene scene;
    private File saveFile;
    private File[] fmSaveFile = new File[3];
    private MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbox = new VBox();

        HBox inputFileHBox = fileChooser();
        HBox outputFileHBox = outputFileHandler();
        HBox fmOutputFileHBox = frequencyModulationHandler();

        vbox.getChildren().addAll(inputFileHBox, outputFileHBox, fmOutputFileHBox);
        primaryStage.setTitle("Assignment 2 : Generating Sounds of Different Pitch Tones");
        scene = new Scene(vbox, 640, 128);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox frequencyModulationHandler() {
        HBox fmOutputFileHBox = new HBox();
        fmOutputFileHBox.setAlignment(Pos.CENTER_LEFT);
        fmOutputFileHBox.setSpacing(15);
        fmOutputFileHBox.setPadding(new Insets(5, 10, 5, 10));
        Label fmLabel = new Label("Frequency Modulation : ");
        ComboBox<Integer> fmFrequencyComboBox = new ComboBox<>();
        fmFrequencyComboBox.setValue(100);
        Label fmHzLabel = new Label("Hz");
        fmSaveFileButton = new Button("Save WAV");
        fmSaveFileButton.setDisable(true);

        fmSaveFileButton.setOnMouseClicked(e -> {
            fmSaveFileButton.setText("Please wait...");
            int targetIndex = fmFrequencyComboBox.getItems().indexOf(fmFrequencyComboBox.getValue());

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV (*.wave, *.wav)", "*.wav"));
            fileChooser.setInitialFileName(file.getName().substring(0, file.getName().indexOf('.'))
                    + "_fm" + fmFrequencyComboBox.getValue());
            fmSaveFile[targetIndex] = fileChooser.showSaveDialog(null);

            if (fmSaveFile[targetIndex] == null) {
                fmSaveFileButton.setText("Save WAV");
                return;
            }

            FrequencyModulator frequencyModulator = new FrequencyModulator(song.getSynthesizedSamples());
            WavFileProcessor wavFileProcessor = new WavFileProcessor();
            try {
                wavFileProcessor.outputWaveFile(
                        fmSaveFile[targetIndex].getAbsolutePath(),
                        frequencyModulator.getResult(fmFrequencyComboBox.getValue()));
            } catch (Exception e1) {
                showExceptionError(e1);
                saveFileButton.setText("Save WAV");
                return;
            }
            fmPlayFileButton.setDisable(false);
            fmSaveFileButton.setText("Save WAV");
        });

        fmPlayFileButton = new Button("Play");
        fmPlayFileButton.setDisable(true);

        fmPlayFileButton.setOnMouseClicked(e->{
            int targetIndex = fmFrequencyComboBox.getItems().indexOf(fmFrequencyComboBox.getValue());
            try {
                Media sound = new Media(fmSaveFile[targetIndex].toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }catch(Exception e1){
                showExceptionError(e1);
            }
        });

        fmFrequencyComboBox.getItems().addAll(100, 500, 800);
        fmFrequencyComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                int targetIndex = fmFrequencyComboBox.getItems().indexOf(fmFrequencyComboBox.getValue());
                if(fmSaveFile[targetIndex] != null && fmSaveFile[targetIndex].exists()){
                    fmPlayFileButton.setDisable(false);
                }else{
                    fmPlayFileButton.setDisable(true);
                }
            }
        });
        fmOutputFileHBox.getChildren().addAll(fmLabel, fmFrequencyComboBox, fmHzLabel, fmSaveFileButton, fmPlayFileButton);
        return fmOutputFileHBox;
    }

    private HBox outputFileHandler() {
        HBox outputFileHBox = new HBox();
        outputFileHBox.setAlignment(Pos.CENTER_LEFT);
        outputFileHBox.setSpacing(15);
        outputFileHBox.setPadding(new Insets(5, 10, 5, 10));
        Label tonalSoundLabel = new Label("Tonal Sound Generation : ");
        saveFileButton = new Button("Save WAV");
        saveFileButton.setDisable(true);

        saveFileButton.setOnMouseClicked(e -> {
            saveFileButton.setText("Please wait...");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV (*.wave, *.wav)", "*.wav"));
            fileChooser.setInitialFileName(file.getName().substring(0, file.getName().indexOf('.')));
            saveFile = fileChooser.showSaveDialog(null);
            if (saveFile == null) {
                saveFileButton.setText("Save WAV");
                return;
            }
            try {
                song.generateSynthesizedFrequencies();
            } catch (Exception e1) {
                showExceptionError(e1);
                saveFileButton.setText("Save WAV");
                return;
            }
            WavFileProcessor wavFileProcessor = new WavFileProcessor();
            try {
                wavFileProcessor.outputWaveFile(saveFile.getAbsolutePath(), song.getSynthesizedSamples());
            } catch (Exception e1) {
                showExceptionError(e1);
                saveFileButton.setText("Save WAV");
                return;
            }
            saveFileButton.setText("Save WAV");
            fmSaveFileButton.setDisable(false);
            playFileButton.setDisable(false);
        });

        playFileButton = new Button("Play");
        playFileButton.setDisable(true);

        playFileButton.setOnMouseClicked(e -> {
            Media sound = new Media(saveFile.toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        });
        outputFileHBox.getChildren().addAll(tonalSoundLabel, saveFileButton, playFileButton);
        return outputFileHBox;
    }

    private void showExceptionError(Exception e1) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e1.getMessage());
        alert.showAndWait();
        saveFileButton.setText("Save WAV");
    }

    private HBox fileChooser() {
        HBox inputFileHBox = new HBox();
        inputFileHBox.setAlignment(Pos.CENTER_LEFT);
        inputFileHBox.setSpacing(15);
        inputFileHBox.setPadding(new Insets(10, 10, 5, 10));
        Button openFileButton = new Button("Open input text File");
        openFileButton.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            file = fileChooser.showOpenDialog(null);
            if (file == null) {
                saveFileButton.setDisable(true);
                playFileButton.setDisable(true);
                fmSaveFileButton.setDisable(true);
                fmPlayFileButton.setDisable(true);
                filePathLabel = new Label("Nothing loaded yet.");
                return;
            }
            filePathLabel.setText(file.getAbsolutePath());
            saveFile = null;
            fmSaveFile = new File[3];
            try {
                songReader = new SongReader(file.getAbsolutePath());
            } catch (Exception e1) {
                showExceptionError(e1);
                saveFileButton.setDisable(true);
                playFileButton.setDisable(true);
                fmSaveFileButton.setDisable(true);
                fmPlayFileButton.setDisable(true);
                filePathLabel = new Label("Nothing loaded yet.");
                return;
            }
            song = new Song(songReader.getBassoTrack(), songReader.getAltoTrack());
            saveFileButton.setDisable(false);
        });
        filePathLabel = new Label("Nothing loaded yet.");
        inputFileHBox.getChildren().addAll(openFileButton, filePathLabel);
        return inputFileHBox;
    }
}
