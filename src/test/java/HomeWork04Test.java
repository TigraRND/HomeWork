import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class HomeWork04Test {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(HomeWork04Test.class);
    TestsData cfg = ConfigFactory.create(TestsData.class);
    SoftAssertions softAssert = new SoftAssertions();

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless");
        driver = new ChromeDriver(options);
        logger.info("Web Driver поднят");
        driver.manage().window().maximize();
//        Настрока не явного ожидания с таймаутом 3 секунды
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("Web Driver закрыт");
    }

    @Test
    public void sampleTest() {
        //Объявление объектов страниц
        BasePage basePage = new BasePage(driver);
        PersonalDataPage personalDataPage;

        //Начало теста
        basePage
                .goToPage()
                .authorization(cfg.otusTestLogin(), cfg.otusTestPassword());
//                .authorization("Tigra","12345");
        personalDataPage = basePage.enterLK();

        //Заполнение полей
        logger.info("Начало ввода тестовых данных");
        personalDataPage
                .setFirstNameRus(cfg.firstNameRus())
                .setFirstNameEng(cfg.firstNameEng())
                .setLastNameRus(cfg.lastNameRus())
                .setLastNameEng(cfg.lastNameEng())
                .setBlogName(cfg.blogName())
                .setBirthday(cfg.birthday())
                .setCountry(cfg.country())
                .setCity(cfg.city())
                .setEnglishSkill(cfg.englishSkill())
                .saveForm(false);
        logger.info("Окончание ввода тестовых данных");

        //Перезапуск драйвера
        shutDown();
        startUp();
        basePage = new BasePage(driver);
        basePage
                .goToPage()
                .authorization(cfg.otusTestLogin(), cfg.otusTestPassword());
        personalDataPage = basePage.enterLK();

        //Проверка
        logger.info("Начало проверки значений");
        softAssert.assertThat(personalDataPage.getFirstNameRus()).isEqualTo(cfg.firstNameRus());
        softAssert.assertThat(personalDataPage.getFirstNameEng()).isEqualTo(cfg.firstNameEng());
        softAssert.assertThat(personalDataPage.getLastNameRus()).isEqualTo(cfg.lastNameRus());
        softAssert.assertThat(personalDataPage.getLastNameEng()).isEqualTo(cfg.lastNameEng());
        softAssert.assertThat(personalDataPage.getBlogName()).isEqualTo(cfg.blogName());
        softAssert.assertThat(personalDataPage.getBirthday()).isEqualTo(cfg.birthday());
        softAssert.assertThat(personalDataPage.getCountry()).isEqualTo(cfg.country());
        softAssert.assertThat(personalDataPage.getCity()).isEqualTo(cfg.city());
        softAssert.assertThat(personalDataPage.getEnglishSkill()).isEqualTo(cfg.englishSkill());
        logger.info("Окончание проверки значений");
        softAssert.assertAll();

//        driver.findElement(By.xpath("//span[text()='Способ связи']")).click();
    }
}