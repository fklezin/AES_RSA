package crypto; /**
 * Created by fklezin on 4.1.2017.
 */

import factory.InputOutputStreamHandler;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;


public class AES {

    //INPUT VALUES
    private int keylength_in_bytes=128; //default value
    private String key_path;
    private String inputFile_path;
    private String outputFile_path;

    //GENERATE VALUES
    private SecretKey key;

    public AES(String key_path, String outputFile_path, String inputFile_path) {
        this.key_path = key_path;
        this.outputFile_path = outputFile_path;
        this.inputFile_path = inputFile_path;

    }

    public AES(int keylength_in_bytes, String key_path, String inputFile_path, String outputFile_path) {
        this.keylength_in_bytes = keylength_in_bytes;
        this.key_path = key_path;
        this.inputFile_path = inputFile_path;
        this.outputFile_path = outputFile_path;
    }

    //CRYPTO MAAAN
    private void doCrypto(int cipherMode) throws AESException {

        try {
            if (cipherMode==Cipher.ENCRYPT_MODE)
                generateKey();
            else if (cipherMode==Cipher.DECRYPT_MODE)
                getKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, this.key);

            byte[] inputBytes = InputOutputStreamHandler.readFile(this.inputFile_path);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            InputOutputStreamHandler.writeFile(this.outputFile_path,outputBytes);

            //Console log
            System.out.println("AES successful");

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new AESException("Error encrypting/decrypting file", ex);
        }
    }

    //GET KEY
    private void getKey() {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.key_path));
            this.key = (SecretKey) inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //GENERATE KEY
    private void generateKey(){

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(this.keylength_in_bytes);
            this.key = keyGen.generateKey();

            File keyFile = new File(this.key_path);
            if (keyFile.getParentFile() != null) {
                keyFile.getParentFile().mkdirs();
            }
            keyFile.createNewFile();


            ObjectOutputStream keyOS = new ObjectOutputStream(
                    new FileOutputStream(keyFile));
            keyOS.writeObject(this.key);
            keyOS.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encrypt() throws AESException {
        doCrypto(Cipher.ENCRYPT_MODE);
    }

    public void decrypt() throws AESException {
        doCrypto(Cipher.DECRYPT_MODE);
    }
}