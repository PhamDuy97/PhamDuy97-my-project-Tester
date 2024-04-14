package TimetableManagement;

import org.testng.annotations.Test;

import authenticationMicrosoftBypass.AuthenticationMicrosoftBypass;

import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DeleteClass {
	
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

		// Click vào tab "Phân Công"
		WebElement importTab = webDriver.findElement(By.xpath("//*[@id=\"main-menu-navigation\"]/li[4]/ul/li[2]/a"));
		importTab.click();

		// Chờ 1 giây
		Thread.sleep(2000);
	    
	 // Nhập dữ liệu vào trường "Chọn học kỳ"
	    WebElement termDropdown = webDriver.findElement(By.xpath("//*[@id=\"select2-term-container\"]"));
	    termDropdown.click();
	    WebElement termInput = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div[1]/div[1]/div/span[2]/span/span[1]/input"));
	    termInput.sendKeys("991");
	    termInput.sendKeys(Keys.ENTER); // Nhấn phím Enter để chọn

	    // Nhập dữ liệu vào trường "Chọn ngành"
	    WebElement majorDropdown = webDriver.findElement(By.xpath("//*[@id=\"select2-major-container\"]"));
	    majorDropdown.click();
	    WebElement majorInput = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/section/div/div/div/div[2]/div[1]/div[2]/div/span[2]/span/span[1]/input"));
	    majorInput.sendKeys("CongNgheThongTin991");
	    majorInput.sendKeys(Keys.ENTER); // Nhấn phím Enter để chọn
	    
	    // Chờ 1 giây
	    Thread.sleep(3000);
	    
	    WebElement lectureDropdown = webDriver.findElement(By.xpath("//*[@id=\"assignLecturerDiv\"]/div[2]/div[1]/div[5]/span[1]/span[1]/span/ul/li/input"));
		lectureDropdown.click();
	    
	    WebElement buttonClear = webDriver.findElement(By.xpath("//*[@id=\"assignLecturerDiv\"]/div[2]/div[1]/div[5]/span[2]/span/div/button[2]"));
	    buttonClear.click();

		// Chờ 1 giây
	    Thread.sleep(3000);
	    
	    WebElement inputField = webDriver.findElement(By.xpath("//*[@id=\"assignLecturerDiv\"]/div[2]/div[1]/div[5]/span[1]/span[1]/span/ul/li/input"));
		inputField.sendKeys("Ung Thị Tường An");
		inputField.sendKeys(Keys.ENTER); // Nhấn phím Enter để chọn
		
		// Click vào tab "lớp GV"
		WebElement emptyLecture = webDriver.findElement(By.xpath("//*[@id=\"303710\"]"));
		emptyLecture.click();

		// Chờ 1 giây
		Thread.sleep(1000);

		// Click vào tab "lớp GV"
		WebElement empty1Lecture = webDriver.findElement(By.xpath("//*[@id=\"303710\"]"));
		empty1Lecture.click();
				
		// Chờ 1 giây
		Thread.sleep(1000);

		// Click vào tab "Chưa phân 2"
		WebElement empty2Lecture = webDriver.findElement(By.xpath("//*[@id=\"303710\"]"));
		empty2Lecture.click();

		// Chờ 1 giây
		Thread.sleep(1000);
		
		//Chọn xóa giảng viên
		WebElement buttonDelete = webDriver.findElement(By.xpath("//button[@class='btn btn-danger waves-effect waves-float waves-light btn-icon btn-delete ms-25']"));
		buttonDelete.click();
		
		// Tìm phần tử <p> chứa văn bản cần lấy mã lớp học phần
		WebElement paragraphElement = webDriver.findElement(By.xpath("//p[@class='text-danger mb-0']"));
																	
		// Lấy văn bản từ phần tử <p>
		String text = paragraphElement.getText();

		// Loại bỏ các ký tự thường và trước dấu phẩy
		String filteredText = text.replaceAll("[a-z.,]+|[H]", "");

		// Tạo biểu thức chính quy để lọc ra các chữ số, ký tự in đậm và ký tự đặc biệt
		Pattern pattern = Pattern.compile("[0-9_\\p{Lu}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\p{Nl}\\p{Nd}\\p{P}\\p{S}]");
		Matcher matcher = pattern.matcher(filteredText);
		StringBuilder stringBuilder = new StringBuilder();
		while (matcher.find()) {
		    stringBuilder.append(matcher.group());
		}

		// Nhập dữ liệu đã lọc vào trường dữ liệu của phần tử <input>
		WebElement inputNameField = webDriver.findElement(By.className("swal2-input"));
		inputNameField.sendKeys(stringBuilder.toString());
		
		//Chọn xác nhận giảng viên
		WebElement button2Delete = webDriver.findElement(By.xpath("/html/body/div[4]/div/div[6]/button[1]"));
		button2Delete.click();
	    
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
