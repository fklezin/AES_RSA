package factory;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by fklezin on 4.11.2016.
 */
public class GUIFactory {

    public static String openFileChooserGetFilePath(Stage stage){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            return  selectedFile.getAbsolutePath();
        }

        return "Error";
    }

    public static File saveFileDialog (Stage stage) {
        FileChooser fileChooser = new FileChooser();

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            return file;
        }
        return null;
    }
}
