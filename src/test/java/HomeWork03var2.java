import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomeWork03var2 {
    protected static WebDriver driver;
    private final Logger log = LogManager.getLogger(HomeWork03.class);
    TestsData testsData = ConfigFactory.create(TestsData.class);
    SoftAssertions softAssert = new SoftAssertions();

    @Before
    public void startUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        log.info("Web Driver поднят");
    }

    @After
    public void shutDown() {
        if (driver != null)
            driver.quit();
        log.info("Web Driver закрыт");
    }

    @Test
    public void voltTest(){
        driver.manage().window().maximize();
        log.info("Разворачиваем окно на весь экран");

        driver.get("https://www.220-volt.ru/catalog/perforatory/");
        log.info("Переход на сайт https://www.220-volt.ru, раздел перфораторов");

        chooseBand(testsData.perforator01());
        chooseBand(testsData.perforator02());

        By filterSubmit = By.id("filterSubm");
        driver.findElement(filterSubmit).click();
        log.info("Отфильтровать товары по бренду");

        driver.findElement(By.cssSelector("span.select2-selection__arrow")).click();
        driver.findElement(By.cssSelector("ul.select2-results__options li")).click();
        log.info("Отфильтровать товары по цене от меньшей к большей");

        String makitaModel = getElement(By.xpath("//a[starts-with(text(),'Перфоратор " + testsData.perforator01() + "')]")).getText();
        log.info("Сохраняем название выбранной модели: " + makitaModel);
        getElement(By.xpath("//a[starts-with(text(),'Перфоратор " + testsData.perforator01() + "')]/../preceding-sibling::div[@class='new-item-list-compare custom-ui-compare compare']")).click();
        log.info("Добавление к сравнению");

        driver.findElement(By.xpath("//a[contains(text(),'Продолжить просмотр')]")).click();
        log.info("Нажатие на кнопку Продолжить просмотр");

        String zubrModel = getElement(By.xpath("//a[starts-with(text(),'Перфоратор " + testsData.perforator02() + "')]")).getText();
        log.info("Сохраняем название выбранной модели: " + zubrModel);
        getElement(By.xpath("//a[starts-with(text(),'Перфоратор " + testsData.perforator02() + "')]/../preceding-sibling::div[@class='new-item-list-compare custom-ui-compare compare']")).click();
        log.info("Добавление к сравнению");

        driver.get("https://www.220-volt.ru/compare/");
        log.info("Переход к сравнению");

        List <WebElement> actual = driver.findElements(By.cssSelector("div.title a"));
        softAssert.assertThat(actual.size()).isEqualTo(2);
        softAssert.assertThat("Перфоратор " + actual.get(0).getText()).isEqualTo(makitaModel);
        softAssert.assertThat("Перфоратор " + actual.get(1).getText()).isEqualTo(zubrModel);
        log.info("Проверка продуктов в сравнении");
        log.info("Количество товаров: " + actual.size());
        log.info("Модель 1: " + actual.get(0).getText());
        log.info("Модель 2: " + actual.get(1).getText());
        softAssert.assertAll();
    }

    private void chooseBand(String brand){
        By brandLocator = By.xpath("//label[text()='" + brand + "']");
        getElement(brandLocator).click();
        log.info("Выбор фильтра " + brand);
    }

    private WebElement getElement(By locator){
        return new WebDriverWait(driver,4)
                //Каждый раз при вызове метода отрабатывает явное ожидание в 4 секунды
                //Метод until возвращает объект Web Element
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}