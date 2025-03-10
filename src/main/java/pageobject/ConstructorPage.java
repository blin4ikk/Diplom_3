package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {
    private WebDriver driver;
    public HeaderPage header;

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.header = new HeaderPage(driver);
    }

    //блок с меню в конструкторе
    private By menuBlock = By.className("BurgerIngredients_ingredients__menuContainer__Xu3Mo");

    //раздел с булками
    private By bunTab = By.xpath(".//span[text() = 'Булки']/..");

    //раздел с соусами
    private By souceTab = By.xpath(".//span[text() = 'Соусы']/..");

    //раздел с начинками
    private By fillingsTab = By.xpath(".//span[text() = 'Начинки']/..");

    //клик по разделу "Булки"
    public void clickBunTab() {
        driver.findElement(bunTab).click();
    }

    //клик по разделу "Соусы"
    public void clickSouceTab() {
        driver.findElement(souceTab).click();
    }

    //клик по разделу "Начинки"
    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    //показ меню
    public boolean visibilityMenuBlock() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(menuBlock));
        return driver.findElement(menuBlock).isDisplayed();
    }

    //скролл в самый низ меню конструктора
    public void scrollMenuConstructor() {
        WebElement element = driver.findElement(menuBlock);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", element);
    }

    //проверяем есть ли у таба "Булки" класс, который появляется после клика на него
    public boolean checkClassAfterFocusBunsTab() {
        WebElement element = driver.findElement(bunTab);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.attributeContains(element, "class", "tab_tab_type_current__2BEPc"));
        String expectedClass = element.getAttribute("class");

        return expectedClass.contains("tab_tab_type_current__2BEPc");
    }

    //проверяем есть ли у таба "Соусы" класс, который появляется после клика на него
    public boolean checkClassAfterSouceBunsTab() {
        WebElement element = driver.findElement(souceTab);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.attributeContains(element, "class", "tab_tab_type_current__2BEPc"));
        String expectedClass = element.getAttribute("class");

        return expectedClass.contains("tab_tab_type_current__2BEPc");
    }

    //проверяем есть ли у таба "Начинки" класс, который появляется после клика на него
    public boolean checkClassAfterFillingsBunsTab() {
        WebElement element = driver.findElement(fillingsTab);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.attributeContains(element, "class", "tab_tab_type_current__2BEPc"));
        String expectedClass = element.getAttribute("class");

        return expectedClass.contains("tab_tab_type_current__2BEPc");
    }
}
