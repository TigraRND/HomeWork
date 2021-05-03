import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class YandexMarketPage {
    protected static WebDriver driver;
    private String url = "https://market.yandex.ru/";
    By elctronikLink =  By.xpath("//span[text()='Электроника']/parent::a[contains(@href,'/catalog--elektronika/')]");

    @Test
    public void test(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(elctronikLink).click();
    }
}
