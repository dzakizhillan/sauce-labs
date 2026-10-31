import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.components.Header

// CART02: independent session; locators live in Object Repository.
TestSession.run('CART02') {
    LoginPage.loginValid()
    InventoryPage.addProduct(DemoData.BACKPACK.id)
    InventoryPage.addProduct(DemoData.BIKE_LIGHT.id)
    Header.verifyCartCount(2)
    CartPage.open()
    WebUI.verifyEqual(CartPage.names().sort(), [DemoData.BACKPACK.name, DemoData.BIKE_LIGHT.name].sort())
}
