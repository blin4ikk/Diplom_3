import com.google.gson.Gson;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegistrationPage;

public class RegistrationNegativeTest {
    private final Gson gson = new Gson();
    private WebDriver driver;
    private static final String DEFAULT_BROWSER_NAME = "Chrome";
    private static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";
    private String expected = "Некорректный пароль";
    private String password = generateRandomPassword();
    RegistrationPage registrationPage;
    MainPage mainPage;
    LoginPage loginPage;

    @Before
    public void setUp(){
        String browserName = System.getenv(BROWSER_NAME_ENV_VARIABLE);
        driver =
                WebDriverFactory.createForName(browserName != null ? browserName : DEFAULT_BROWSER_NAME);
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Проверяем контроль на пароль менее 6 символов")
    public void uncorrectPasswordTextControl(){
        driver.get(PAGE_URL);

        mainPage.clickAuthButtonFromMainPage(); //Нажимаем на кнопку "Войти в аккаунт"
        loginPage.clickRegisterButtonPage(); //Нажимаем "Зарегистрироваться"

        registrationPage.visibleRegisterForm(); //Видим форму регистрации
        registrationPage.setPasswordInput(password); //устанавливаем пароль меньше 6 символов
        String actual = registrationPage.visibleTextError();
        Assert.assertEquals("Пароль не должен быть менее 6 символов", expected, actual); //проверяем, что отобразился текст ошибки
    }

    @After
    public void teardown() {
        driver.quit(); // Закрываем браузер
    }

    @Step("Генерация пароля")
    private String generateRandomPassword() {
        return RandomStringUtils.randomNumeric(5);
    }
}
