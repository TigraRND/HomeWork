import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeWork03 {
    protected static WebDriver driver;
    private Logger log = LogManager.getLogger(HomeWork03.class);
    TestsData testsData = ConfigFactory.create(TestsData.class);

    @Before
    public void startUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        log.info("Web Driver поднят");
//        Настрока не явных ожиданий с таймаутом 3 секунды
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

//    @After
    public void shutDown(){
        if (driver!=null)
            driver.quit();
    }

    @Test
    public void yandexTest() {
        driver.manage().window().maximize();
        log.info("Разворачиваем окно на весь экран");

        driver.get("https://market.yandex.ru");
        log.info("Переход по адресу https://market.yandex.ru");

        By elctronicLink = By.xpath("//span[text()='Электроника']/parent::a[contains(@href,'/catalog--elektronika/')]");
        driver.findElement(elctronicLink).click();
        log.info("Переход в раздел Электроника");

//      TODO для обхода капчи добавить ожидание на загрузку страницы

        String pageTitle = driver.getTitle();
        if(pageTitle == "Ой!"){
            By captchaCheckBox = By.xpath("//span[text()='Я не робот']");
            driver.findElement(captchaCheckBox).click();
            log.info("Проходим капчу");
        }

        By smartphonesLink = By.xpath("//a[text()='Смартфоны']");
        driver.findElement(smartphonesLink).click();
        log.info("Переход в категорию Смартфоны");

        chooseBrand(testsData.smartphone01());
        waitForResult();

        chooseBrand(testsData.smartphone02());
        waitForResult();

        By dPrice = By.xpath("//button[@data-autotest-id='dprice']");
        driver.findElement(dPrice).click();
        log.info("Сортировка по цене от меньшей к большей");
        waitForResult();

        String listNameModel = addToCompare(testsData.smartphone01());
        log.debug(listNameModel);

//      TODO проверить что текст во всплывашке содержится
//       в тексте из списка
//        Assert.assertTrue();
    }

    public void waitForResult(){
        By spinnerLocator = By.xpath("//div[@data-tid='8bc8e36b']");
        WebElement spinner = driver.findElement(spinnerLocator);
        new WebDriverWait(driver,8).until(ExpectedConditions.invisibilityOf(spinner));
        log.info("Ожидание результата фильтрации");
    }

    public void chooseBrand(String brand){
        By checkBox = By.xpath("//input[@name='Производитель " + brand + "']/parent::label");
        driver.findElement(checkBox).click();
        log.info("Выбор производителя " + brand);
    }

    public String addToCompare(String item){
        Actions action = new Actions(driver);
        WebElement phone = driver.findElement(By.xpath("//span[contains(text(),'" + item + "')]"));
        String phoneName = phone.getText();
        action.moveToElement(phone).build().perform();
        driver.findElement(By.xpath("//span[contains(text(),'" + item + "')]/ancestor::*[5]/descendant::*[5]")).click();
        log.info("Добавление к сравнению первого " + item);
        return phoneName;
    }
}