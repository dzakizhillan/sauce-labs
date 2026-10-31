package framework.components
import framework.core.Ui
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.WebDriver
import java.time.Duration
import framework.core.AppConfig

class NavigationMenu {
    static void open() {
        Ui.click('Navigation/button_menu')
        // Use the repository object, including for the animation wait: no selector here.
        new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(AppConfig.timeout as long)).until({ WebDriver driver ->
            def panels = WebUI.findWebElements(Ui.object('Navigation/panel_menu'), 1)
            if (!panels) return false
            def panel = panels[0]
            WebUI.executeJavaScript('''
                const panel = arguments[0];
                const rect = panel.getBoundingClientRect();
                return panel.getAttribute('aria-hidden') !== 'true' && rect.width > 0 &&
                    rect.left >= -0.5 && rect.right <= window.innerWidth + 0.5;
            ''', [panel]) == true
        } as ExpectedCondition<Boolean>)
    }
    static void logout() { open(); Ui.click('Navigation/link_logout') }
    static void resetAppState() { open(); Ui.click('Navigation/link_reset') }
}
