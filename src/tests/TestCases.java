package tests;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class TestCases {
	WebDriver d;
	Properties properties;
		
	@BeforeMethod
	public void beforeMethod() {
		loadConfigDetails();
		setDriver(properties.getProperty("browser"));
		d.get(properties.getProperty("testURL")); //
		d.manage().window().maximize();
		
	}

	
	@AfterMethod
	public void afterMethod() {
		d.quit();
	}

	public void setDriver(String browser) {
		String proPath = System.getProperty("user.dir");
		switch (browser){
		case "chrome":
			System.setProperty("webdriver.chrome.driver", proPath + properties.getProperty("chromePath"));
			d = new ChromeDriver();
			break;
		case "ie" :
			System.setProperty("webdriver.ie.driver", proPath + properties.getProperty("iePath"));
			d = new InternetExplorerDriver();
			break;
		}
		d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public void loadConfigDetails() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(".//configs//Configuration.properties"));
			properties = new Properties();
			properties.load(reader);
			reader.close();
			System.out.println("##Config Properties loaded");
		} catch (Exception e) {
			System.out.println("##Error when loading Config Pro");
			System.out.println(e);
			
		}
	}

	public void loginToEasy() {
		loadConfigDetails();
		setDriver(properties.getProperty("browser"));
		d.get(properties.getProperty("appURL")); //
		d.findElement(By.id("txtLanId")).sendKeys(properties.getProperty("username"));
		d.findElement(By.id("txtPassword")).sendKeys(properties.getProperty("pasword"));
		d.findElement(By.id("btnlogin")).click();
	}

//------------------------------------------------------------------
	//@Test(enabled = true, priority = 1)
	public void LeaveData() throws InterruptedException {
		loginToEasy();
		System.out.println("LeaveData...");
		Thread.sleep(8000);
		d.findElement(By.xpath(".//a[@class='modalMyDetail']")).click();
		Thread.sleep(5000);
	}
	//@Test(enabled = true, priority = 2, dependsOnMethods= "TimesheetApproval")
	public void LeaveApproval() throws InterruptedException {
		loginToEasy();
		System.out.println("Leave Approval..");
		d.findElement(By.xpath(".//a[@id='PLlink']")).click();
		Thread.sleep(5000);
	}
	//@Test(enabled = true, priority = 3)
	public void TimesheetApproval() throws InterruptedException {
		loginToEasy();
		System.out.println("TimeSheet Approval..");
		d.findElement(By.xpath(".//a[@id='TimesheetApprovallink']")).click();
		Thread.sleep(5000);
	}

//------------------------------------------------------------------	
	@Test
	public void VerifyDatePicker() {
		moveToDatePicker();
		WebElement e = getElement("xpath", ".//a[@id='ui-id-3']");
		e.click();
	}


	@Test 
	public void Selectable() {
		moveToSelectable();
		WebElement e = getElement("xpath", ".//a[@id='ui-id-3']");
		e.click();
	}
	
	@Test (dataProvider = "inputData")
	public void Registration(HashMap<String, String> inputData) {
		moveToRegistrationForm();
		fillRegistrationForm(inputData);
	}

	//------------------------------------------------------------------	
	
	
	
	
public void fillRegistrationForm(HashMap<String, String> inputData) {
		WebElement e = null;
		
		e = getElement("name", "first_name");
		e.sendKeys(inputData.get("firstName"));
		
		e = getElement("name", "last_name");
		e.sendKeys(inputData.get("lastName"));

		String path = "//input[@value='" + inputData.get("maritalStatus") + "']";
		e = getElement("xpath", path);
		e.click();
		
		String hobby = inputData.get("hobby");
		String[] aryHobby = hobby.split("#");
		ArrayList<String> hobbies = new ArrayList<String>(Arrays.asList(aryHobby));
		
		for(String value : hobbies) {
			String path1 = ".//input[@value='" + value + "']";
			e = getElement("xpath", path1);
			e.click();
		}
		
		Select s = new Select(getElement("id", "dropdown_7"));
		s.selectByVisibleText(inputData.get("country"));

		
	}


@DataProvider(name = "inputData")
public Object[][] dataProvider()
{
	Object[][] obj = new Object[1][1];
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("firstName", "Vinod");
	map.put("lastName", "Bhavsar");
	map.put("maritalStatus", "married");
	map.put("hobby", "dance#reading");
	map.put("country", "India");


	/*HashMap<String, String> map2 = new HashMap();
	map2.put("firstName", "abc");
	map2.put("lastName", "xyz");
	map2.put("maritalStatus", "single");
	map2.put("hobby", "dance|reading");
	map.put("country", "India");

	obj[0][0]=map;
	obj[1][0]=map2;
	*/
	obj[0][0]=map;
	
	return obj;
}

	
public void moveToSelectable() {
	mouseOverOn("DemoSites");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	clickAndMoveTo("Basic Demo Site");
	clickOnLink("Selectable"); 
	
}
	


public WebElement getElement(String locator, String value) {
		WebElement e = null;
		WebDriverWait wait = new WebDriverWait(d, 20);
		switch (locator) {
		case "id":
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(value)));
			break;

		case "xpath":
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value)));
			break;
		
		case "name":
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(value)));
			break;
		}
		return e;
	}

public void moveToRegistrationForm() {
		mouseOverOn("DemoSites");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickAndMoveTo("Basic Demo Site");
		clickOnLink("Registration"); //Registration
	}


public void moveToDatePicker() {
	
	mouseOverOn("DemoSites");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	clickAndMoveTo("Basic Demo Site");
	clickOnLink("Datepicker"); 
	
}


	public void clickOnLink(String lnkName) {
		d.findElement(By.xpath(".//a[text() = '" + lnkName + "']")).click();
	}


	public void clickAndMoveTo(String lnkName) {
		WebElement e = getElement("xpath", ".//span[@class='text-wrap']//span[@class='menu-text'][text()='" + lnkName + "']");
		e.click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		Set<String> handles = d.getWindowHandles();
		String currHandle = d.getWindowHandle();
		handles.remove(currHandle);
		for(String h : handles) {
			d.switchTo().window(h);
		}
	}


	public void mouseOverOn(String lnkName) {
		WebElement e = getElement("xpath", ".//span[@class='text-wrap']//span[@class='menu-text'][text()='DEMO SITES']");
		Actions a = new Actions(d);
		a.moveToElement(e).build().perform();
}
	
	
	
	
	


	
}
