package helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BrowserHelper {

    private WebDriver driver;
    private String resultFormat = "%s,%s,%s,%s,%s";
    public static final String URL = "https://www.webuyanycar.com/";
    public static final String ACCEPT_COOKIES = "onetrust-accept-btn-handler";
    public static final String REG_INPUT = "vehicleReg";
    public static final String RANDOM_MILEAGE = "Mileage";
    public static final String GET_CAR_VALUATION = "btn-go";
    public static final String IDENTIFIER = "//dt[text()='%s']//following-sibling::dd";

    public String getCarDetails(String reg) {
        openBrowser();
        search(reg);
        String result = String.format(
                resultFormat,
                read("Manufacturer"),
                read("Model"),
                read("Model"),
                read("Year")
        );
        driver.quit();
        return result;
    }

    public String randomCarMileage() {
        int minMileage = 0;       // Minimum mileage for the car
        int maxMileage = 300000;  // Maximum mileage for the car
        // Create an instance of Random
        Random random = new Random();
        int mileage = random.nextInt(maxMileage - minMileage + 1) + minMileage;
        return Integer.toString(mileage);
    }

    private void search(String reg) {
        driver.findElement(By.id(ACCEPT_COOKIES)).click();
        driver.findElement(By.id(REG_INPUT)).sendKeys(reg);
        driver.findElement(By.id(RANDOM_MILEAGE)).sendKeys(randomCarMileage());
        driver.findElement(By.id(GET_CAR_VALUATION)).click();
        waitUntilElementToHave(getElement("Manufacturer"), reg);
    }

    private void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(BrowserHelper.URL);
    }

    private String read(String label) {
        return getElement(label).getText();
    }

    private void waitUntilElementToHave(WebElement element, String reg) {
        FluentWait wait = new FluentWait(driver);
        wait.withTimeout(5000, TimeUnit.MILLISECONDS);
        wait.pollingEvery(250, TimeUnit.MILLISECONDS);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, reg));
        } catch (TimeoutException ignored) {
            throw new RuntimeException(reg + ": Vehicle Not Found");
        }
    }

    private WebElement getElement(String label) {
        return driver.findElement(By.xpath(String.format(IDENTIFIER, label)));
    }

    /*
    This method should loop through car details
     */
    public void getVehicleDetails (){
        List<WebElement> vehicleDetails = driver.findElements(By.className("vehicle-details"));
        // Loop through each carousel item
        for (WebElement detail : vehicleDetails) {
            // Find elements for specification names and values
            List<WebElement> labels = detail.findElements(By.className("spec-label"));
            List<WebElement> values = detail.findElements(By.className("spec-value"));

            for (int i = 0; i < labels.size(); i++) {
                System.out.println(labels.get(i).getText() + ": " + values.get(i).getText());
            }
        }
    }
}
