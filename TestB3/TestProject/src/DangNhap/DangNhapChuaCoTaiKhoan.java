package DangNhap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DangNhapChuaCoTaiKhoan {

    public static void main(String[] args) throws InterruptedException {
        // Thiết lập đường dẫn của trình duyệt Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\KoDau\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("http://didoshop.vn/");
        
        // Nhấp nút đăng nhập
        driver.findElement(By.className("btnSignin")).click();
        
        // Nhập thông tin tài khoản không tồn tại
        driver.findElement(By.id("username")).sendKeys("tai.khoan.khong.ton.tai@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("password")).sendKeys("matkhaukhongdung");
        Thread.sleep(2000);
        
        // Nhấp nút đăng nhập
        driver.findElement(By.id("btnSubmit")).click();
        
     // Kiểm tra đăng nhập thành công
        String currentURL = driver.getCurrentUrl();
        if (currentURL.equals("http://didoshop.vn/user/signin")) {
            System.out.println("Tài khoản chưa được tạo. Đăng nhập thất bại!");

         // Kiểm tra kết quả
            String expect_result = currentURL;
            String actual_result = "http://didoshop.vn/user/signin";

            if (expect_result.equals(actual_result)) {
                System.out.println("Kết quả thực tế đúng với kết quả mong đợi");
            } else {
                System.out.println("Kết quả thực tế khác kết quả mong đợi");
                System.out.println("Kết quả mong đợi: " + expect_result);
                System.out.println("Kết quả thực tế: " + actual_result);
            }
        } else {
            System.out.println("Đăng nhập thành công");
        }
        
        // Đóng trình duyệt
        driver.close();
    }
}
