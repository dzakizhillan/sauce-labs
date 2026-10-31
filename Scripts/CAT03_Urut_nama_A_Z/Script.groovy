import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.pages.LoginPage
import framework.pages.InventoryPage

// CAT03: independent session; locators live in Object Repository.
TestSession.run('CAT03') {
    LoginPage.loginValid()
    InventoryPage.sortBy('az')
    def actual = InventoryPage.names()
    WebUI.verifyEqual(actual.size(), 6)
    def expected = new ArrayList(actual).sort()
    WebUI.verifyEqual(actual, expected)
}
