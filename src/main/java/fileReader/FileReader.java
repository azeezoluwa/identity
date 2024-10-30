package fileReader;

import java.io.IOException;
import java.util.List;

public interface FileReader {

    String DEFAULT_INPUT_FILE_PATH = "car_input.txt";
    String INPUT_PATH = System.getProperty("input_file_path", DEFAULT_INPUT_FILE_PATH);

    String DEFAULT_OUTPUT_FILE_PATH = "car_output.txt";
    String OUTPUT_PATH = System.getProperty("output_file_path", DEFAULT_OUTPUT_FILE_PATH);

    String getInputFileContent() throws IOException;

    List<String> getOutputFileContent() throws IOException;
}
