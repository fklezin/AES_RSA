package crypto; /**
 * Created by fklezin on 5.1.2017.
 */
import factory.InputOutputStreamHandler;

import java.io.*;
import java.security.*;

import javax.crypto.*;

public class RSA {

    //INPUT VALUES
    private String private_key_path;
    private String public_key_path;
    private String input_file_path;
    private String output_file_path;
    private int keylength_in_bits=2048;

    //GENERATE VALUES
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSA(String private_key_path, String public_key_path, int keylength_in_bits, String output_file_path, String input_file_path){
        this.private_key_path = private_key_path;
        this.public_key_path = public_key_path;
        this.keylength_in_bits = keylength_in_bits;
        this.input_file_path = input_file_path;
        this.output_file_path = output_file_path;
    }

    public RSA(String private_key_path, String public_key_path, String output_file_path, String input_file_path) {
        this.private_key_path = private_key_path;
        this.public_key_path = public_key_path;
        this.input_file_path = input_file_path;
        this.output_file_path = output_file_path;
    }

    public void generateKeys() throws RSAException {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(this.keylength_in_bits);
            KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File(this.private_key_path);
            File publicKeyFile = new File(this.public_key_path);

            // Create files to store public and private key
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();

            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            // Saving the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();

            this.privateKey = key.getPrivate();
            this.publicKey = key.getPublic();
        } catch (NoSuchAlgorithmException | IOException ex) {
            throw new RSAException("RSA: Error encrypting/decrypting file", ex);
        }

    }

    public boolean keysExists() {

        File privateKey = new File(this.private_key_path);
        File publicKey = new File(this.public_key_path);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    //GET KEYS
    private void getKeys() {
        ObjectInputStream inputStream;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(this.public_key_path));
            this.publicKey = (PublicKey) inputStream.readObject();

            inputStream = new ObjectInputStream(new FileInputStream(this.private_key_path));
            this.privateKey = (PrivateKey) inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void doCrypto(int cipherMode) throws RSAException {
        if (keysExists())
            getKeys();
        else
            generateKeys();

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            if (cipherMode==Cipher.DECRYPT_MODE){
                cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            }
            else if (cipherMode==Cipher.ENCRYPT_MODE){
                cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            }

            byte[] inputBytes = InputOutputStreamHandler.readFile(this.input_file_path);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            InputOutputStreamHandler.writeFile(this.output_file_path,outputBytes);

            System.out.println("RSA successful");

        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | IOException ex) {
            throw new RSAException("RSA: Error encrypting/decrypting file", ex);
        }
    }

    public void encrypt() throws RSAException {
        doCrypto(Cipher.ENCRYPT_MODE);
    }

    public void decrypt() throws RSAException {
        doCrypto(Cipher.DECRYPT_MODE);
    }

}
