import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.AppConfig
import framework.core.TestSession

// AUTH07: independent session; locators live in Object Repository.
TestSession.run('AUTH07') {
    WebUI.navigateToUrl(AppConfig.baseUrl + 'inventory.html')
    WebUI.verifyElementVisible(findTestObject('Login/button_login'))
    WebUI.verifyElementText(findTestObject('Login/label_error'), "Epic sadface: You can only access '/inventory.html' when you are logged in.")
}
