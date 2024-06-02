package a2.ppl.tugas.akhir.web.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import a2.ppl.tugas.akhir.web.utils.ConfigReader;
import a2.ppl.tugas.akhir.web.utils.SeleniumHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DashboardSteps {
    WebDriver driver;
    SeleniumHelper seleniumHelper;
    ConfigReader configReader;

    public DashboardSteps() {
        configReader = new ConfigReader();
    }

    @Given("Sudah login")
    public void login() {
        String url = "https://www.saucedemo.com";
        System.setProperty("webdriver.chrome.driver", configReader.getProperty("webdriver.chrome.driver"));
        driver = new ChromeDriver();
        seleniumHelper = new SeleniumHelper(driver);
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        assertEquals(seleniumHelper.isElementDisplayedById("login-button"), true);
        String username = "standard_user";
        String password = "secret_sauce";
        seleniumHelper.setInputByName("user-name", username);
        seleniumHelper.setInputByName("password", password);
        seleniumHelper.clickButtonById("login-button");
    }

    @Given("berada pada halaman dashboard")
    public void checkCurrentPage() {
        String currentUrl = driver.getCurrentUrl();
        String expected = "https://www.saucedemo.com/inventory.html";
        assertEquals(expected, currentUrl);
    }

    @When("menekan tombol {string} pada card barang {string}")
    public void addItem(String buttonText, String productName) {
        // WebElement product = driver.findElement(By.xpath("//div[contains(text(), '" +
        // productName + "')]"));
        // WebElement button = product.findElement(By.id(productName))
        seleniumHelper.clickButtonByName(productName);
    }

    @Then("halaman dashboard menampilkan katalog barang")
    public void checkProductCatalog() {
        String value = seleniumHelper.getElementByClassName("title").getText();
        String expected = "Products";
        assertEquals(expected, value);
    }

    @Then("tombol {string} dari barang {string} berubah menjadi {string}")
    public void checkButtonState(String pastButtonName, String productName, String currentButtonName) {
        productName = productName.toLowerCase();
        String buttonName = (currentButtonName + productName).replace(' ', '-');
        assertEquals(seleniumHelper.isElementDisplayedByClassName(buttonName), true);
    }

    @Then("jumlah barang pada icon cart menjadi {int}")
    void checkItemInCart(Integer totalItems) {
        WebElement cart = seleniumHelper.getElementByClassName("shopping_cart_badge");
        assertEquals(cart.getText(), totalItems);
    }

}
