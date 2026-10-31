import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.CheckoutOverviewPage
import framework.flows.CheckoutFlow

// CHK04: independent session; locators live in Object Repository.
TestSession.run('CHK04') {
    LoginPage.loginValid()
    CheckoutFlow.start([DemoData.BACKPACK.id, DemoData.BIKE_LIGHT.id])
    CheckoutFlow.submitCustomer()
    WebUI.verifyEqual(CheckoutOverviewPage.names().sort(), [DemoData.BACKPACK.name, DemoData.BIKE_LIGHT.name].sort())
    WebUI.verifyElementText(findTestObject('Checkout/Overview/label_subtotal'), 'Item total: $' + DemoData.TWO_PRODUCT_TOTAL.subtotal)
    WebUI.verifyElementText(findTestObject('Checkout/Overview/label_tax'), 'Tax: $' + DemoData.TWO_PRODUCT_TOTAL.tax)
    WebUI.verifyElementText(findTestObject('Checkout/Overview/label_total'), 'Total: $' + DemoData.TWO_PRODUCT_TOTAL.total)
    TestSession.screenshot('CHK04-totals-verified')
}
