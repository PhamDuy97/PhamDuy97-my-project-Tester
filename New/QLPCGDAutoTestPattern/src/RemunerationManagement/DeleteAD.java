package RemunerationManagement;

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

public class DeleteAD {

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
	public void testAddUserFunctionality() throws InterruptedException {

		// Bấm vào chức năng "Thù Lao"
		WebElement tkbButton = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[6]/a"));
		tkbButton.click();

		// Chờ 1 giây
		Thread.sleep(2000);

		// Click vào tab "Học hàm học vị"
		WebElement importTab = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[6]/ul/li[1]/a/span"));
		importTab.click();

		// Chờ 1 giây
		Thread.sleep(2000);

		// Nhập vào trường dữ liệu tìm kiếm tên ngành
		WebElement inputTimKiem = webDriver.findElement(By.className("form-control"));
		inputTimKiem.sendKeys("991");

		//xóa HH,HV được tìm thấy 
	    webDriver.findElement(By.xpath("//*[@id=\"tblAcademicDegree\"]/tbody/tr/td[5]/a[2]")).click();
        
	 // Chờ 1 giây
	 		Thread.sleep(1000);
	 		
        webDriver.findElement(By.xpath("/html/body/div[3]/div/div[6]/button[1]")).click();	       

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
