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
    private By menuBlock = By.xpath(".//main[@class = 'App_componentContainer__2JC2W']");

    //раздел с булками
    private By bunList = By.xpath(".//span[text() = 'Булки']");

    //раздел с соусами
    private By souceList = By.xpath(".//span[text() = 'Соусы']");

    //раздел с начинками
    private By fillingsList = By.xpath(".//span[text() = 'Начинки']");


    //секция с выбором булок
    private By bunsForSelect = By.xpath(".//h2[text()='Булки']/following-sibling::ul[1]");

    //секция с выбором соусов
    private By soucesForSelect = By.xpath(".//h2[text()='Соусы']/following-sibling::ul[1]");

    //секция с выбором начинок
    private By fillingsForSelect = By.xpath(".//h2[text()='Начинки']/following-sibling::ul[1]");

    //проверка видимости секции с булками
    public WebElement getBunsForSelect(){
        return driver.findElement(bunsForSelect);
    }

    //проверка видимости секции с соусами
    public WebElement getSoucesForSelect(){
        return driver.findElement(soucesForSelect);
    }

    //проверка видимости секции с начинками
    public WebElement getFillingsForSelect(){
        return driver.findElement(fillingsForSelect);
    }

    // Проверка, что элемент отображается в области видимости
    public boolean isIngedientsInView(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(1)).until(driver -> {
            Rectangle rect = element.getRect();
            Dimension windowSize = driver.manage().window().getSize();

            return rect.getX() < windowSize.getWidth() && rect.getX() + rect.getWidth() > 0
                    && rect.getY() < windowSize.getHeight() && rect.getY() + rect.getHeight() > 0;
        });
    }

    //клик по разделу "Булки"
    public void clickBunList(){
        driver.findElement(bunList).click();
    }

    //клик по разделу "Соусы"
    public void clickSouceList(){
        driver.findElement(souceList).click();
    }

    //клик по разделу "Начинки"
    public void clickFillingsList(){
        driver.findElement(fillingsList).click();
    }

    //показ меню
    public boolean visibilityMenuBlock(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(menuBlock));
        return driver.findElement(menuBlock).isDisplayed();
    }

}
