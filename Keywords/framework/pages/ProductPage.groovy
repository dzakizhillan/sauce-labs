package framework.pages
import framework.core.Ui

class ProductPage {
    static void backToCatalog() { Ui.click('Product/button_back'); Ui.route('inventory.html') }
}
