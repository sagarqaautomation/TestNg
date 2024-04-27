package genericFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.gherkin.model.Scenario;

public class ReusableMethods {
	
	static WebDriverWait wait;
	static WebElement webElement;
	static Actions objAction;
	static String filePath = "/src/test/resources/GlobalTestData/globaldata.properties";

	public static void launchApplication(WebDriver driver, String Url) {
		driver.get(Url);
	}

	public static void quit(WebDriver driver) {
		driver.quit();
	}

	public static void NavigateApplication(WebDriver driver, String Url) {
		driver.navigate().to(Url);
	}

	public static void reload(WebDriver driver) {
		driver.navigate().refresh();
	}

	public static void EnterValue(WebDriver driver, WebElement element, String value) {
		try {
			waitForElement(driver, element);
			element.sendKeys(value);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void EnterValue(WebDriver driver, By element, String value) {
		try {
			waitForElement(driver, element);
			driver.findElement(element).sendKeys(value);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void click(WebDriver driver, WebElement element) {
		try {
			waitForElement(driver, element);
			element.click();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void click(WebDriver driver, By element) {
		try {

			driver.findElement(element).click();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static String getTtitle(WebDriver driver) {
		return driver.getTitle();	
	}
	

	public static void click_javaScript(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public static void click(List<WebElement> element) {
		try {
			Thread.sleep(2000);
			element.get(0).click();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void getListOfElements(List<WebElement> element, int expectedListCount) throws Exception {
		try {
			if (getListofElementsCount(element) > 0 && getListofElementsCount(element) == expectedListCount) {
				element.stream()
						.forEach(listOfElments -> ExtentCucumberAdapter.addTestStepLog(listOfElments.getText()));
			} else {
				throw new Exception("List of elements are not matched or list of elements are not displayed");
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static int getListofElementsCount(List<WebElement> element) {
		System.out.println(element.size());
		return element.size();
	}

	public static void compareList(List<WebElement> element, List<String> actualList) throws Exception {
		List<String> listOfElements = new ArrayList<String>();
		try {
			for (WebElement e : element) {
				listOfElements.add(e.getText().replace("\n", " "));
			}
			listOfElements.removeAll(Arrays.asList(" ", "", null));
			System.out.println(listOfElements);
			System.out.println(actualList);
			Assert.assertTrue(listOfElements.equals(actualList), "Expected element does not match with Actual element");
		} catch (Exception e) {
			List<String> unMatchedData = listOfElements.stream().filter(d -> !actualList.contains(d))
					.collect(Collectors.toList());
			ExtentCucumberAdapter.addTestStepLog("Expected list does not match with actual list : " + unMatchedData);
			Assert.fail("Expected list does not match with actual list : " + unMatchedData);
		}

	}

	public static void validateList(List<String> actualList, List<String> expectedList) {
		try {
			expectedList = new ArrayList<>(expectedList);
			Collections.sort(expectedList);
			Collections.sort(actualList);
			System.out.println(actualList);
			System.out.println(expectedList);

			Assert.assertTrue(expectedList.equals(actualList), "Expected element does not match with Actual element");
		} catch (Exception e) {
			List<String> unMatchedData = expectedList.stream().filter(d -> !actualList.contains(d))
					.collect(Collectors.toList());
			ExtentCucumberAdapter.addTestStepLog("Expected list does not match with actual list : " + unMatchedData);
			Assert.fail("Expected list does not match with actual list : " + unMatchedData);
		}

	}

	public static List<String> getListofElements(List<WebElement> element) throws Exception {
		List<String> listOfElements = null;
		try {
			listOfElements = new ArrayList<String>();
			for (WebElement e : element) {
				listOfElements.add(e.getText().replace("\n", " "));

			}
			System.out.println(listOfElements);
		} catch (Exception e) {

			Assert.fail("list not matches" + listOfElements);
		}
		return listOfElements;

	}

	public static String GetTextData(WebDriver driver, By element) {
		try {
			return driver.findElement(element).getText().trim();
		} catch (Exception ex) {
			Assert.fail("Unable to get text from the element || Exception Message: " + ex.getMessage());
			throw ex;
		}
	}

	public static List<String> getListofElements(WebDriver driver, By element) {
		List<String> listOfElements = new ArrayList<String>();
		try {
			List<WebElement> values = driver.findElements(element);
			for (WebElement e : values) {
				listOfElements.add(e.getText().replace("\n", " "));
			}
		} catch (Exception e) {
			Assert.fail("list not matches" + listOfElements);
		}
		return listOfElements;
	}

	public static void waitForElement(WebDriver driver, WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(45));
			wait.until(x -> element).isDisplayed();
		} catch (NoSuchElementException ex) {
			Assert.fail("No such element is found on the webpage. || Exception Message :" + ex.getMessage());
		} catch (StaleElementReferenceException ex) {
			Assert.fail("Element is not available || Exception Message :" + ex.getMessage());
		} catch (Exception ex) {
			Assert.fail("Expected element is not loaded|| Exception Message: " + ex.getMessage());
		} finally {
			wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		}
	}

	public static boolean waitForElementToBeDisplayed(WebElement element, final int timeout, WebDriver driver) {
		boolean status = false;
		try {
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			driverWait.until(ExpectedConditions.visibilityOf(element));
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(45));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (NoSuchElementException ex) {
			Assert.fail("No such element is found on the webpage. || Exception Message :" + ex.getMessage());
		} catch (StaleElementReferenceException ex) {
			Assert.fail("Element is not available || Exception Message :" + ex.getMessage());
		} catch (Exception ex) {
			Assert.fail("Expected element is not loaded|| Exception Message: " + ex.getMessage());
		} finally {
			wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		}
	}

	public static void waitForElement(WebDriver driver, By element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(45));
			wait.until(x -> driver.findElement(element)).isDisplayed();
		} catch (NoSuchElementException ex) {
			Assert.fail("No such element is found on the webpage. || Exception Message :" + ex.getMessage());
		} catch (StaleElementReferenceException ex) {
			Assert.fail("Element is not available || Exception Message :" + ex.getMessage());
		} catch (Exception ex) {
			Assert.fail("Expected element is not loaded|| Exception Message: " + ex.getMessage());
		} finally {
			wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		}
	}

	public static void fluentWait(WebDriver driver, WebElement element) {
		Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class)
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class)
				.ignoring(Exception.class);
		fluentWait.until(d -> element).isDisplayed();
	}

	public static String getCurrentURL(WebDriver driver) {
		waitforPageLoad(driver);
		return driver.getCurrentUrl();
	}

	public static boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isEnabled(WebElement element) {
		try {
			return element.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public static void ScrollToElement_JavScript(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			js.executeScript("window.scrollBy(0,document.body.scrollHeight");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void scrollToBottom(WebDriver driver) {
		try {
			objAction = new Actions(driver);
			objAction.sendKeys(Keys.PAGE_DOWN).build().perform();
		} catch (Exception ex) {
			Assert.fail("Unable to Scroll to Element " + ex.getMessage());
		}
	}

	public static void ScrollToElement(WebDriver driver, WebElement element) {
		try {
			objAction = new Actions(driver);
			objAction.moveToElement(element);
			objAction.perform();
		} catch (Exception ex) {
			Assert.fail("Unable to Scroll to Element " + element + "|| Exception Message:" + ex.getMessage());
		}
	}

	public static void ScrollToElement(WebDriver driver, List<WebElement> element) {
		try {
			objAction = new Actions(driver);
			objAction.moveToElement(element.get(0));
			objAction.perform();
		} catch (Exception ex) {
			Assert.fail("Unable to Scroll to Element " + element + "|| Exception Message:" + ex.getMessage());
		}
	}

	public static void moveToElement(WebDriver driver, WebElement element) {
		try {
			objAction = new Actions(driver);
			objAction.moveToElement(element).click().perform();
		} catch (Exception ex) {
			Assert.fail("Unable to move to Element " + element + "|| Exception Message:" + ex.getMessage());
		}
	}

	public static void moveToElement(WebDriver driver, By element) {
		try {
			objAction = new Actions(driver);
			objAction.moveToElement(driver.findElement(element)).click().perform();
		} catch (Exception ex) {
			Assert.fail("Unable to move to Element " + element + "|| Exception Message:" + ex.getMessage());
		}
	}

	public static void ScrollToElement_Mobile(WebDriver driver, WebElement element) {
		try {
			waitForElement(driver, element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("direction", "down");
			js.executeScript("mobile: scroll", scrollObject);
		} catch (Exception e) {
			Assert.fail("unable to scroll the element" + e.getMessage());
		}
	}

	public static void clickUsingTab(WebDriver driver) {
		try {
			objAction = new Actions(driver);
			objAction.sendKeys(Keys.TAB);
		} catch (Exception ex) {
			Assert.fail("Unable to click on Tab || Exception Message:" + ex.getMessage());
		}
	}

	public void ScrollToBottomUsingJavaScript(WebDriver driver) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception ex) {
			System.out.println(
					"Unable to scroll page to the bottom using javascript || Exception Message: " + ex.getMessage());
		}

	}

	public static void swithToIFrame(WebDriver driver, WebElement element) {
		try {
			driver.switchTo().frame(element);
		} catch (NoSuchFrameException ex) {
			Assert.fail("Unable to locate frame with element " + element + " || Execption Message " + ex.getMessage());
		} catch (StaleElementReferenceException ex) {
			Assert.fail("Element with " + element + " is not attached to the webpage || Exception Message :"
					+ ex.getMessage());
		} catch (Exception ex) {
			Assert.fail("Not switched to frame || Exception Message: " + ex.getMessage());
		}
	}

	public static void swithToIFrame(WebDriver driver, String frameName) {
		try {
			driver.switchTo().frame(frameName);
		} catch (NoSuchFrameException ex) {
			Assert.fail("Unable to locate frame with name " + frameName + " || Execption Message " + ex.getMessage());
		} catch (StaleElementReferenceException ex) {
			Assert.fail("Name with" + frameName + " is not attached to the webpage || Exception Message :"
					+ ex.getMessage());
		} catch (Exception ex) {
			Assert.fail("Not switched to frame || Exception Message: " + ex.getMessage());
		}
	}

	public static void swithToIFrame(WebDriver driver, int frameIndex) {
		try {
			driver.switchTo().frame(frameIndex);
		} catch (NoSuchFrameException ex) {
			Assert.fail(
					"Unable to locate frame with index " + frameIndex + " || Execption Message :" + ex.getMessage());
		}

		catch (StaleElementReferenceException ex) {
			Assert.fail("Frame index " + frameIndex + " is not attached to the webpage || Exception Message :"
					+ ex.getMessage());
		} catch (Exception ex) {
			Assert.fail("Not switched to frame || Exception Message: " + ex.getMessage());
		}
	}

	public static void switchBackFromIFrame(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			Assert.fail("Did not switch back from frame || Exception Message: " + ex.getMessage());
		}
	}

	public static String GetTextData(WebElement element) {
		try {
			return element.getText().trim();
		} catch (Exception ex) {
			Assert.fail("Unable to get text from the element || Exception Message: " + ex.getMessage());
			throw ex;
		}
	}

	public static String GetPlaceholderText(WebElement element) {
		String strPlaceholderText = null;
		try {
			strPlaceholderText = element.getAttribute("placeholder");
		} catch (Exception ex) {
			Assert.fail("Unable to get placeholder text || Exception Message: " + ex.getMessage());
		}
		return strPlaceholderText;
	}

	public static String GetValueByAttribute(WebElement element, String attributeName) {
		String attributevalue = null;
		try {
			attributevalue = element.getAttribute(attributeName);
		} catch (Exception ex) {
			Assert.fail("Unable to get the attribute value || Exception Message :" + ex.getMessage());
		}
		return attributevalue;
	}

	public static String GetCSSValue(WebElement element, String attributeName) {
		String attributevalue = null;
		try {
			attributevalue = element.getCssValue(attributeName);
		} catch (Exception ex) {
			Assert.fail("Unable to get the attribute value || Exception Message :" + ex.getMessage());
		}
		return attributevalue;
	}

	public Boolean VerifyText(WebElement element, String expectedText) {
		String actualText = GetTextData(element);
		return actualText == expectedText;
	}

	public void AcceptAlert(WebDriver driver) {
		try {
			driver.switchTo().alert().accept();
		} catch (Exception ex) {
			Assert.fail("Unable to accept alert || Exception Message: " + ex.getMessage());
		}
	}

	public void DismissAlert(WebDriver driver) {
		try {
			driver.switchTo().alert().dismiss();
		} catch (Exception ex) {
			Assert.fail("Unable to dismiss alert || Exception Message: " + ex.getMessage());
		}
	}

	public void AlertEnterText(WebDriver driver, String strText) {
		try {
			driver.switchTo().alert().sendKeys(strText);
		} catch (Exception ex) {
			Assert.fail("Unable to enter text in alert || Exception Message: " + ex.getMessage());
		}
	}

	public static void ClearField_Backspace(WebElement element) {
		try {
			element.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
		} catch (Exception ex) {
			Assert.fail("Unable to Clear the Text || Exception Message: " + ex.getMessage());
		}
	}

	public static void ClearField(WebElement element, int index) {
		try {
			for (int i = 0; i < index; i++) {
				element.sendKeys(Keys.BACK_SPACE);
			}
		} catch (Exception ex) {
			Assert.fail("Unable to Clear the Text || Exception Message: " + ex.getMessage());
		}
	}

	public static void ClearAndEnterValue(WebDriver driver, WebElement element, String strText) {
		waitForElement(driver, element);
		element.clear();
		EnterValue(driver, element, strText);
	}

	public boolean IsCheckBoxChecked(WebElement element) {
		if (element.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	public void DropDownSelect_Text(WebElement element, String strText) {
		try {
			Select objSElement = new Select(element);
			objSElement.selectByVisibleText(strText);
		} catch (Exception ex) {
			Assert.fail("Unable to Select the dorpdown text : " + strText + " || Execption Message" + ex.getMessage());
		}
	}

	public void DropDownSelect_Value(WebElement element, String strText) {
		try {
			Select objSElement = new Select(element);
			objSElement.selectByValue(strText);
		} catch (Exception ex) {
			Assert.fail("Unable to Select the dorpdown text : " + strText + " || Execption Message" + ex.getMessage());
		}
	}

	public void DropDownSelect_Index(WebElement element, int index) {
		try {
			Select objSElement = new Select(element);
			objSElement.selectByIndex(index);
		} catch (Exception ex) {
			Assert.fail("Unable to Select the dorpdown text : " + index + " || Execption Message" + ex.getMessage());
		}
	}

	public static void SelectCheckbox(WebElement element) {
		try {
			if (element.isSelected() != true) {
				element.click();
			}
		} catch (Exception ex) {
			Assert.fail("Unable to Select the dorpdown text : " + element + " || Execption Message" + ex.getMessage());
		}
	}

	public static boolean isSelected(WebElement element) {
		try {
			return element.isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	public static void SelectRadioButton(WebElement element) {
		try {
			if (element.isSelected() != true) {
				element.click();
			}
		} catch (Exception ex) {
			Assert.fail("Unable to Select the dorpdown text : " + element + " || Execption Message" + ex.getMessage());
		}
	}

	public void DeSelectCheckbox(WebElement element) {
		try {
			if (element.isSelected() != false) {
				element.click();
			}
		} catch (Exception ex) {
			Assert.fail(
					"Unable to Deselect checkbox with Element " + element + "|| Execption Message:" + ex.getMessage());
		}
	}

	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + filePath);
		prop.load(fis);
		return prop.getProperty(key);
	}



	public static void setGlobalValue(String key, String data) throws IOException {
		Properties configProperty = new Properties();
		File file = new File(System.getProperty("user.dir") + filePath);
		FileInputStream fis = new FileInputStream(file);
		configProperty.load(fis);
		configProperty.setProperty(key, data);
		FileOutputStream fileOut = new FileOutputStream(file);
		configProperty.store(fileOut, "sample properties");
	}


	public static String generateRandomValues(String randomType, int lengthCount) {
		int index;
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";
		String numbers = "0123456789";
		String alphaNumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(lengthCount);
		for (int i = 0; i < lengthCount; i++) {
			if (randomType.contains("alphabets")) {
				index = (int) (alphabets.length() * Math.random());
				sb.append(alphabets.charAt(index));
			} else if (randomType.contains("numbers")) {
				index = (int) (numbers.length() * Math.random());
				sb.append(numbers.charAt(index));
			} else {
				index = (int) (alphaNumeric.length() * Math.random());
				sb.append(alphaNumeric.charAt(index));
			}
		}

		return sb.toString();
	}

	public static void waitforPageLoad(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return document.readyState").toString().equals("complete");
		Long loadtime = (Long)((JavascriptExecutor)driver).executeScript(
			    "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
		ExtentCucumberAdapter.addTestStepLog(loadtime.toString());
	}

	public static void Sleep(int number) throws InterruptedException {
		Thread.sleep(number * 1000);
	}

	public static String capitalizeFirstLetter(String str) {
		String words[] = str.split("\\s");
		String capitalizeWord = "";
		for (String w : words) {
			String first = w.substring(0, 1);
			String afterfirst = w.substring(1);
			capitalizeWord += first.toUpperCase() + afterfirst + " ";
		}
		return capitalizeWord.trim();
	}

	public static File screenshotFileName() {
		Date currentDate = new Date();
		String FileName = currentDate.toString().replace(":", "_").replace(" ", "_") + ".png";
		File destinationPath = new File(System.getProperty("user.dir") + "/Screenshot/" + FileName);
		return destinationPath;
	}

	public static byte[] getByteScreenshot(WebDriver driver) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		byte[] fileContent = FileUtils.readFileToByteArray(src);
		return fileContent;
	}

	public static void checkUnMatchedData(List<String> expectedData, List<String> actualData) {
		List<String> unMatchedData = expectedData.stream().filter(d -> !actualData.contains(d))
				.collect(Collectors.toList());
		if (unMatchedData.size() != 0) {
			ExtentCucumberAdapter.addTestStepLog("UnMatchedData : " + unMatchedData);
		}

	}

	public static String getCurrentDate(String pattern) {
		DateTimeFormatter currentDatePattern = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime getCurrentDataNow = LocalDateTime.now();
		return currentDatePattern.format(getCurrentDataNow);
	}
	
	public static String AddPlusToCurrentDate(int days)
	{
		LocalDate currentDate=LocalDate.now();
		LocalDate addDays=currentDate.plusDays(days);
		String [] splitDate=addDays.toString().split("-");
		String actualDate=splitDate[2].replaceAll("^0+(?!$)", "");
		return actualDate;
	}
	
	public static String AddMinusToCurrentDate(int days)
	{
		LocalDate currentDate=LocalDate.now();
		LocalDate addDays=currentDate.minusDays(days);
		String [] splitDate=addDays.toString().split("-");
		String actualDate=splitDate[2].replaceAll("^0+(?!$)", "");
		return actualDate;
	}
	
	public static int calculateAge(String date) {
	int age = 0;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	LocalDate actualDate = LocalDate.parse(date, formatter);
	LocalDate curDate = LocalDate.now();
	if (actualDate != null) {
		age = Period.between(actualDate, curDate).getYears();
	}
	System.out.println(age);
	return age;
}
}