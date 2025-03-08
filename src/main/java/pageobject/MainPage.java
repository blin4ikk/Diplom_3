package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;
    public HeaderPage header;
    public ConstructorPage constructorPage;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.header = new HeaderPage(driver);
        this.constructorPage = new ConstructorPage(driver);
    }

    //кнопка перехода на страницу логина в контенте главной страницы
    private By authBottomButtonFromMainPage = By.xpath(".//button[text() = 'Войти в аккаунт']");


    //клик по кнопке перехода на страницу входа
    public void clickAuthButtonFromMainPage(){
        driver.findElement(authBottomButtonFromMainPage).click();
    }


    //кнопка "Конструктор"
}
