package framework.data

/** Public demo fixtures. These values are test expectations, not values scraped from UI. */
class DemoData {
    static final Map BACKPACK = [id: 'sauce-labs-backpack', index: '4', name: 'Sauce Labs Backpack', price: '29.99'].asImmutable()
    static final Map BIKE_LIGHT = [id: 'sauce-labs-bike-light', index: '0', name: 'Sauce Labs Bike Light', price: '9.99'].asImmutable()
    static final Map CUSTOMER = [firstName: 'QA', lastName: 'Tester', postalCode: '12190'].asImmutable()
    static final List<String> CATALOG_NAMES = ['Sauce Labs Backpack', 'Sauce Labs Bike Light', 'Sauce Labs Bolt T-Shirt', 'Sauce Labs Fleece Jacket', 'Sauce Labs Onesie', 'Test.allTheThings() T-Shirt (Red)'].asImmutable()
    static final Map TWO_PRODUCT_TOTAL = [subtotal: '39.98', tax: '3.20', total: '43.18'].asImmutable()
}
