package TimetableManagement;

import org.testng.annotations.Test;

import authenticationMicrosoftBypass.AuthenticationMicrosoftBypass;

import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ViewPersonalTimetable {

	private final static String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
	private final static String WEBDRIVER_CHROME_DRIVER_PATH = "C:\\KoDau\\chromedriver.exe";

	private WebDriver webDriver;


	@BeforeClass
	public void setUp() throws InterruptedException {
		System.setProperty(WEBDRIVER_CHROME_DRIVER, WEBDRIVER_CHROME_DRIVER_PATH);
		AuthenticationMicrosoftBypass authenticationMicrosoftBypass = new AuthenticationMicrosoftBypass();
		int waitingTime = 15;
		long timeWaitToAuthetication = waitingTime * 1000;		

		if(authenticationMicrosoftBypass.getCookiesFromPropertiesFile() == null) {
			authenticationMicrosoftBypass.setCookiesToPropertiesFile(getCookiesFromfirstLogin(timeWaitToAuthetication));			
		}

		String cookies = authenticationMicrosoftBypass.getCookiesFromPropertiesFile();

		webDriver = loginToWebsite(cookies, authenticationMicrosoftBypass);
	}


	@Test
	public void testAddUserFunctionality() throws AWTException, InterruptedException {
		// Bấm vào chức năng "TKB"
		WebElement tkbButton = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[4]/a/span"));
		tkbButton.click();
		
		// Chờ 1 giây
				Thread.sleep(2000);

		// Click vào tab "Thời khóa biểu"
		WebElement importTab = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[4]/ul/li[3]/a/span"));
		importTab.click();
		
		// Chờ 1 giây
				Thread.sleep(2000);

		// Nhập dữ liệu vào trường "Chọn học kỳ"
		WebElement termDropdown = webDriver.findElement(By.xpath("//*[@id=\"select2-term-container\"]"));
		termDropdown.click();
		WebElement termInput = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div[1]/div[1]/div/span[2]/span/span[1]/input"));
		termInput.sendKeys("991");
		termInput.sendKeys(Keys.ENTER); // Nhấn phím Enter để chọn
		
		// Chờ 1 giây
				Thread.sleep(2000);

		// Nhập dữ liệu vào trường "Chọn tuần"
		WebElement weekDropdown = webDriver.findElement(By.xpath("//*[@id=\"select2-week-container\"]"));
		weekDropdown.click();
		WebElement weekInput = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div[1]/div[2]/div/span[2]/span/span[1]/input"));
		weekInput.sendKeys("Tuần 8");
		weekInput.sendKeys(Keys.ENTER); // Nhấn phím Enter để chọn

		// Chờ 1 giây
		Thread.sleep(3000);
		
		// Nhập dữ liệu vào trường "Chọn GV"
		WebElement lectureDropdown = webDriver.findElement(By.xpath("//*[@id=\"select2-lecturer-container\"]"));
		lectureDropdown.click();
		WebElement lectureInput = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div[1]/div[3]/div/span[2]/span/span[1]/input"));
		lectureInput.sendKeys("Nguyễn Phạm Hoang Duy");
		lectureInput.sendKeys(Keys.ENTER); // Nhấn phím Enter để chọn

		// Chờ 1 giây
		Thread.sleep(3000);

		

	}

	@AfterClass
	public void afterClass() {

	}

	private String getCookiesFromfirstLogin(long timeToAuthentication) throws InterruptedException {
		WebDriver webDriver = new ChromeDriver();
		AuthenticationMicrosoftBypass authenticationMicrosoftBypass = new AuthenticationMicrosoftBypass();
		String[] usernamePassword = authenticationMicrosoftBypass.getUsernameAndPasswordFromPropertiesFile();

		webDriver.manage().window().maximize();
		webDriver.navigate().to("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/Account/Login");

		webDriver.findElement(By.id("details-button")).click();
		Thread.sleep(3000);
		webDriver.findElement(By.id("proceed-link")).click();
		Thread.sleep(5000);

		webDriver.findElement(By.id("idSIButton9")).click();

		Thread.sleep(timeToAuthentication);
		webDriver.findElement(By.id("idBtn_Back")).click();

		String cookies = webDriver.manage().getCookies().toString();

		webDriver.close();

		return cookies;
	}

	private WebDriver loginToWebsite(String cookies, AuthenticationMicrosoftBypass authenticationMicrosoftBypass) throws InterruptedException {
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.navigate().to("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/Account/Login");


		webDriver.findElement(By.id("details-button")).click();
		Thread.sleep(1500);
		webDriver.findElement(By.id("proceed-link")).click();
		Thread.sleep(1500);

		String[] cookiesArray = cookies.split(";");
		String[] cookiesPairs = cookiesArray[0].split("=");
		String cookiesName = cookiesPairs[0].replace("[", "");

		webDriver.manage().addCookie(new Cookie(cookiesName, cookiesPairs[1]));

		webDriver.navigate().refresh();

		return webDriver;	
	}


}
