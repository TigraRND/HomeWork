import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TitleTest {

    private Logger logger = LogManager.getLogger(TitleTest.class);
    protected static WebDriver driver;
    TestsData testdata = ConfigFactory.create(TestsData.class);

    @BeforeClass
    public static void start(){
        Logger log = LogManager.getLogger(TitleTest.class);
        log.info("******************** Новый запуск ********************");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        log.info("Web Driver создан");
        driver.get("https://otus.ru");
        log.info("Переход по адресу https://otus.ru");
    }

    @AfterClass
    public static void ending(){
        if(driver != null)
            driver.quit();
        Logger log = LogManager.getLogger(TitleTest.class);
        log.info("Все тесты завершены");
        log.info("Web Driver закрыт");
    }

    @After
    public void logging (){
        logger.info("Тест завершен");
    }

    @Test
    public void checkMainTitle(){
        String actual = driver.getTitle();
        Assert.assertEquals(testdata.mainTitle(), actual);
    }

    @Test
    public void checkSpecialTitle(){
        String actual = driver.getTitle();
        Assert.assertTrue(actual.contains(testdata.celebratoryTitle()));
    }

}
