import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.CheckoutInformationPage
import framework.flows.CheckoutFlow

// CHK06: independent session; locators live in Object Repository.
TestSession.run('CHK06') {
    LoginPage.loginValid()
    CheckoutFlow.start([DemoData.BACKPACK.id])
    CheckoutInformationPage.cancel()
    WebUI.verifyElementText(findTestObject('Cart/label_productName'), DemoData.BACKPACK.name)
}
