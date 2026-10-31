import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.components.Header

// CART05: independent session; locators live in Object Repository.
TestSession.run('CART05') {
    LoginPage.loginValid()
    InventoryPage.addProduct(DemoData.BACKPACK.id)
    WebUI.refresh()
    Header.verifyCartCount(1)
    CartPage.open()
    WebUI.verifyElementText(findTestObject('Cart/label_productName'), DemoData.BACKPACK.name)
    CartPage.continueShopping()
}
