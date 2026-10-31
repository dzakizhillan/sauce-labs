import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.Ui
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.CheckoutInformationPage
import framework.flows.CheckoutFlow

// CHK01: independent session; locators live in Object Repository.
TestSession.run('CHK01') {
    LoginPage.loginValid()
    CheckoutFlow.start([DemoData.BACKPACK.id])
    CheckoutInformationPage.fillCustomer('', '', '')
    CheckoutInformationPage.continueCheckout()
    WebUI.verifyElementText(findTestObject('Checkout/Information/label_error'), 'Error: First Name is required')
    Ui.route('checkout-step-one.html')
}
