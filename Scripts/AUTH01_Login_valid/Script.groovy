import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.pages.LoginPage

// AUTH01: independent session; locators live in Object Repository.
TestSession.run('AUTH01') {
    LoginPage.loginValid()
    WebUI.verifyElementText(findTestObject('Common/label_title'), 'Products')
    TestSession.screenshot('AUTH01-login-verified')
}
