package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PersonalDataPage extends CommonElements {
    private final By firstNameRus = By.id("id_fname");
    private final By lastNameRus = By.id("id_lname");
    private final By firstNameEng = By.id("id_fname_latin");
    private final By lastNameEng = By.id("id_lname_latin");
    private final By blogName = By.id("id_blog_name");
    private final By birthday = By.cssSelector(".input-icon > input:nth-child(1)");
    private final By countryListBox = By.cssSelector(".js-lk-cv-dependent-master div");
    private final By cityListBox = By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)");
    private final By englishSkill = By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)");
    private final By emptyTypeOfContact = By.xpath("//span[text()='Способ связи']");
    private final By addButton = By.xpath("//button[text()='Добавить']");
    private final By deleteButtons = By.xpath("(//button[text()='Удалить'])[position() mod 2 = 0]");
    private final By saveAndContinue = By.xpath("//button[@title='Сохранить и продолжить']");
    private final By saveAndStay = By.xpath("//button[@title='Сохранить и заполнить позже']");

    public PersonalDataPage(WebDriver driver){
        super(driver);
    }

    @Step("Переход на страницу персональных данных пользователя")
    public PersonalDataPage goToPage(){
        driver.get("https://otus.ru/lk/biography/personal/");
        logger.info("Переход в личный кабинет, раздел 'О себе'");
        return this;
    }

    @Step("Ввод имени пользователя")
    public PersonalDataPage setFirstNameRus(String name){
        fillField(firstNameRus,name);
        return this;
    }

    @Step("Ввод имени пользователя латиницей")
    public PersonalDataPage setFirstNameEng(String name){
        fillField(firstNameEng,name);
        return this;
    }

    @Step("Ввод фамилии пользователя")
    public PersonalDataPage setLastNameRus(String name){
        fillField(lastNameRus,name);
        return this;
    }

    @Step("Ввод фамилии пользователя латиницей")
    public PersonalDataPage setLastNameEng(String name){
        fillField(lastNameEng,name);
        return this;
    }

    @Step("Ввод имени в блоге")
    public PersonalDataPage setBlogName(String name){
        fillField(blogName,name);
        return this;
    }

    @Step("Ввод даты рождения пользователя")
    public PersonalDataPage setBirthday(String date) {
        fillField(birthday,date);
        return this;
    }

    @Step("Выбор страны пользователя")
    public PersonalDataPage setCountry(String country){
        selectFromListBox(countryListBox, country);
        return this;
    }

    @Step("Выбор города пользователя")
    public PersonalDataPage setCity(String city){
        selectFromListBox(cityListBox, city);
        return this;
    }

    @Step("Выбор уровня знания английского языка")
    public PersonalDataPage setEnglishSkill(String level){
        selectFromListBox(englishSkill,level);
        return this;
    }

    @Step("Получение имени пользователя")
    public String getFirstNameRus(){
        return getFieldValue(firstNameRus);
    }

    @Step("Получение имени пользователя латиницей")
    public String getFirstNameEng(){
        return getFieldValue(firstNameEng);
    }

    @Step("Получение фамилии пользователя")
    public String getLastNameRus(){
        return getFieldValue(lastNameRus);
    }

    @Step("Получение фамилии пользователя латиницей")
    public String getLastNameEng(){
        return getFieldValue(lastNameEng);
    }

    @Step("Получение имени в блоге")
    public String getBlogName(){
        return getFieldValue(blogName);
    }

    @Step("Получение даты рождения пользователя")
    public String getBirthday(){
        return getFieldValue(birthday);
    }

    @Step("Получение страны пользователя")
    public String getCountry(){
        return getFieldValue(countryListBox);
    }

    @Step("Получение города пользователя")
    public String getCity(){
        return getFieldValue(cityListBox);
    }

    @Step("Получение уровня знаний английского языка")
    public String getEnglishSkill(){return getFieldValue(englishSkill);}

    @Step("Сохранение данных о пользователе в личном кабинете")
    public void saveForm(boolean redirect){
        if(redirect){
            driver.findElement(saveAndContinue).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        } else{
            driver.findElement(saveAndStay).click();
        }
    }

    @Step("Добавление контакта пользователя")
    public PersonalDataPage addContact(String type, String value){
        String typeOfContact = "(//button[@title='%s'])[last()]";
        String contactInput = "//div[text()='%s']/ancestor::*[3]/child::input";
        driver.findElement(emptyTypeOfContact).click();
        driver.findElement(By.xpath(String.format(typeOfContact, type))).click();
        driver.findElement(By.xpath(String.format(contactInput,type))).sendKeys(value);
        driver.findElement(addButton).click();
        return this;
    }

    @Step("Удаление контактов пользователя")
    public PersonalDataPage cleanContacts(){
        List<WebElement> deletes = driver.findElements(deleteButtons);
        if(deletes.size() > 1){
            for (WebElement button:deletes) {
                button.click();
            }
            driver.findElement(addButton).click();
        }
        return this;
    }

    @Step("Получение контакта пользователя")
    public String getContact(String type){
        String locator = "//div[contains(text(),'%s')]/ancestor::*[3]/child::input";
        return getFieldValue(By.xpath(String.format(locator,type)));
    }

    //Заполнение полей формы
    private void fillField(By locator, String value){
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(value);
    }
    //Получение значений из полей формы
    private String getFieldValue(By locator){
        WebElement field = driver.findElement(locator);
        String result = field.getAttribute("value");
        if(result == null){
            result = field.getText();
        }
        return result;
    }

    //Выбор значений из выпадающих списков
    private void selectFromListBox(By locator, String value){
        WebElement listBox = driver.findElement(locator);
        if(!listBox.getText().contains(value)) {
            listBox.click();
            driver
                    .findElement(By.xpath(String.format("//*[contains(text(),'%s')]",value)))
                    .click();
        }
    }
}