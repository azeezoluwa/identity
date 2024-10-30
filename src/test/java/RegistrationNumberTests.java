import helper.PlateNumberHelper;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class RegistrationNumberTests {
    private PlateNumberHelper findRegistrationNumberHelper;

    @Before
    public void setUp() {
        findRegistrationNumberHelper = new PlateNumberHelper();
    }

    @Test
    public void noMatchingNumberFound() {
        String text = "This text doesn't contain any car registration numbers";
        assertEquals(Collections.emptyList(), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void findARegWithNoWhiteSpacesAfterTwoDigits() {
        String text = "Checking example BMW with registration AD58VNF the value of the car is roughly around £3000.";
        assertEquals(asList("AD58VNF"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void findARegWithWhiteSpacesBetweenAfterTwoDigits() {
        String text = "However car with registration BW57 BOW is not worth much in current market.";
        assertEquals(asList("BW57BOW"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void stripWhiteSpacesFromReg() {
        String text = "However car with registration BW57 BOW is not worth much in current market.";
        assertEquals(asList("BW57BOW"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void textWithMultipleRegistrationNumbers() {
        String text = "There are multiple cars available higher than £10k with registraions KT17DLX and SG18 HTN.";
        assertEquals(asList("KT17DLX", "SG18HTN"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void textWithMultipleRegistrationNumbersFromMultipleLines() {
        String text = "However car with registration BW57 BOW is not worth much in current market. \n" +
                "There are multiple cars available higher than £10k with registraions KT17DLX and SG18 HTN.";
        assertEquals(asList("BW57BOW", "KT17DLX", "SG18HTN"), findRegistrationNumberHelper.find(text));
    }
}
