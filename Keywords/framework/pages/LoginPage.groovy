package framework.pages
import framework.core.Ui
import framework.core.AppConfig

class LoginPage {
	static void submit(String username, String password) {
		Ui.fill('Login/input_username', username)
		Ui.fill('Login/input_password', password)
		Ui.click('Login/button_login')
	}
	static void loginValid() {
		submit(AppConfig.username, AppConfig.password)
		Ui.route('inventory.html')
	}
}
