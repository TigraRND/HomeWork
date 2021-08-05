package pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class CommonElements {
    protected static WebDriver driver;
    final Logger logger = LogManager.getLogger(CommonElements.class);
    private boolean auth = false;
    private final By enterAndRegBtn = By.cssSelector("button.header2__auth");
    private final By loginInput = By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)");
    private final By passwordInput = By.cssSelector(".js-psw-input");
    private final By submitBtn = By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)");
    private final By avatar = By.cssSelector(".ic-blog-default-avatar");

    public CommonElements(WebDriver driver){
        CommonElements.driver = driver;
    }

    @Step("Авторизация пользователя")
    public CommonElements authorization(String login, String password){
        //Проаерка что авторизация еще не пройдена
        if (!auth){
            driver.findElement(enterAndRegBtn).click();
            logger.info("Нажатие кнопки Вход и регистрация");

            driver.findElement(loginInput).sendKeys(login);
            logger.info("Ввод логина");

            driver.findElement(passwordInput).sendKeys(password);
            logger.info("Ввод пароля");

            driver.findElement(submitBtn).submit();
            logger.info("Нажатие кнопки Войти");
            auth = true;
        }else{
            logger.warn("Авторизация уже пройдена");
        }
        return this;
    }

    public PersonalDataPage enterLK(){
        if(!auth){
            logger.error("Авторизация не продена,личный кабинет не доступен");
            return null;
        }
        WebElement avatarBtn = driver.findElement(avatar);
        Actions actions = new Actions(driver);
        actions.moveToElement(avatarBtn).build().perform();
        logger.info("Нажимаем на аватарку"); //Нужна небольшая задержка, можно было использовать Thread.Sleep();
        PersonalDataPage personalDataPage = new PersonalDataPage(driver);
        return personalDataPage.goToPage();
    }
}