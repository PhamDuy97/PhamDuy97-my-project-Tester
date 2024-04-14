package RemunerationManagement;

import org.testng.annotations.Test;

import authenticationMicrosoftBypass.AuthenticationMicrosoftBypass;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ViewADranks {

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
		WebElement ThulaoButton = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[6]/a"));
		ThulaoButton.click();

		// Chờ 1 giây
		Thread.sleep(2000);

		// Click vào tab "Học hàm học vị"
		WebElement importTab = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[6]/ul/li[1]/a/span"));
		importTab.click();

		// Click vào tab "Cấp bậc"
		webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/ul/li[2]/a")).click();

		// Chờ 1 giây
		Thread.sleep(2000);

		// Bấm vào chức năng "lựa chọn hiển thị"
		WebElement dropdown = webDriver.findElement(By.name("tblAcademicDegreeRank_length"));
		Select select = new Select(dropdown);
		select.selectByVisibleText("25");

		// Scroll down multiple times
		for (int i = 0; i < 5; i++) {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("window.scrollBy(0, 200)");
			Thread.sleep(1000); // Add a short delay after each scroll
		}

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
