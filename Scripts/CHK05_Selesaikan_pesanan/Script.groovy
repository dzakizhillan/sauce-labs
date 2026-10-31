import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.CheckoutOverviewPage
import framework.pages.CheckoutCompletePage
import framework.components.Header
import framework.flows.CheckoutFlow

// CHK05: independent session; locators live in Object Repository.
TestSession.run('CHK05') {
    LoginPage.loginValid()
    CheckoutFlow.start([DemoData.BACKPACK.id])
    CheckoutFlow.submitCustomer()
    WebUI.verifyElementText(findTestObject('Common/label_title'), 'Checkout: Overview')
    TestSession.screenshot('CHK05-checkout-overview')
    CheckoutOverviewPage.finish()
    WebUI.verifyElementText(findTestObject('Checkout/Complete/label_confirmation'), 'Thank you for your order!')
    TestSession.screenshot('CHK05-order-confirmed')
    CheckoutCompletePage.backHome()
    Header.verifyCartCount(0)
}
