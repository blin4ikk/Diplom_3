package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserProfilePage {
    private WebDriver driver;
    public HeaderPage header;

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        this.header = new HeaderPage(driver);
    }

    //текст в личном кабинете
    private By textUserProfile = By.xpath(".//p[text() = 'В этом разделе вы можете изменить свои персональные данные']");

    //кнопка "Выход"
    private By exitButton = By.xpath(".//button[text() = 'Выход']");

    //клик по кнопке "Выход"
    public void clickExitButton(){
        driver.findElement(exitButton).click();
    }

    //получение текста в профиле пользователя
    public String displayTextUserProfile(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(textUserProfile));
        return driver.findElement(textUserProfile).getText();
    }


}
