import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomeWork01Test {
    private final Logger logger = LogManager.getLogger(HomeWork01Test.class);
    protected static WebDriver driver;
    TestsData cfg = ConfigFactory.create(TestsData.class);

    @BeforeClass
    public static void start(){
        Logger log = LogManager.getLogger(HomeWork01Test.class);
        log.info("******************** Новый запуск ********************");
    }

    @Before
    public void starUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Web Driver поднят");
    }

    @Test
    public void checkMainTitle(){
        gotoOTUS();
        String actual = driver.getTitle();
        Assert.assertEquals(cfg.mainTitle(), actual);
        logger.info("Проверка Title страницы");

    }

    @Test
    public void checkSpecialTitle(){
        gotoOTUS();
        String actual = driver.getTitle();
        Assert.assertTrue("Title страницы не содержит слова Скидки",actual.contains(cfg.celebratoryTitle()));
        logger.info("Проверка Title страницы на наличие слова Скидки");
    }

    @After
    public void logging (){
        if(driver != null)
            driver.quit();
        logger.info("Web Driver закрыт");
    }

    private void gotoOTUS(){
        driver.get("https://otus.ru");
        logger.info("Переход по адресу https://otus.ru");
    }
}