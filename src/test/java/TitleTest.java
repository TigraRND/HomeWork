import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TitleTest {

    private Logger logger = LogManager.getLogger(TitleTest.class);
    protected static WebDriver driver;

    @Before
    public void Start(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Web Driver создан");
    }

    @After
    public void Ending(){
        if(driver != null)
            driver.quit();
        logger.info("Web Driver закрыт");
    }

    @Test
    public void CheckTitle(){
        driver.get("https://otus.ru");
        logger.info("Переход по адресу https://otus.ru");
        logger.info("Старт теста");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Онлайн‑курсы для профессионалов"));
//        Assert.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям",title);
        logger.info("Тест завершен");
    }

}
