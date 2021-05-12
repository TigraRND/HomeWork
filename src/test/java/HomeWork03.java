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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomeWork03 {
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
    public void shutDown(){
        if (driver!=null)
            driver.quit();
        log.info("Web Driver закрыт");
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

//      Попытка бороться с капчей
        String pageTitle = driver.getTitle();
        if(pageTitle.equals("Ой!")){
            By captchaCheckBox = By.xpath("//span[text()='Нажмите, чтобы продолжить']");
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

        String model1FromList = addToCompare(testsData.smartphone01());
        checkItemToCompare(model1FromList);

        String model2FromList = addToCompare(testsData.smartphone02());
        checkItemToCompare(model2FromList);

        driver.get("https://market.yandex.ru/my/compare-lists");
        log.info("Переход на страницу сравнения товаров");

        List<WebElement> items = driver.findElements(By.xpath("//a[contains(text(),'Смартфон')]"));
        softAssert.assertThat(items.size()).isEqualTo(2);
        softAssert.assertAll();
        log.info("Проверка количества товаров на сравнении: " + items.size());
    }

    private void waitForResult(){
        By spinnerLocator = By.xpath("//div[@data-tid='8bc8e36b']");
        WebElement spinner = driver.findElement(spinnerLocator);
        new WebDriverWait(driver,8).until(ExpectedConditions.invisibilityOf(spinner));
        log.info("Ожидание результата фильтрации");
    }

    private void chooseBrand(String brand){
        By checkBox = By.xpath("//input[@name='Производитель " + brand + "']/parent::label");
        driver.findElement(checkBox).click();
        log.info("Выбор производителя " + brand);
    }

    private String addToCompare(String item){
        Actions action = new Actions(driver);
        //Находим первый в списке нужный товар
        WebElement phone = driver.findElement(By.xpath("//span[contains(text(),'" + item + "')]"));
        //Запоминаем название товара без указания цвета
        String phoneName = phone.getText().split(",")[0];
        //Наводим мышь на товар
        action.moveToElement(phone).build().perform();
        //Нажимаем на кнопку сравить
        driver.findElement(By.xpath("//span[contains(text(),'" + item + "')]/ancestor::*[5]/descendant::*[5]")).click();
        log.info("Добавление к сравнению первого " + item);
        //Возвращаем название товара
        return phoneName;
    }

    private void checkItemToCompare(String modelName){
        By textToCompareLocator = By.xpath("//div[contains(text(),'добавлен к сравнению')]");
        //Берем с плашки текст фактического результата
        String actualResult = getElement(textToCompareLocator).getText();
        //Генерируем текст ожидаемого результата
        String expectedResult = "Товар " + modelName + " добавлен к сравнению";
        //Делаем Soft Assert
        softAssert.assertThat(actualResult).isEqualTo(expectedResult);
        log.info("Проверка добавленного товара на всплывашке: " + actualResult);
    }

    private WebElement getElement(By locator){
        return new WebDriverWait(driver,4)
                //Каждый раз при вызове метода отрабатывает явное ожидание в 4 секунды
                //Метод until возвращает объект Web Element
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}