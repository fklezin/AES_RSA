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
