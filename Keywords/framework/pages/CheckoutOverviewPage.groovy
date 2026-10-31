package framework.pages
import framework.core.Ui

class CheckoutOverviewPage {
    static List<String> names() { Ui.texts('Checkout/Overview/list_names') }
    static void finish() { Ui.click('Checkout/Overview/button_finish'); Ui.route('checkout-complete.html') }
    static void cancel() { Ui.click('Checkout/Overview/button_cancel'); Ui.route('inventory.html') }
}
