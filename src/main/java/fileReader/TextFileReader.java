package fileReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextFileReader implements FileReader {
    @Override
    public String getInputFileContent() throws IOException {
        return Files.readString(Paths.get(INPUT_PATH));
    }

    @Override
    public List<String> getOutputFileContent() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(OUTPUT_PATH), StandardCharsets.US_ASCII);
        lines.remove(0);
        return lines;
    }
}
