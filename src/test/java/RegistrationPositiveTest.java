import com.google.gson.Gson;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.MainPage;
import toJson.User;
import pageobject.RegistrationPage;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationPositiveTest {
    private final Gson gson = new Gson();
    private WebDriver driver;
    private static final String DEFAULT_BROWSER_NAME = "Chrome";
    private static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";

    private String mail = generateRandomEmail();
    private String name = generateRandomName();
    private String password = generateRandomPassword();

    private String accessToken;
    private String deleteMessage;

    MainPage mainPage;
    LoginPage loginPage;
    RegistrationPage registrationPage;


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        String browserName = System.getenv(BROWSER_NAME_ENV_VARIABLE);
        driver =
                WebDriverFactory.createForName(browserName != null ? browserName : DEFAULT_BROWSER_NAME);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void registrationSuccess(){
        driver.get(PAGE_URL);

        mainPage.clickAuthButtonFromMainPage(); //Нажимаем на кнопку "Войти в аккаунт"
        loginPage.clickRegisterButtonPage(); //Нажимаем на кнопку "Зарегистрироваться"

        registrationPage.visibleRegisterForm();  //Увидели форму регистрации
        registrationPage.setMailInput(mail); //Указываем имейл
        registrationPage.setNameInput(name); //Указываем имя
        registrationPage.setPasswordInput(password); //Указываем пароль
        registrationPage.clickRegisterButton(); //Нажимаем на кнопку "Зарегистрироваться"

    }

    @After
    public void teardown() {
        driver.quit(); // Закрываем браузер
        loginUser(mail, password); //логинимся для получения accessToken

        //удаляем пользака
        if (accessToken != null) {
            deleteUser(accessToken);
            System.out.println(deleteMessage);
        } else {
            System.out.println(deleteMessage);
        }
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

    @Step("Логин пользователя")
    private void loginUser(String mail, String password) {
 User loginUser = new User(mail, password);
           Response response = given()
                .header("Content-Type", "application/json")
                .body(gson.toJson(loginUser))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract().response();
           accessToken = response.body().path("accessToken");
           System.out.println("Пользователь успешно залогинился: " + response.body().path("user"));
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
        System.out.println("Пользователь успешно удален: " + response.asString());
    }

}
