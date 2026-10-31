package framework.pages
import framework.core.Ui

class CartPage {
    static void open() { Ui.click('Common/link_cart'); Ui.route('cart.html') }
    static void removeProduct(String productId) { Ui.click('Cart/button_removeProduct', [productId: productId]) }
    static void checkout() { Ui.click('Cart/button_checkout'); Ui.route('checkout-step-one.html') }
    static void continueShopping() { Ui.click('Cart/button_continueShopping'); Ui.route('inventory.html') }
    static List<String> names() { Ui.texts('Cart/list_names') }
}
