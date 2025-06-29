package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SearchStep {

    WebDriver driver;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Given("booking search page is opened")
    public void bookingSearchPageIsOpened() throws InterruptedException {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
        Thread.sleep(5000);
    }

    @When("user searches for {string}")
    public void userSearchesFor(String hotel) throws InterruptedException {
        driver.findElement(By.name("ss")).click();
        Thread.sleep(5000);
        driver.findElement(By.name("ss")).sendKeys(hotel);
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(5000);
    }

    @Then("{string} hotel is shown")
    public void hotelIsShown(String expectedResult) {
        List <WebElement> titles = driver.findElements(By.cssSelector("[data-testid=title]"));
        boolean isHotelFound = false;
        for (WebElement title : titles) {
            if (title.getText().equals(expectedResult)) {
                isHotelFound = true;
                break;
            }
        }
        Assert.assertTrue(isHotelFound);
    }

    @And("hotel name {string} from rating {string}")
    public void hotelNameFromRating(String hotelName, String hotelRating) {
        String hotelScore = driver.findElement(By.xpath(String.format("//div[text()='%s']" +
                "/ancestor::div[@data-testid='property-card-container']" +
                "/descendant::a[@data-testid='review-score-link']", hotelName))).getText();
        assertEquals(hotelScore, hotelRating);
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
