package framework.components
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.AppConfig

/** Shared header assertions; badges are used on several pages. */
class Header {
    static void verifyCartCount(int expected) {
        if (expected == 0) WebUI.verifyElementNotPresent(findTestObject('Common/badge_cart'), AppConfig.timeout as int)
        else WebUI.verifyElementText(findTestObject('Common/badge_cart'), expected.toString())
    }
}
