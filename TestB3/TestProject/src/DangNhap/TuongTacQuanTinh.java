package DangNhap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TuongTacQuanTinh {

    public static void main(String[] args) throws InterruptedException {
        // Thiết lập đường dẫn của trình duyệt Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\KoDau\\chromedriver.exe");
        
        // Khởi tạo trình duyệt Chrome
        WebDriver driver = new ChromeDriver();
        
        // Mở cửa sổ trình duyệt và phóng to toàn màn hình
        driver.manage().window().maximize();
        
        // Mở trang web
        driver.get("http://didoshop.vn/"); // Sử dụng driver.get() thay cho driver.navigate().to()
        
        // Nhấp vào nút đăng nhập
        driver.findElement(By.className("btnSignin")).click();
        
        // Nhập thông tin đăng nhập
        driver.findElement(By.id("username")).sendKeys("phamdyyuu0907@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("password")).sendKeys("phamduy0907");
        Thread.sleep(2000);
        
        // Nhấp vào nút đăng nhập
        driver.findElement(By.id("btnSubmit")).click();
        
        // Kiểm tra đăng nhập thành công
        String currentURL = driver.getCurrentUrl();
        if (currentURL.equals("http://didoshop.vn/")) {
            System.out.println("Đăng nhập thành công!");
            
            // Thực hiện vào trang thông tin cá nhân
            driver.findElement(By.id("account")).click(); // Thêm dấu ngoặc đơn đóng sau id("account")
            driver.findElement(By.cssSelector("a[href*='/profile']")).click();
            
            // Thực hiện cập nhật thông tin cá nhân
            driver.findElement(By.cssSelector("a[rel='nofollow'][href='/profile/edit']")).click();
            Thread.sleep(2000);
                        
            // Tương tác với thành phố và quận
            tươngTácThànhPhốVàQuận(driver, "255", "346"); // Hồ Chí Minh và Quận 8
            Thread.sleep(2000); // Đợi 2 giây trước khi thực hiện tương tác tiếp
            
            tươngTácThànhPhốVàQuận(driver, "269", "646"); // Lào cai và Bát xát
            Thread.sleep(2000); // Đợi 2 giây trước khi thực hiện tương tác tiếp
            
            // Kiểm tra tương tác thành công
            currentURL = driver.getCurrentUrl();
            if (currentURL.equals("http://didoshop.vn/profile/edit")) {
                System.out.println("Tương tác thành công!");
            } else {
                System.out.println("Tương tác thất bại!");
            }
        } else {
            System.out.println("Đăng nhập thất bại!");
        }
        
        // Đóng trình duyệt
        driver.quit(); // Sử dụng driver.quit() để đóng tất cả các cửa sổ trình duyệt
    }
    
    // Hàm tương tác với thành phố và quận
    public static void tươngTácThànhPhốVàQuận(WebDriver driver, String cityValue, String districtValue) {
        // Tìm phần tử select thành phố bằng ID
        WebElement citySelectElement = driver.findElement(By.id("cityId"));
        // Khởi tạo đối tượng Select từ phần tử select thành phố
        Select citySelect = new Select(citySelectElement);
        // Chọn thành phố (ví dụ: Hồ Chí Minh)
        citySelect.selectByValue(cityValue);
        
        // Tìm phần tử select quận bằng tên
        WebElement districtSelectElement = driver.findElement(By.name("districtId"));
        // Khởi tạo đối tượng Select từ phần tử select quận
        Select districtSelect = new Select(districtSelectElement);
        // Chọn quận (ví dụ: Quận 8)
        districtSelect.selectByValue(districtValue);
    }
}
