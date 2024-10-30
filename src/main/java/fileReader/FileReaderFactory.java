package fileReader;

public class FileReaderFactory {
    public static FileReader provideFileReader(String fileType) {
        // In the future if we got to support for example json file, we can have a switch here
        return new TextFileReader();
    }
}
