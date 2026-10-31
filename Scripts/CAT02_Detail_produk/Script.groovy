import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage
import framework.pages.ProductPage

// CAT02: independent session; locators live in Object Repository.
TestSession.run('CAT02') {
    LoginPage.loginValid()
    InventoryPage.openProduct(DemoData.BACKPACK.index)
    WebUI.verifyElementText(findTestObject('Product/label_name'), DemoData.BACKPACK.name)
    WebUI.verifyElementText(findTestObject('Product/label_price'), '$' + DemoData.BACKPACK.price)
    WebUI.verifyElementVisible(findTestObject('Product/label_description'))
    WebUI.verifyNotEqual(WebUI.getText(findTestObject('Product/label_description')).trim(), '')
    ProductPage.backToCatalog()
}
