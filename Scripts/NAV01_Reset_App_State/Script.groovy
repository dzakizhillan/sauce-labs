import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.components.Header
import framework.components.NavigationMenu

// NAV01: independent session; locators live in Object Repository.
TestSession.run('NAV01') {
    LoginPage.loginValid()
    InventoryPage.addProduct(DemoData.BACKPACK.id)
    NavigationMenu.resetAppState()
    WebUI.refresh()
    Header.verifyCartCount(0)
    CartPage.open()
    WebUI.verifyElementNotPresent(findTestObject('Cart/list_items'), 2)
}
