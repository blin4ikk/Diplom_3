import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver createForName(String browserName) {
        if (browserName.equals("Yandex")) {
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
            return new ChromeDriver(options);
        } else if (browserName.equals("Chrome")) {
            return new ChromeDriver();
        } else {
            throw new RuntimeException("Нераспознанный браузер: " + browserName);
        }
    }
}