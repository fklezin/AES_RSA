package factory;

import java.io.*;

/**
 * Created by fklezin on 5.1.2017.
 */
public class InputOutputStreamHandler {

    public static byte[] readFile(File inputFile) throws IOException {

        return getInputStreamBytes(inputFile);
    }

    public static byte[] readFile(String inputFile_path) throws IOException {

        File inputFile = new File(inputFile_path);
        return getInputStreamBytes(inputFile);
    }

    public static void writeFile(File outputFile, byte[] outputBytes) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);
        outputStream.close();
    }

    public static void writeFile(String outputFile_path, byte[] outputBytes) throws IOException {

        File outputFile = new File(outputFile_path);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);
        outputStream.close();
    }

    private static byte[] getInputStreamBytes(File inputFile) throws IOException {

        byte[] inputBytes;

        FileInputStream inputStream = new FileInputStream(inputFile);
        inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);
        inputStream.close();

        return inputBytes;
    }
}
