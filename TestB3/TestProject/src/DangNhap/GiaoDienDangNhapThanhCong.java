package DangNhap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GiaoDienDangNhapThanhCong {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\KoDau\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("http://didoshop.vn/");
        
        // Nhấp nút đăng nhập
        driver.findElement(By.className("btnSignin")).click();

        // Kiểm tra xem đã chuyển đến giao diện đăng nhập hay không
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("signin")) {
            System.out.println("Chuyển đến giao diện đăng nhập thành công!");
            
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
            System.out.println("Chuyển đến giao diện đăng nhập thất bại!");
        }
        
        // Đóng trình duyệt
        driver.close();
    }
}
