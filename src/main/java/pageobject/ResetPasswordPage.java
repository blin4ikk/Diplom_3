package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage {
    private WebDriver driver;

    public ResetPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    //кнопка перехода на страницу входа с главной страницы
    private By authFromResetPasswordPage = By.className("Auth_link__1fOlj");

    //клик по кнопке перехода на страницу входа
    public void clickAuthFromResetPasswordPage(){
        driver.findElement(authFromResetPasswordPage).click();
    }
}
