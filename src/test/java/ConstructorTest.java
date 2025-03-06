import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.ConstructorPage;
import pageobject.MainPage;

public class ConstructorTest {
    private WebDriver driver;
    private static final String DEFAULT_BROWSER_NAME = "Chrome";
    private static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";
    MainPage mainPage;
    ConstructorPage constructorPage;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        String browserName = System.getenv(BROWSER_NAME_ENV_VARIABLE);
        driver =
                WebDriverFactory.createForName(browserName != null ? browserName : DEFAULT_BROWSER_NAME);
        mainPage = new MainPage(driver);
        constructorPage = new ConstructorPage(driver);
    }

        @Test
        @DisplayName("Проверка перехода в раздел Булки")
        public void checkBunsList(){
            driver.get(PAGE_URL);
            mainPage.header.clickConstructorButton();
            constructorPage.clickFillingsList();
            constructorPage.clickBunList();
            Assert.assertTrue("Должен быть отображен список булок", mainPage.constructorPage.visibilityBunsForSelect());
        }

    @Test
    @DisplayName("Проверка перехода в раздел Соусы")
    public void checkSoucesList(){
        driver.get(PAGE_URL);
        mainPage.header.clickConstructorButton();
        constructorPage.clickSouceList();
        Assert.assertTrue("Должен быть отображен список булок", mainPage.constructorPage.visibilitySoucesForSelect());
    }

    @Test
    @DisplayName("Проверка перехода в раздел Начинки")
    public void checkFillingsList(){
        driver.get(PAGE_URL);
        mainPage.header.clickConstructorButton();
        constructorPage.clickFillingsList();
        Assert.assertTrue("Должен быть отображен список булок", mainPage.constructorPage.visibilityFillingsForSelect());
    }

    @After
    public void teardown() {
        driver.quit(); // Закрываем браузер
    }
}
