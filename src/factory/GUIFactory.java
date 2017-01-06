package factory;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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

    public static void newPopupDialog(Stage stage, String message){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(message));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
