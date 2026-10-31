import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.components.Header

// CART04: independent session; locators live in Object Repository.
TestSession.run('CART04') {
    LoginPage.loginValid()
    InventoryPage.addProduct(DemoData.BACKPACK.id)
    CartPage.open()
    CartPage.removeProduct(DemoData.BACKPACK.id)
    WebUI.verifyElementNotPresent(findTestObject('Cart/list_items'), 2)
    Header.verifyCartCount(0)
}
