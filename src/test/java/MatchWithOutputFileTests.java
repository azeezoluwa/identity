import fileReader.FileReader;
import fileReader.FileReaderFactory;
import helper.PlateNumberHelper;
import helper.BrowserHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class MatchWithOutputFileTests {
    private FileReader textFileReader;
    private PlateNumberHelper findRegistrationNumberHelper;

    @Before
    public void setup() {
        textFileReader = FileReaderFactory.provideFileReader("text");
        findRegistrationNumberHelper = new PlateNumberHelper();
    }

    @Test
    public void inputFileToContainRegistrationNumbers() throws IOException {

        String inputFileContent = textFileReader.getInputFileContent();
        assertEquals(
                asList("AD58VNF", "BW57BOW", "KT17DLX", "SG18HTN"),
                findRegistrationNumberHelper.find(inputFileContent)
        );
    }

    @Test
    public void compareWebsiteOutputWithOutputFIleContentsForMatching() throws IOException {
        textFileReader = FileReaderFactory.provideFileReader("text");
        List<String> inputRegList = findRegistrationNumberHelper.find(textFileReader.getInputFileContent());
        List<String> outputLines = textFileReader.getOutputFileContent();
        inputRegList
                .forEach(
                        reg -> {
                            String actual = new BrowserHelper().getCarDetails(reg);
                            verifyInEachOutputLine(outputLines, reg, actual);
                        });
    }

    private void verifyInEachOutputLine(List<String> outputLines, String reg, String actual) {
        outputLines
                .stream()
                .filter(expectedLine -> expectedLine.split(",")[0].equals(reg))
                .forEach(expectedLine -> printStatus(reg, actual, expectedLine));
    }

    private void printStatus(String reg, String actual, String expectedLine) {
        System.out.println(reg + ": " + (expectedLine.equals(actual) ? "MATCHED" : "NOT MATCHED"));
    }
}
