package framework.pages
import framework.core.Ui
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class InventoryPage {
    static void addProduct(String productId) { Ui.click('Inventory/button_addProduct', [productId: productId]) }
    static void removeProduct(String productId) { Ui.click('Inventory/button_removeProduct', [productId: productId]) }
    static void openProduct(String productIndex) { Ui.click('Inventory/link_product', [productIndex: productIndex]) }
    static void sortBy(String option) { WebUI.selectOptionByValue(Ui.object('Inventory/select_sort'), option, false) }
    static List<String> names() { Ui.texts('Inventory/list_names') }
    static List<BigDecimal> prices() { Ui.texts('Inventory/list_prices').collect { new BigDecimal(it.replace('$', '')) } }
}
