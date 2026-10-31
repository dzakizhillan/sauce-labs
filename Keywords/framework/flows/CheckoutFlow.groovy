package framework.flows
import framework.pages.InventoryPage
import framework.pages.CartPage
import framework.pages.CheckoutInformationPage
import framework.core.Ui
import framework.data.DemoData

/** Repeated cross-page setup only; scenario-specific assertions stay in test cases. */
class CheckoutFlow {
    static void start(List<String> productIds) {
        productIds.each { InventoryPage.addProduct(it) }
        CartPage.open()
        CartPage.checkout()
    }
    static void submitCustomer() {
        def customer = DemoData.CUSTOMER
        CheckoutInformationPage.fillCustomer(customer.firstName, customer.lastName, customer.postalCode)
        CheckoutInformationPage.continueCheckout()
        Ui.route('checkout-step-two.html')
    }
}
