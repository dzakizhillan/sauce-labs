package framework.pages
import framework.core.Ui

class CheckoutInformationPage {
    static void fillCustomer(String firstName, String lastName, String postalCode) {
        Ui.fill('Checkout/Information/input_firstName', firstName)
        Ui.fill('Checkout/Information/input_lastName', lastName)
        Ui.fill('Checkout/Information/input_postalCode', postalCode)
    }
    static void continueCheckout() { Ui.click('Checkout/Information/button_continue') }
    static void cancel() { Ui.click('Checkout/Information/button_cancel'); Ui.route('cart.html') }
}
