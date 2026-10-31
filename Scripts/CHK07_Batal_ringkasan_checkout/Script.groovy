import framework.core.TestSession
import framework.data.DemoData
import framework.pages.LoginPage
import framework.pages.CheckoutOverviewPage
import framework.components.Header
import framework.flows.CheckoutFlow

// CHK07: independent session; locators live in Object Repository.
TestSession.run('CHK07') {
    LoginPage.loginValid()
    CheckoutFlow.start([DemoData.BACKPACK.id])
    CheckoutFlow.submitCustomer()
    CheckoutOverviewPage.cancel()
    Header.verifyCartCount(1)
}
