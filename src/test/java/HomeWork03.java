import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
    public void yandexTest(){
        driver.manage().window().maximize();
        log.info("Разворачиваем окно на весь экран");

        driver.get("https://market.yandex.ru");
        log.info("Переход по адресу https://market.yandex.ru");

        By elctronicLink = By.xpath("//span[text()='Электроника']/parent::a[contains(@href,'/catalog--elektronika/')]");
        driver.findElement(elctronicLink).click();
        log.info("Переход в раздел Электроника");
    }
}
