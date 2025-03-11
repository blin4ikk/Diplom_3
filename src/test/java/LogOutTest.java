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

import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.UserProfilePage;
import toJson.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LogOutTest {
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
    UserProfilePage userProfilePage;

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
        userProfilePage = new UserProfilePage(driver);
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void userProfileLogOut(){
        driver.get(PAGE_URL);
        loginUser(mail, password); //логинимся
        mainPage.header.clickAccountButton(); //переходим по кнопке "Личный кабинет"
        userProfilePage.displayTextUserProfile(); //убедились, что мы в авторизованном личном кабинете
        userProfilePage.clickExitButton(); //тык на кнопку "Выход" в личном кабинете
        Assert.assertTrue("Должна отобразиться форма логина",
                loginPage.isVisibleLoginForm()); //проверяем, что в личном кабинете требуется авторизация
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

    @Step
    public void loginUser(String mail, String password){
        mainPage.header.clickAccountButton();
        loginPage.isVisibleLoginForm();;
        loginPage.fillAuthForm(mail, password);
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
