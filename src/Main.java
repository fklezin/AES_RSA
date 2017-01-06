import crypto.AES;
import crypto.AESException;
import crypto.RSA;
import crypto.RSAException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("AES_RSA");
        this.mainScene = new Scene(root);

        AesTabInit();
        RSATabInit();

        primaryStage.setScene(this.mainScene);
        primaryStage.show();
    }

    private void AesTabInit() {
                
    }

    private void RSATabInit() {

    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void test(){

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
