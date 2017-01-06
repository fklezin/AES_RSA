import crypto.*;
import factory.GUIFactory;
import factory.InputOutputStreamHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

public class Main extends Application{

    private Scene mainScene;
    private AES aes;
    private RSA rsa;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AES_RSA");
        this.mainScene = new Scene(root);

        AesTabInit();
        RSATabInit();

        this.primaryStage.setScene(this.mainScene);
        this.primaryStage.show();
    }

    private void AesTabInit() {
        TextField aesKeyTextField = (TextField) mainScene.lookup("#AESkeyTextField");
        Button aesBrowseBtnKey  = (Button) mainScene.lookup("#AESbrowseBtnKey");
        aesBrowseBtnKey.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"keys");
            aesKeyTextField.setText(filePath);
        });

        TextField aesKeylengthTextField = (TextField) mainScene.lookup("#AESkeylengthTextField");

        TextField aesInputFileTextField = (TextField) mainScene.lookup("#AESinputFileTextField");
        Button aesBrowseBtnInputFile  = (Button) mainScene.lookup("#AESbrowseBtnInputFile");
        aesBrowseBtnInputFile.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"documents/AES");
            aesInputFileTextField.setText(filePath);
        });

        TextField aesOutputFileTextField = (TextField) mainScene.lookup("#AESoutputFileTextField");
        Button aesBrowseBtnOutputFile  = (Button) mainScene.lookup("#AESbrowseBtnOutputFile");
        aesBrowseBtnOutputFile.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"documents/AES");
            aesOutputFileTextField.setText(filePath);
        });

        Button aesEncryptBtn  = (Button) mainScene.lookup("#AESencryptBtn");
        aesEncryptBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String key = aesKeyTextField.getText();
            String keylength = aesKeylengthTextField.getText();
            String input = aesInputFileTextField.getText();
            String output = aesOutputFileTextField.getText();

            if (validateAES(key,keylength,input,output)){
                try {
                    this.aes = new AES(Integer.parseInt(keylength),key,input,output);
                    aes.encrypt();
                } catch (AESException e) {
                    GUIFactory.newPopupDialog(this.primaryStage, ("ERROR: AES EXCEPTION\n"+e.getMessage()) );
                    e.printStackTrace();
                }
            }
        });

        Button aesDecryptBtn  = (Button) mainScene.lookup("#AESdecryptBtn");
        aesDecryptBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String key = aesKeyTextField.getText();
            String keylength = aesKeylengthTextField.getText();
            String input = aesInputFileTextField.getText();
            String output = aesOutputFileTextField.getText();

            if (validateAES(key,keylength,input,output)){
                try {
                    this.aes = new AES(Integer.parseInt(keylength),key,input,output);
                    aes.decrypt();
                } catch (AESException e) {
                    GUIFactory.newPopupDialog(this.primaryStage, ("ERROR: AES EXCEPTION\n"+e.getMessage()) );
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validateAES(String key, String keylength, String input, String output) {

        if (key.isEmpty()||keylength.isEmpty()||input.isEmpty()||output.isEmpty()){
            GUIFactory.newPopupDialog(this.primaryStage,"ERROR: ONE OF THE FIELDS IS EMPTY");
            return false;
        }

        try {
            Integer.parseInt(keylength);
        } catch (NumberFormatException e) {
            GUIFactory.newPopupDialog(this.primaryStage,"ERROR: BAD KEY VALUE");
            return false;
        }
        return true;
    }

    private void RSATabInit() {
        TextField rsaPublicKeyTextField = (TextField) mainScene.lookup("#RSAkeyPublicTextField");
        Button rsaBrowseBtnPublicKey  = (Button) mainScene.lookup("#RSAbrowseBtnPublicKey");
        rsaBrowseBtnPublicKey.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"keys");
            rsaPublicKeyTextField.setText(filePath);
        });
        TextField rsaPrivateKeyTextField = (TextField) mainScene.lookup("#RSAkeyPrivateTextField");
        Button rsaBrowseBtnKey  = (Button) mainScene.lookup("#RSAbrowseBtnPrivateKey");
        rsaBrowseBtnKey.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"keys");
            rsaPrivateKeyTextField.setText(filePath);
        });

        TextField rsaKeylenghtTextField = (TextField) mainScene.lookup("#RSAkeylengthTextField");

        TextField rsaInputFileTextField = (TextField) mainScene.lookup("#RSAinputFileTextField");
        Button rsaBrowseBtnInputFile  = (Button) mainScene.lookup("#RSAbrowseBtnInputFile");
        rsaBrowseBtnInputFile.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"documents/RSA");
            rsaInputFileTextField.setText(filePath);
        });

        TextField rsaOutputFileTextField = (TextField) mainScene.lookup("#RSAoutputFileTextField");
        Button rsaBrowseBtnOutputFile  = (Button) mainScene.lookup("#RSAbrowseBtnOutputFile");
        rsaBrowseBtnOutputFile.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String filePath= GUIFactory.openFileChooserGetFilePath(this.primaryStage,"documents/RSA");
            rsaOutputFileTextField.setText(filePath);
        });

        Button rsaEncryptBtn  = (Button) mainScene.lookup("#RSAencryptBtn");
        rsaEncryptBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String keyPub = rsaPublicKeyTextField.getText();
            String keyPriv = rsaPrivateKeyTextField.getText();
            String keylength = rsaKeylenghtTextField.getText();
            String input = rsaInputFileTextField.getText();
            String output = rsaOutputFileTextField.getText();

            if (validateRSA(keyPriv,keyPub,keylength,input,output)){
                try {
                    this.rsa = new RSA(keyPriv,keyPub,Integer.parseInt(keylength),output,input);
                    rsa.encrypt();
                } catch (RSAException e) {
                    GUIFactory.newPopupDialog(this.primaryStage, ("ERROR: rsa EXCEPTION\n"+e.getMessage()) );
                    e.printStackTrace();
                }
            }
        });

        Button rsaDecryptBtn  = (Button) mainScene.lookup("#RSAdecryptBtn");
        rsaDecryptBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String keyPub = rsaPublicKeyTextField.getText();
            String keyPriv = rsaPrivateKeyTextField.getText();
            String keylength = rsaKeylenghtTextField.getText();
            String input = rsaInputFileTextField.getText();
            String output = rsaOutputFileTextField.getText();

            if (validateRSA(keyPriv,keyPub,keylength,input,output)){
                try {
                    this.rsa = new RSA(keyPriv,keyPub,Integer.parseInt(keylength),output,input);
                    rsa.decrypt();
                } catch (RSAException e) {
                    GUIFactory.newPopupDialog(this.primaryStage, ("ERROR: rsa EXCEPTION\n"+e.getMessage()) );
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean validateRSA(String keyPriv, String keyPub, String keylength, String input, String output) {
        if (keyPriv.isEmpty()||keyPub.isEmpty()||keylength.isEmpty()||input.isEmpty()||output.isEmpty()){
            GUIFactory.newPopupDialog(this.primaryStage,"ERROR: ONE OF THE FIELDS IS EMPTY");
            return false;
        }

        try {
            Integer.parseInt(keylength);
        } catch (NumberFormatException e) {
            GUIFactory.newPopupDialog(this.primaryStage,"ERROR: BAD KEY VALUE");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void consoleTest(){

        try {
            AES aes = new AES("keys/AESkey.key","documents/AES/encrypted.txt","documents/AES/original.txt");
            aes.encrypt();

            aes = new AES("keys/AESkey.key","documents/AES/decrypted.txt","documents/AES/encrypted.txt");
            aes.decrypt();
        } catch (AESException e) {
            e.printStackTrace();
        }

        try {
            RSA rsa = new RSA("keys/RSAprivate.key","keys/RSApublic.key","documents/RSA/encrypted.txt","documents/RSA/original.txt");
            rsa.encrypt();

            rsa = new RSA("keys/RSAprivate.key","keys/RSApublic.key","documents/RSA/decrypted.txt","documents/RSA/encrypted.txt");
            rsa.decrypt();
        }catch (RSAException ex){
            ex.printStackTrace();
        }
    }

}
