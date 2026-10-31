import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.AppConfig
import framework.core.TestSession
import framework.pages.LoginPage

// AUTH04: independent session; locators live in Object Repository.
TestSession.run('AUTH04') {
    LoginPage.submit(AppConfig.username, '')
    WebUI.verifyElementText(findTestObject('Login/label_error'), 'Epic sadface: Password is required')
}
