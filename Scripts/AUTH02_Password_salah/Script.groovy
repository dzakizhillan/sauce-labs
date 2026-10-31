import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.AppConfig
import framework.core.TestSession
import framework.pages.LoginPage

// AUTH02: independent session; locators live in Object Repository.
TestSession.run('AUTH02') {
    LoginPage.submit(AppConfig.username, 'wrong_password')
    WebUI.verifyElementText(findTestObject('Login/label_error'), 'Epic sadface: Username and password do not match any user in this service')
    WebUI.verifyElementVisible(findTestObject('Login/button_login'))
}
