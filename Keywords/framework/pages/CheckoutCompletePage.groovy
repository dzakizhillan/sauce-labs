package framework.pages
import framework.core.Ui

class CheckoutCompletePage {
    static void backHome() { Ui.click('Checkout/Complete/button_backHome'); Ui.route('inventory.html') }
}
