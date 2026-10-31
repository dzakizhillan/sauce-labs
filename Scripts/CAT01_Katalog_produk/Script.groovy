import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.AppConfig
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.InventoryPage

// CAT01: independent session; locators live in Object Repository.
TestSession.run('CAT01') {
    LoginPage.loginValid()
    WebUI.verifyEqual(InventoryPage.names().sort(), new ArrayList(DemoData.CATALOG_NAMES).sort())
    WebUI.verifyEqual(InventoryPage.prices().size(), 6)
    WebUI.verifyEqual(InventoryPage.prices().every { it > 0 }, true)
    WebUI.verifyEqual(WebUI.findWebElements(findTestObject('Inventory/list_addButtons'), AppConfig.timeout as int).size(), 6)
}
