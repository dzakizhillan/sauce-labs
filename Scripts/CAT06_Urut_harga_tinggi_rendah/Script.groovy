import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.pages.LoginPage
import framework.pages.InventoryPage

// CAT06: independent session; locators live in Object Repository.
TestSession.run('CAT06') {
    LoginPage.loginValid()
    InventoryPage.sortBy('hilo')
    def actual = InventoryPage.prices()
    WebUI.verifyEqual(actual.size(), 6)
    def expected = new ArrayList(actual).sort().reverse()
    WebUI.verifyEqual(actual, expected)
}
