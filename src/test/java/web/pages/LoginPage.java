package web.pages;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Base.WebTestBase;
import com.TestContext.ScenarioContext;

import genericFunctions.ReusableMethods;

public class LoginPage extends WebTestBase{

	public WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user_email_login")
	public WebElement username_TextField;

	@FindBy(id = "user_password")
	public WebElement password_TextField;

	@FindBy(id = "user_submit")
	public WebElement SignIn_Button;
	

	public void launchApplication() {
		try {
			ReusableMethods.launchApplication(driver, ReusableMethods.getGlobalValue("globalUrl"));
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterLoginCreds() throws IOException {
		ReusableMethods.waitForElementToBeClickable(driver, SignIn_Button);
		ReusableMethods.EnterValue(driver, username_TextField, "login");
		ReusableMethods.EnterValue(driver, password_TextField, "password");
	}

	public void clickSignIn() {
		ReusableMethods.click(driver, SignIn_Button);
	}
}
