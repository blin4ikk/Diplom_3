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
            constructorPage.visibilityMenuBlock();
            mainPage.header.clickConstructorButton(); //нажимаем на конструктор в шапке
            constructorPage.scrollMenuConstructor(); //выполняем скролл в самый низ меню
            constructorPage.clickBunTab(); //нажимаем на заголовок "Булки", чтобы проверить скролл к булкам
            Assert.assertTrue("Класс 'tab_tab_type_current__2BEPc' не добавлен после клика", constructorPage.checkClassAfterFocusBunsTab());
        }

    @Test
    @DisplayName("Проверка перехода в раздел Соусы")
    public void checkSoucesList(){
        driver.get(PAGE_URL);
        constructorPage.visibilityMenuBlock();
        mainPage.header.clickConstructorButton(); //нажимаем на конструктор в шапке
        constructorPage.scrollMenuConstructor(); //выполняем скролл в самый низ меню
        constructorPage.clickSouceTab();
        Assert.assertTrue("Класс 'tab_tab_type_current__2BEPc' не добавлен после клика", constructorPage.checkClassAfterSouceBunsTab());
    }

    @Test
    @DisplayName("Проверка перехода в раздел Начинки")
    public void checkFillingsList(){
        driver.get(PAGE_URL);
        constructorPage.visibilityMenuBlock();
        mainPage.header.clickConstructorButton(); //нажимаем на конструктор в шапке
        constructorPage.clickFillingsTab(); //нажимаем на заголовок "Начинки"
        Assert.assertTrue("Класс 'tab_tab_type_current__2BEPc' не добавлен после клика", constructorPage.checkClassAfterFillingsBunsTab());
    }

    @After
    public void teardown() {
        driver.quit(); // Закрываем браузер
    }
}
