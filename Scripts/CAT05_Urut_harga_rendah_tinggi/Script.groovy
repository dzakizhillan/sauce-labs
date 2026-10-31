import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.pages.LoginPage
import framework.pages.InventoryPage

// CAT05: independent session; locators live in Object Repository.
TestSession.run('CAT05') {
    LoginPage.loginValid()
    InventoryPage.sortBy('lohi')
    def actual = InventoryPage.prices()
    WebUI.verifyEqual(actual.size(), 6)
    def expected = new ArrayList(actual).sort()
    WebUI.verifyEqual(actual, expected)
}
