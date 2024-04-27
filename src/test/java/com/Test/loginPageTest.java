package com.Test;

import java.io.IOException;
import org.testng.annotations.Test;
import com.Base.WebTestBase;
import web.pages.LoginPage;

public class loginPageTest extends WebTestBase{
	
	
	@Test
	public void launchURL() throws IOException
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.launchApplication();
		loginPage.enterLoginCreds();
	}

}
