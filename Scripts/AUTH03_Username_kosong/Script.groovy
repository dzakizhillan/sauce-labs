import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.pages.LoginPage

// AUTH03: independent session; locators live in Object Repository.
TestSession.run('AUTH03') {
    LoginPage.submit('', '')
    WebUI.verifyElementText(findTestObject('Login/label_error'), 'Epic sadface: Username is required')
}
