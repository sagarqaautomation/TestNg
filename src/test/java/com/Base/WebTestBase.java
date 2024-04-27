package com.Base;

import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import genericFunctions.ReusableMethods;

public class WebTestBase {

	public static WebDriver driver;

	String downloadPath = System.getProperty("user.dir") + "/Downloads";

	@BeforeTest
	public void start() {
		try {
			String browser = ReusableMethods.getGlobalValue("browser");
			switch (browser) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				HashMap<String, Object> prefs = new HashMap<>();
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("plugins.always_open_pdf_externally", true);
				prefs.put("download.default_directory", downloadPath);
				chromeOptions.setAcceptInsecureCerts(true);
				chromeOptions.setExperimentalOption("prefs", prefs);
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				chromeOptions.addArguments("--remote-allow-origins=*");
				// chromeOptions.addArguments("--headless");
				chromeOptions.setBrowserVersion("114");
				chromeOptions.addArguments("--window-size=1920x1080");
				chromeOptions.addArguments("--no-sandbox");
				driver = new ChromeDriver(chromeOptions);
				break;
			case "firefox":
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setAcceptInsecureCerts(true);
				// options.addArguments("--disable-notifications");
				driver = new FirefoxDriver(firefoxOptions);
				break;
			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.setAcceptInsecureCerts(true);
				// options.addArguments("--disable-notifications");
				driver = new EdgeDriver();
				break;
			default:
				throw new Exception("Browser implementation not found" + browser);
			}
		} catch (Exception e) {
			Assert.fail("Browser value is incorrect" + e.getMessage());
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
	}

	public void teardown() {
		driver.quit();
	}

}
