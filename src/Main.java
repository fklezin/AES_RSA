import crypto.AES;
import crypto.AESException;
import crypto.RSA;
import crypto.RSAException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {


        try {
            AES aes = new AES("keys/AESkey.key","documents/AES/encrypted.txt","documents/AES/original.txt");
            aes.encrypt();

            aes = new AES("keys/AESkey.key","documents/AES/decrypted.txt","documents/AES/encrypted.txt");
            aes.decrypt();
        } catch (AESException e) {
            e.printStackTrace();
        }



       /* try {
            new RSA("keys/private.key","keys/public.key",2048,"file.txt","document.txt").runRSA();
        } catch (RSAException e) {
            e.printStackTrace();
        }*/
    }
}
