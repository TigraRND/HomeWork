import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TitleTest {

    private Logger logger = LogManager.getLogger(TitleTest.class);
    protected static WebDriver driver;
    TestsData testdata = ConfigFactory.create(TestsData.class);

    @Before
    public void start(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Web Driver создан");
    }

    @After
    public void ending(){
        if(driver != null)
            driver.quit();
        logger.info("Web Driver закрыт");
    }

    public void checkTitle(String expected){
        driver.get("https://otus.ru");
        logger.info("Переход по адресу https://otus.ru");
        String actual = driver.getTitle();
        Assert.assertTrue(actual.contains(expected));
        logger.info("Тест завершен");
    }

    @Test
    public void checkMainTitle(){
        checkTitle(testdata.mainTitle());
    }

    @Test
    public void checkSpecialTitle(){
        checkTitle(testdata.celebratoryTitle());
    }

}
