package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalDataPage extends BasePage {
    private final Logger logger = LogManager.getLogger(PersonalDataPage.class);
    String url = "https://otus.ru/lk/biography/personal/";
    By firstNameRus = By.id("id_fname");
    By lastNameRus = By.id("id_lname");
    By firstNameEng = By.id("id_fname_latin");
    By lastNameEng = By.id("id_lname_latin");
    By blogName = By.id("id_blog_name");
    By birthday = By.cssSelector(".input-icon > input:nth-child(1)");
    By countryListBox = By.cssSelector(".js-lk-cv-dependent-master div");
    By cityListBox = By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)");
    By englishSkill = By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)");
    By contactType = By.cssSelector("button[title='VK']");
    By saveAndContinue = By.xpath("//button[@title='Сохранить и продолжить']");
    By saveAndStay = By.xpath("//button[@title='Сохранить и заполнить позже']");

    public PersonalDataPage(WebDriver driver){
        super(driver);
    }

    public PersonalDataPage goToPage(){
        driver.get(url);
        logger.info("Переход в личный кабинет, раздел 'О себе'");
        return this;
    }

    public PersonalDataPage setFirstNameRus(String name){
        fillField(firstNameRus,name);
        return this;
    }

    public PersonalDataPage setFirstNameEng(String name){
        fillField(firstNameEng,name);
        return this;
    }

    public PersonalDataPage setLastNameRus(String name){
        fillField(lastNameRus,name);
        return this;
    }

    public PersonalDataPage setLastNameEng(String name){
        fillField(lastNameEng,name);
        return this;
    }

    public PersonalDataPage setBlogName(String name){
        fillField(blogName,name);
        return this;
    }

    public PersonalDataPage setBirthday(String date) {
        fillField(birthday,date);
        return this;
    }

    public PersonalDataPage setCountry(String country){
        selectFromListBox(countryListBox, country);
        return this;
    }

    public PersonalDataPage setCity(String city){
        selectFromListBox(cityListBox, city);
        return this;
    }

    public PersonalDataPage setEnglishSkill(String level){
        selectFromListBox(englishSkill,level);
        return this;
    }

    public String getFirstNameRus(){
        return getFieldValue(firstNameRus);
    }

    public String getFirstNameEng(){
        return getFieldValue(firstNameEng);
    }

    public String getLastNameRus(){
        return getFieldValue(lastNameRus);
    }

    public String getLastNameEng(){
        return getFieldValue(lastNameEng);
    }

    public String getBlogName(){
        return getFieldValue(blogName);
    }

    public String getBirthday(){
        return getFieldValue(birthday);
    }

    public String getCountry(){
        return getFieldValue(countryListBox);
    }

    public String getCity(){
        return getFieldValue(cityListBox);
    }

    public String getEnglishSkill(){return getFieldValue(englishSkill);}

    public void saveForm(Boolean redirect){
        if(redirect){
            driver.findElement(saveAndContinue).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        } else{
            driver.findElement(saveAndStay).click();
        }
    }

    public void setContactType(String type){
        //FIXME метод не работает
//        driver.findElement(By.cssSelector("button[title='VK']")).click();
        driver.findElement(By.cssSelector("button[title='" + type + "']")).click();
        driver.findElement(By.cssSelector("button[title='" + type + "']")).click();
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
                    .findElement(By.xpath("//*[contains(text(),'" + value + "')]"))
                    .click();
        }
    }
}