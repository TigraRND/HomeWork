package сases;

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
import utils.TestsData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeWork02Test {
    protected static WebDriver driver;
    private final Logger log = LogManager.getLogger(HomeWork02Test.class);
    TestsData testsData = ConfigFactory.create(TestsData.class);

    @Before
    public void startUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Настрока не явных ожиданий с таймаутом 3 секунды
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        log.info("Web Driver поднят");
    }

    @After
    public void shutDown(){
        if (driver!=null)
            driver.quit();
    }

    @Test
    public void checkContactAddress(){
        gotoURL(testsData.otusURL());

        By contactsLink = By.cssSelector("a[href=\"/contacts/\"].header2_subheader-link");
        getElement(contactsLink).click();
        log.info("Переход на страницу Контакты");

        By address = By.xpath("//div[text()='Адрес']/following-sibling::div");
        String actualAddress = getElement(address).getText();
        Assert.assertEquals(testsData.expectedAddress(),actualAddress);
        log.info("Проверка адреса на странице Контакты");
    }

    @Test
    public void checkPageTitle(){
        gotoURL(testsData.otusURL());

        By contactsLink = By.cssSelector("a[href=\"/contacts/\"].header2_subheader-link");
        getElement(contactsLink).click();
        log.info("Переход на страницу Контакты");

        driver.manage().window().maximize();
        log.info("Браузер развернут на полный экран");

        Assert.assertEquals(testsData.contactsTitle(), driver.getTitle());
        log.info("Проверка Title страницы");
    }

    @Test
    public void beelineTest(){
        gotoURL("https://msk.tele2.ru/shop/number");

        By searchNumber = By.cssSelector("input#searchNumber");
        getElement(searchNumber).clear();
        getElement(searchNumber).sendKeys("97");
        log.info("Поиск номеров по цифрам 97");

        List<WebElement> elements = driver.findElements(By.cssSelector("span.phone-number"));
        Assert.assertEquals(20,elements.size(),4.0);
        log.info("Проверка количества вернувшихся элементов");
    }

    @Test
    public void checkFAQ(){
        gotoURL(testsData.otusURL());

        By faqLink = By.cssSelector("a[href=\"/faq/\"].header2_subheader-link");
        getElement(faqLink).click();
        log.info("Переход на страницу FAQ");

        By questionLink = By.xpath("//div[text()='Где посмотреть программу интересующего курса?']");
        getElement(questionLink).click();
        log.info("Нажатие на ссылку с вопросом");

        By answerText = By.xpath("//div[text()='Где посмотреть программу интересующего курса?']/following-sibling::div");
        String actual = getElement(answerText).getText();
        Assert.assertEquals(testsData.answerText(), actual);
        log.info("Проверка текста ответа");
    }

    @Test
    public void checkSubscribe(){
        gotoURL(testsData.otusURL());

        By emailInput = By.xpath("//input[@class='input footer2__subscribe-input']");
        getElement(emailInput).sendKeys(testsData.testEmail());
        log.info("Ввод тестового email в поле");

        By submitButton = By.xpath("//button[@class='footer2__subscribe-button button button_blue button_as-input']");
        getElement(submitButton).click();
        log.info("Нажатие на кнопку Подписаться");

        By subscribeConfirm = By.xpath("//p[@class='subscribe-modal__success']");
        String actual = getElement(subscribeConfirm).getText();
        Assert.assertEquals(testsData.subscribeConfirm(), actual);
        log.info("Проверка текста об успешной подписке");
    }

    private WebElement getElement(By locator){
        return new WebDriverWait(driver,3)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void gotoURL(String URL){
        driver.get(URL);
        log.info("Переход по адресу " + URL);
    }
}