import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.Ui
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.CheckoutInformationPage
import framework.flows.CheckoutFlow

// CHK02: independent session; locators live in Object Repository.
TestSession.run('CHK02') {
    LoginPage.loginValid()
    CheckoutFlow.start([DemoData.BACKPACK.id])
    CheckoutInformationPage.fillCustomer('QA', '', '')
    CheckoutInformationPage.continueCheckout()
    WebUI.verifyElementText(findTestObject('Checkout/Information/label_error'), 'Error: Last Name is required')
    Ui.route('checkout-step-one.html')
}
