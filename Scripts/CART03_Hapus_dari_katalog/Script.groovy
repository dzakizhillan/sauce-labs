import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.components.Header

// CART03: independent session; locators live in Object Repository.
TestSession.run('CART03') {
    LoginPage.loginValid()
    InventoryPage.addProduct(DemoData.BACKPACK.id)
    InventoryPage.removeProduct(DemoData.BACKPACK.id)
    Header.verifyCartCount(0)
    WebUI.verifyElementVisible(findTestObject('Inventory/button_addProduct', [productId: DemoData.BACKPACK.id]))
    CartPage.open()
    WebUI.verifyElementNotPresent(findTestObject('Cart/list_items'), 2)
}
