package framework.core

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.WebDriver
import java.time.Duration
import framework.core.AppConfig

/** Infrastructure only: resolves native repository objects and synchronizes actions. */
class Ui {
    static TestObject object(String path, Map variables = [:]) {
        TestObject result = findTestObject(path, variables)
        if (result == null) throw new IllegalArgumentException('Missing repository object: ' + path)
        return result
    }
    static void click(String path, Map variables = [:]) {
        TestObject target = object(path, variables)
        WebUI.waitForElementVisible(target, AppConfig.timeout as int)
        WebUI.waitForElementClickable(target, AppConfig.timeout as int)
        WebUI.click(target)
    }
    static void fill(String path, String value) {
        TestObject target = object(path)
        WebUI.waitForElementVisible(target, AppConfig.timeout as int)
        WebUI.setText(target, value)
    }
    static List<String> texts(String path) {
        WebUI.findWebElements(object(path), AppConfig.timeout as int).collect { it.getText() }
    }
    static void route(String suffix) {
        String expected = AppConfig.baseUrl + suffix
        new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(AppConfig.timeout as long)).until({ WebDriver driver ->
            driver.getCurrentUrl() == expected
        } as ExpectedCondition<Boolean>)
        WebUI.verifyMatch(WebUI.getUrl(), expected, false)
    }
}
