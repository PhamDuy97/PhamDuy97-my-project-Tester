package WrongCase;

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

public class SearchKoHienThiKetQua {
	
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
	public void testSearchNonexistentResult() {
	    try {
	        // Bấm vào chức năng "Học kỳ và ngành"
	        webDriver.findElement(By.xpath("//span[contains(text(),'Học kỳ và ngành')]")).click();

	        // Click vào tab "Ngành"
	        webDriver.findElement(By.xpath("//body/div[2]/div[2]/div[3]/div[1]/section[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[2]/a[1]")).click();

	        // Nhập vào trường dữ liệu tìm kiếm tên ngành
	        WebElement inputTimKiem = webDriver.findElement(By.className("form-control"));
	        inputTimKiem.sendKeys("khongtonaiketqua");

	        // Chờ cho thông báo "Không tìm thấy kết quả" xuất hiện
	        WebDriverWait wait = new WebDriverWait(webDriver, 10);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='dataTables_empty' and contains(text(),'Không tìm thấy kết quả')]")));

	        // Kiểm tra xem thông báo "Không tìm thấy kết quả" có hiển thị hay không
	        WebElement messageElement = webDriver.findElement(By.xpath("//td[@class='dataTables_empty']"));
	        String message = messageElement.getText();
	        
	        // Kiểm tra thông báo
	        if (message.contains("Không tìm thấy kết quả")) {
	            System.out.println("Không tìm thấy kết quả!");
	        } else {
	            System.out.println("Thông báo không hiển thị đúng.");
	        }
	    } catch (Exception e) {
	        // Xử lý ngoại lệ nếu có
	        System.out.println("Lỗi: " + e.getMessage());
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
