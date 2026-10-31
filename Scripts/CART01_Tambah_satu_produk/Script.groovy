import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.components.Header

// CART01: independent session; locators live in Object Repository.
TestSession.run('CART01') {
    LoginPage.loginValid()
    InventoryPage.addProduct(DemoData.BACKPACK.id)
    Header.verifyCartCount(1)
    WebUI.verifyElementVisible(findTestObject('Inventory/button_removeProduct', [productId: DemoData.BACKPACK.id]))
    CartPage.open()
    WebUI.verifyElementText(findTestObject('Cart/label_productName'), DemoData.BACKPACK.name)
    WebUI.verifyElementText(findTestObject('Cart/label_productPrice'), '$' + DemoData.BACKPACK.price)
    WebUI.verifyElementText(findTestObject('Cart/label_quantity'), '1')
    TestSession.screenshot('CART01-cart-verified')
}
