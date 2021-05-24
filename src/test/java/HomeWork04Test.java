import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.PersonalDataPage;

import java.util.concurrent.TimeUnit;

public class HomeWork04Test {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(HomeWork04Test.class);
    private final TestsData cfg = ConfigFactory.create(TestsData.class);
    private final Argumentator arg = new Argumentator();
    private final SoftAssertions softAssert = new SoftAssertions();

    @Before
    public void startUp() {
        driver = WDFactory.getDriver(arg.getBrowser());
//        Настрока не явного ожидания с таймаутом 3 секунды
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("WebDriver закрыт");
    }

    @Test
    public void fillAndCheck() {
        //Создание объекта тестируемой страницы
        PersonalDataPage personalDataPage = new PersonalDataPage(driver);

        //Переход на сайт, авторизация, переход в личный кабинет
        personalDataPage.goToSite()
                .authorization(arg.getLogin(), arg.getPassword())
//                .authorization("Tigra","12345")
                .enterLK();
//        logger.debug("Проверка повторной авторизации");

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
                .cleanContacts()
                .addContact("VK", cfg.vk())
                .addContact("Facebook",cfg.Facebook())
                .saveForm(false);
        logger.info("Окончание ввода тестовых данных");

        //Перезапуск драйвера
        shutDown();
        startUp();

        //Создание нового объекта тестируемой страницы
        personalDataPage = new PersonalDataPage(driver);

        //Переход на сайт, авторизация, переход в личный кабинет
        personalDataPage.goToSite()
                .authorization(arg.getLogin(), arg.getPassword())
                .enterLK();

        //Проверка данных
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
        softAssert.assertThat(personalDataPage.getContact("VK")).isEqualTo(cfg.vk());
        softAssert.assertThat(personalDataPage.getContact("Facebook")).isEqualTo(cfg.Facebook());
        logger.info("Окончание проверки значений");
        softAssert.assertAll();
    }
}