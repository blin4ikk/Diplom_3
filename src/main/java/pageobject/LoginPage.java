package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //кнопка перехода на страницу сброса пароля
    private By resetPasswordButton = By.xpath(".//a[text() = 'Восстановить пароль']");

    //кнопка перехода на страницу регистрации
    private By registerButtonPage = By.xpath(".//a[text() = 'Зарегистрироваться']");

    //форма логина
    private By loginForm = By.className("Auth_login__3hAey");

    //поле ввода Email
    private By mailInput = By.xpath(".//input[@name = 'name']");

    //поле ввода пароля
    private By passwordInput = By.xpath(".//input[@name = 'Пароль']");

    //кнопка "Войти"
    private By loginButton = By.xpath(".//button[text() = 'Войти']");

    //ввод в поле "Email"
    public void setMailInput(String mail) {
        driver.findElement(mailInput).click();
        driver.findElement(mailInput).sendKeys(mail);
    }

    //ввод в поле "Пароль"
    public void setPasswordInput(String password) {
        driver.findElement(passwordInput).click();
        driver.findElement(passwordInput).sendKeys(password);
    }

    //клик по кнопке "Войти"
    public void clickLoginButton(){
        driver.findElement(loginButton).click();
    }

    //клик по кнопке перехода на страницу сброса пароля
    public void clickResetPasswordButton(){
        driver.findElement(resetPasswordButton).click();
    }

    //клик по кнопке перехода на страницу регистрации
    public void clickRegisterButtonPage(){
        driver.findElement(registerButtonPage).click();
    }

    //проверяем, отобразилась ли форма логина
    public boolean isVisibleLoginForm(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(loginForm));
        return driver.findElement(loginForm).isDisplayed();
    }

    public void fillAuthForm(String mail, String password){
        setMailInput(mail);
        setPasswordInput(password);
        clickLoginButton();
    }

}