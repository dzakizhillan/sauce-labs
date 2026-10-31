package framework.core

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import framework.core.AppConfig

/** Owns one isolated browser per test; preserves the original assertion failure. */
class TestSession {
    /** Capture a named checkpoint after its assertions; evidence failure does not hide test results. */
    static void screenshot(String checkpoint) {
        if (!(checkpoint ==~ /[A-Za-z0-9_-]+/)) {
            throw new IllegalArgumentException('Screenshot name must contain only letters, numbers, underscores or hyphens')
        }
        try {
            String folder = RunConfiguration.getReportFolder()
            if (folder) {
                WebUI.takeScreenshot(folder + '/' + checkpoint + '.png', FailureHandling.OPTIONAL)
            } else {
                WebUI.comment('Screenshot unavailable: no report folder for ' + checkpoint)
            }
        } catch (Throwable screenshotFailure) {
            WebUI.comment('Screenshot unavailable: ' + screenshotFailure.message)
        }
    }

    static void run(String caseId, Closure scenario) {
        AppConfig.validate()
        // Preferences apply only to the browser profile created for this test.
        RunConfiguration.setWebDriverPreferencesProperty('prefs', [
            'credentials_enable_service': false,
            'profile.password_manager_enabled': false,
            'profile.password_manager_leak_detection': false
        ])
        try {
            WebUI.openBrowser('')
            WebUI.disableSmartWait()
            WebUI.setViewPortSize(1366, 768)
            WebUI.navigateToUrl(AppConfig.baseUrl)
            scenario.call()
        } catch (Throwable failure) {
            try {
                String folder = RunConfiguration.getReportFolder()
                if (folder) WebUI.takeScreenshot(folder + '/' + caseId + '-failure.png', FailureHandling.OPTIONAL)
            } catch (Throwable screenshotFailure) {
                WebUI.comment('Screenshot unavailable: ' + screenshotFailure.message)
            }
            throw failure
        } finally {
            WebUI.closeBrowser(FailureHandling.OPTIONAL)
        }
    }
}
