package DangNhap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class DangNhapThanhCong {

    public static void main(String[] args) throws InterruptedException {
        // Thiết lập đường dẫn của trình duyệt Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\KoDau\\chromedriver.exe");

        // Khởi tạo trình duyệt Chrome
        WebDriver driver = new ChromeDriver();

        // Mở cửa sổ trình duyệt và phóng to toàn màn hình
        driver.manage().window().maximize();

        // Mở trang web
        driver.get("http://didoshop.vn/");

        // Nhấp nút đăng nhập
        driver.findElement(By.className("btnSignin")).click();

        // Nhập thông tin đăng nhập
        driver.findElement(By.id("username")).sendKeys("phamdyyuu0907@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("password")).sendKeys("phamduy0907");
        Thread.sleep(2000);

        // Nhấp nút đăng nhập
        driver.findElement(By.id("btnSubmit")).click();

        // Kiểm tra đăng nhập thành công
        String currentURL = driver.getCurrentUrl();
        if (currentURL.equals("http://didoshop.vn/")) {
            System.out.println("Đăng nhập thành công!");

            // Kiểm tra kết quả
            String expect_result = "http://didoshop.vn/";
            String actual_result = currentURL;

            if (expect_result.equals(actual_result)) {
                System.out.println("Kết quả thực tế đúng với kết quả mong đợi");
            } else {
                System.out.println("Kết quả thực tế khác kết quả mong đợi");
                System.out.println("Kết quả mong đợi: " + expect_result);
                System.out.println("Kết quả thực tế: " + actual_result);
            }
        } else {
            System.out.println("Đăng nhập thất bại!");
        }

        // Đóng trình duyệt
        driver.quit();
    }
}
