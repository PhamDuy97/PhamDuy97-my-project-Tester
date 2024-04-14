package LogInLogOut;

import org.testng.annotations.Test;

import authenticationMicrosoftBypass.AuthenticationMicrosoftBypass;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogOut {
	
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
	public void testAddUserFunctionality() {
		// Tìm phần tử chứa nút đăng xuất dựa trên class
		WebElement dropdownUserLink = webDriver.findElement(By.className("dropdown-user-link"));

		// Nhấn vào phần tử đã tìm được để mở dropdown
		dropdownUserLink.click();

		// Đợi cho dropdown xuất hiện
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu dropdown-menu-end show']")));

		// Tìm và nhấn lựa chọn đăng xuất dựa trên văn bản hoặc class
		WebElement logoutOption = webDriver.findElement(By.xpath("//a[contains(text(),'Đăng xuất')]"));
		logoutOption.click();
    
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
