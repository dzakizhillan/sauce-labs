import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.AppConfig
import framework.core.TestSession
import framework.pages.LoginPage

// AUTH05: independent session; locators live in Object Repository.
TestSession.run('AUTH05') {
    LoginPage.submit('locked_out_user', AppConfig.password)
    WebUI.verifyElementText(findTestObject('Login/label_error'), 'Epic sadface: Sorry, this user has been locked out.')
}
