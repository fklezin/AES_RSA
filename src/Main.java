import crypto.AES;
import crypto.RSA;

public class Main {

    public static void main(String[] args) {
       new RSA("keys/private.key","keys/public.key",2048).testRSA();
        AES.testAES();
    }
}
