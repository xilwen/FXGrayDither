import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    private File file;
    private String path;
    private int simpleDitheringMatrixSize;
    private int inplaceDitheringMatrixSize;
    private InputImageLoader inputImageLoader;
    private GrayLevelProcessor grayLevelProcessor;
    private SimpleDitherProcessor simpleDitherProcessor;
    private InplaceDitherProcessor inplaceDitherProcessor;

    void init() {
        grayscaleVBox.disableProperty().setValue(true);
        simpleDitheringVBox.disableProperty().setValue(true);
        inplaceDitheringVBox.disableProperty().setValue(true);
        simpleDitheringCombobox.getItems().addAll(4, 9, 16, 64);
        inplaceDitheringCombobox.getItems().addAll(4, 9, 16, 64);

        openImageButton.setOnMouseClicked(e -> {
            FileChooser filechooser = new FileChooser();
            file = filechooser.showOpenDialog(null);
            if (file == null) {
                return;
            }
            path = file.getAbsolutePath();
            grayscaleVBox.disableProperty().setValue(false);
            simpleDitheringVBox.disableProperty().setValue(false);
            inplaceDitheringVBox.disableProperty().setValue(false);
            initImages();
        });

        simpleDitheringCombobox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                applySimpleDitheringMatrixSize();
            }
        });

        inplaceDitheringCombobox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                applyInplaceDitheringMatrixSize();
            }
        });

        saveGrayscaleImageButton.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Images (*.jpeg, *.jpg)", "*.jpg"));
            File saveFile = fileChooser.showSaveDialog(null);
            if (saveFile != null) {
                grayLevelProcessor.saveImage(saveFile.getAbsolutePath());
            }
        });

        saveSimpleDitheringImageButton.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Images (*.jpeg, *.jpg)", "*.jpg"));
            File saveFile = fileChooser.showSaveDialog(null);
            if (saveFile != null) {
                simpleDitherProcessor.saveImage(saveFile.getAbsolutePath());
            }
        });

        saveInplaceDitheringButton.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Images (*.jpeg, *.jpg)", "*.jpg"));
            File saveFile = fileChooser.showSaveDialog(null);
            if (saveFile != null) {
                inplaceDitherProcessor.saveImage(saveFile.getAbsolutePath());
            }
        });

        randomMatrixCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                applySimpleDitheringMatrixSize();
                applyInplaceDitheringMatrixSize();
            }
        });

        showDitheringMatrixButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("FXGrayDither");
            alert.setHeaderText("Dithering Matrices (Threshold Maps) Information");
            alert.setContentText("Simple Dithering: " + simpleDitherProcessor.getDitheringMatrixString() +
            "\nInplace Dithering: "+ inplaceDitherProcessor.getDitheringMatrixString());
            alert.showAndWait();
        });
    }

    private void initImages() {
        inputImageLoader = new InputImageLoader(path);
        Image image = inputImageLoader.getJavafxImage();
        inputImage.setImage(inputImageLoader.getJavafxImage());

        grayLevelProcessor = new GrayLevelProcessor(inputImageLoader.getMat());
        grayLevelProcessor.doProcess();
        grayscaleImage.setImage(grayLevelProcessor.getJavafxImage());

        simpleDitheringMatrixSize = 4;
        inplaceDitheringMatrixSize = 16;
        simpleDitheringCombobox.setValue(4);
        inplaceDitheringCombobox.setValue(16);
        applySimpleDitheringMatrixSize();
        applyInplaceDitheringMatrixSize();
    }

    private void applySimpleDitheringMatrixSize() {
        simpleDitheringMatrixSize = simpleDitheringCombobox.getValue();
        simpleDitherProcessor = new SimpleDitherProcessor(grayLevelProcessor.getMat());
        simpleDitherProcessor.setDitheringMatrixSize(simpleDitheringMatrixSize);
        if (randomMatrixCheckBox.isSelected()) {
            simpleDitherProcessor.setRandomFlag(true);
        } else {
            simpleDitherProcessor.setRandomFlag(false);
        }
        simpleDitherProcessor.doProcess();
        simpleDitheringImage.setImage(simpleDitherProcessor.getJavafxImage());
    }

    private void applyInplaceDitheringMatrixSize() {
        inplaceDitheringMatrixSize = inplaceDitheringCombobox.getValue();
        inplaceDitherProcessor = new InplaceDitherProcessor(grayLevelProcessor.getMat());
        inplaceDitherProcessor.setDitheringMatrixSize(inplaceDitheringMatrixSize);
        if (randomMatrixCheckBox.isSelected()) {
            inplaceDitherProcessor.setRandomFlag(true);
        } else {
            inplaceDitherProcessor.setRandomFlag(false);
        }
        inplaceDitherProcessor.doProcess();
        inplaceDitheringImage.setImage(inplaceDitherProcessor.getJavafxImage());
    }

    @FXML
    private ImageView inputImage;

    @FXML
    private JFXButton openImageButton;

    @FXML
    private VBox grayscaleVBox;

    @FXML
    private ImageView grayscaleImage;

    @FXML
    private JFXButton saveGrayscaleImageButton;

    @FXML
    private VBox simpleDitheringVBox;

    @FXML
    private ImageView simpleDitheringImage;

    @FXML
    private JFXButton saveSimpleDitheringImageButton;

    @FXML
    private JFXComboBox<Integer> simpleDitheringCombobox;

    @FXML
    private VBox inplaceDitheringVBox;

    @FXML
    private ImageView inplaceDitheringImage;

    @FXML
    private JFXButton saveInplaceDitheringButton;

    @FXML
    private JFXComboBox<Integer> inplaceDitheringCombobox;

    @FXML
    private JFXCheckBox randomMatrixCheckBox;

    @FXML
    private JFXButton showDitheringMatrixButton;
}
