import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.*;
import toJson.User;

import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    private final String mail = generateRandomEmail();
    private final String password = generateRandomPassword();
    private final String name = generateRandomName();
    private String accessToken;
    private String deleteMessage;
    private final Gson gson = new Gson();
    private WebDriver driver;
    private static final String DEFAULT_BROWSER_NAME = "Chrome";
    private static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";
    MainPage mainPage;
    LoginPage loginPage;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        String browserName = System.getenv(BROWSER_NAME_ENV_VARIABLE);
        driver =
                WebDriverFactory.createForName(browserName != null ? browserName : DEFAULT_BROWSER_NAME);
        User user = new User(mail, password, name);
        createUser(user);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("через кнопку «Личный кабинет»")
    public void loginFromHeaderMainPage(){
        driver.get(PAGE_URL);
        mainPage.header.clickAccountButton();

        loginPage.visibleLoginForm();
        loginPage.fillAuthForm(mail, password);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        Assert.assertTrue("Меню на главной не отображается", mainPage.constructorPage.visibilityMenuBlock());

    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginFromBottomMailPage(){
        driver.get(PAGE_URL);

        mainPage.clickAuthButtonFromMainPage();

        loginPage.visibleLoginForm();
        loginPage.fillAuthForm(mail, password);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        Assert.assertTrue("Должно отобразиться меню конструктора на главной", mainPage.constructorPage.visibilityMenuBlock());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginFromRegisterPage(){
        driver.get(PAGE_URL);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        mainPage.header.clickAccountButton();
        loginPage.clickRegisterButtonPage();
        registrationPage.clickAuthButtonFromRegisterForm();

        loginPage.visibleLoginForm();
        loginPage.fillAuthForm(mail, password);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        Assert.assertTrue("Меню на главной не отображается", mainPage.constructorPage.visibilityMenuBlock());

    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginFromResetPasswordPage(){
        driver.get(PAGE_URL);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);

        mainPage.header.clickAccountButton();
        loginPage.clickRegisterButtonPage();
        resetPasswordPage.clickAuthFromResetPasswordPage();

        loginPage.visibleLoginForm();
        loginPage.fillAuthForm(mail, password);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));
        Assert.assertTrue("Меню на главной не отображается", mainPage.constructorPage.visibilityMenuBlock());

    }

    @After
    public void teardown() {
        driver.quit(); // Закрываем браузер

        //удаляем пользака
        if (accessToken != null) {
            deleteUser(accessToken);
            System.out.println(deleteMessage);
        } else {
            System.out.println(deleteMessage);
        }
    }

    @Step("Создание пользователя")
    private void createUser(User user) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(gson.toJson(user))
                .when()
                .post("/api/auth/register")
                .then()
                .extract().response();
        accessToken = response.body().path("accessToken");
    }

    @Step("Генерация почты")
    private String generateRandomEmail() {
        return "user_" + RandomStringUtils.randomAlphanumeric(6) + "@yandex.ru";
    }

    @Step("Генерация пароля")
    private String generateRandomPassword() {
        return RandomStringUtils.randomNumeric(6);
    }

    @Step("Генерация имени")
    private String generateRandomName() {
        return "Test_" + RandomStringUtils.randomAlphabetic(4);
    }

    @Step("Удаление пользователя")
    private void deleteUser(String accessToken) {
        Response response = given()
                .when()
                .header("Authorization", accessToken)
                .delete("/api/auth/user")
                .then()
                .statusCode(202)
                .body("success", equalTo(true))
                .extract().response();
        deleteMessage = response.body().path("message");
    }

}
