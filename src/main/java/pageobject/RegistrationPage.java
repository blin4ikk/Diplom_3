package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    //форма регистрации
    private By registerForm = By.xpath(".//form");

    //поле ввода имя
    private By nameInput = By.xpath(".//fieldset[1]//input"); // ".//fieldset[1]//input"

    //поле ввода Email
    private By mailInput = By.xpath(".//fieldset[2]//input"); // ".//fieldset[2]//input"

    //поле ввода пароль
    private By passwordInput = By.xpath("//input[@name='Пароль']");

    //кнопка "Зарегистрироваться"
    private By registerButton = By.xpath(".//button[text() = 'Зарегистрироваться']");

    //текст ошибки на некорректный пароль
    private By textError = By.className("input__error");

    //кнопка "Войти" на странице регистрации
    private By authButtonFromRegisterForm = By.className("Auth_link__1fOlj");

    //показ формы регистрации
    public void visibleRegisterForm(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(registerForm));
        driver.findElement(registerForm).isDisplayed();
    }

    //ввод в поле "Email"
    public void setMailInput(String mail) {
        driver.findElement(mailInput).click();
        driver.findElement(mailInput).sendKeys(mail);
    }

    //ввод в поле "Имя"
    public void setNameInput(String name) {
        driver.findElement(nameInput).click();
        driver.findElement(nameInput).sendKeys(name);
    }

    //ввод в поле "Пароль"
    public void setPasswordInput(String password) {
        driver.findElement(passwordInput).click();
        driver.findElement(passwordInput).sendKeys(password);
    }

    //клик по кнопке "Зарегистрироваться"
    public void clickRegisterButton(){
        driver.findElement(registerButton).click();
    }

    //показ ошибки на пароль менее 6 символов
    public String visibleTextError(){
        driver.findElement(mailInput).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(textError));
        return driver.findElement(textError).getText();
    }

    //клик по кнопке "Войти" на странице регистрации
    public void clickAuthButtonFromRegisterForm(){
        driver.findElement(authButtonFromRegisterForm).click();
    }
}
