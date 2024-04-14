package WrongCase;

import org.testng.annotations.Test;

import authenticationMicrosoftBypass.AuthenticationMicrosoftBypass;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateDaTonTai {
    
    private final static String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    private final static String WEBDRIVER_CHROME_DRIVER_PATH = "C:\\KoDau\\chromedriver.exe";
    
    private WebDriver webDriver;
    
    
    @BeforeClass
    public void setUp() throws InterruptedException {
        System.setProperty(WEBDRIVER_CHROME_DRIVER, WEBDRIVER_CHROME_DRIVER_PATH);
        AuthenticationMicrosoftBypass authenticationMicrosoftBypass = new AuthenticationMicrosoftBypass();
        int waitingTime = 15;
        long timeWaitToAuthentication = waitingTime * 1000;        

        if(authenticationMicrosoftBypass.getCookiesFromPropertiesFile() == null) {
            authenticationMicrosoftBypass.setCookiesToPropertiesFile(getCookiesFromfirstLogin(timeWaitToAuthentication));            
        }
        
        String cookies = authenticationMicrosoftBypass.getCookiesFromPropertiesFile();
        
        webDriver = loginToWebsite(cookies, authenticationMicrosoftBypass);
    }

    
    @Test
    public void testAddUserFunctionalityWithExistingName() {
        // Bấm vào chức năng "Học kỳ và ngành"
        webDriver.findElement(By.xpath("//span[contains(text(),'Học kỳ và ngành')]")).click();

        // Click vào tab "Ngành"
        webDriver.findElement(By.xpath("//body/div[2]/div[2]/div[3]/div[1]/section[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[2]/a[1]")).click();
        
        // Click vào nút "Thêm mới"
        webDriver.findElement(By.xpath("//body[1]/div[2]/div[2]/div[3]/div[1]/section[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]/span[1]")).click();
        
        // Nhập ID
        webDriver.findElement(By.id("id")).sendKeys("222");
        
        // Nhập tên ngành đã tồn tại
        String existingName = "Công Nghệ thông tin"; // Giả sử tên ngành đã tồn tại là "Công Nghệ thông tin"
        webDriver.findElement(By.id("name")).sendKeys(existingName);
        
        // Nhập viết tắt
        webDriver.findElement(By.id("abbreviation")).sendKeys("CNTT");
        
        // Chọn loại chương trình 
        webDriver.findElement(By.className("select2-selection__placeholder")).click();
        WebElement optionChuongTrinhDaoTao = webDriver.findElement(By.xpath("//li[text()='Tiêu chuẩn']"));
        optionChuongTrinhDaoTao.click();

        // Click nút "Lưu"
        webDriver.findElement(By.xpath("//button[contains(text(),'Lưu')]")).click();

        // Kiểm tra xem có hiển thị thông báo lỗi hay không
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("swal2-error")));
        Assert.assertTrue(errorElement.isDisplayed(), "Error message is not displayed when using existing major name.");
        
        // Trích xuất nội dung của thông báo
        String errorMessage = webDriver.findElement(By.className("swal2-html-container")).getText();
        Assert.assertEquals(errorMessage, "Mã ngành này đã tồn tại!", "Error message text is incorrect.");

        // Click nút "OK" để đóng thông báo
        webDriver.findElement(By.className("swal2-confirm")).click();
    }

    @AfterClass
    public void afterClass() {
        // Đóng trình duyệt sau khi hoàn thành
        if (webDriver != null) {
            webDriver.quit();
        }
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
