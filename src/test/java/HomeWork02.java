import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeWork02 {
    protected static WebDriver driver;
    private Logger log = LogManager.getLogger(HomeWork02.class);
    TestsData testsData = ConfigFactory.create(TestsData.class);

    @Before
    public void StartUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        log.info("Web Driver поднят");
    }

    @After
    public void ShutDown(){
        if (driver!=null)
            driver.quit();
    }

    @Test
    public void Test01(){
        driver.get("https://otus.ru");
        log.info("Переход по адресу https://otus.ru");

        By contacts = By.cssSelector("a[href=\"/contacts/\"].header2_subheader-link");
        getElement(contacts).click();
        log.info("Переход на страницу Контакты");

        log.info("Проверка адреса на странице Контакты");
        By address = By.xpath("//div[text()='Адрес']/following-sibling::div");
        String addressText = getElement(address).getText();
        Assert.assertEquals(testsData.contactsAddress(),addressText);
    }

    @Test
    public void Test02(){
        driver.get("https://otus.ru");
        log.info("Переход по адресу https://otus.ru");

        By contacts = By.cssSelector("a[href=\"/contacts/\"].header2_subheader-link");
        getElement(contacts).click();
        log.info("Переход на страницу Контакты");

        driver.manage().window().maximize();
        log.info("Браузер развернут на полный экран");

        log.info("Проверка Title страницы");
        Assert.assertEquals(testsData.contactsTitle(), driver.getTitle());
    }

    @Test
    public void Test03(){
        driver.get("https://msk.tele2.ru/shop/number");
        log.info("Переход по адресу https://msk.tele2.ru/shop/number");

        By searchNumber = By.cssSelector("input#searchNumber");
        getElement(searchNumber).clear();
        getElement(searchNumber).sendKeys("97");
        log.info("Поиск номеров по цифрам 97");
        //TODO написать явное ожидание на загрузку номеров
        // запросить номера телефонов через findElements по селектору
        // div.phone-number-block
        // проверить что вернулось 20 штук номеров

    }

    private WebElement getElement(By locator){
        return new WebDriverWait(driver,3)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
