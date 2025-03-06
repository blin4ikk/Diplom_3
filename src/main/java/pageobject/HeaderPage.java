package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage {
    private WebDriver driver;

    public HeaderPage(WebDriver driver) {
        this.driver = driver;
    }

    //кнопка "Конструктор"
    private By constructorButton = By.xpath(".//p[text() = 'Конструктор']");

    //кнопка "Логотип"
    private By logoButton = By.className("AppHeader_header__logo__2D0X2");

    //кнопка перехода на страницу логина в хедере главной страницы
    private By accountButton = By.xpath(".//p[text() = 'Личный Кабинет']");

    //клик по кнопке "Конструктор"
    public void clickConstructorButton(){
        driver.findElement(constructorButton).click();
    }

    //клик по кнопке "Логотип"
    public void clickLogoButton(){
        driver.findElement(logoButton).click();
    }

    //клик по кнопке перехода на страницу входа
    public void clickAccountButton(){
        driver.findElement(accountButton).click();
    }
}
