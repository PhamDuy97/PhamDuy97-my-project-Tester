package DangNhap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TuongTacQuanHuyenKQ {

    public static void main(String[] args) throws InterruptedException {
        // Thiết lập đường dẫn của trình duyệt Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\KoDau\\chromedriver.exe");

        // Khởi tạo trình duyệt Chrome
        WebDriver driver = new ChromeDriver();

        // Mở cửa sổ trình duyệt và phóng to toàn màn hình
        driver.manage().window().maximize();

        // Mở trang web
        driver.get("http://didoshop.vn/");

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
            driver.findElement(By.id("account")).click();
            driver.findElement(By.cssSelector("a[href*='/profile']")).click();

            // Thực hiện cập nhật thông tin cá nhân
            driver.findElement(By.cssSelector("a[rel='nofollow'][href='/profile/edit']")).click();
            Thread.sleep(2000);

            // Tương tác với thành phố và quận
            tươngTácThànhPhốVàQuận(driver, "255", "350"); // Hồ Chí Minh và Quận 8
            Thread.sleep(2000);

            tươngTácThànhPhốVàQuận(driver, "269", "646"); // Lào Cai và Huyện Bát Xát
            Thread.sleep(2000);

         // Kiểm tra kết quả thực tế
            kiemTraKetQuaThucTe(driver);

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
        driver.close();
    }

    // Hàm tương tác với thành phố và quận
    public static void tươngTácThànhPhốVàQuận(WebDriver driver, String cityValue, String districtValue) {
        // Tìm phần tử select thành phố bằng ID
        WebElement citySelectElement = driver.findElement(By.id("cityId"));
        // Khởi tạo đối tượng Select từ phần tử select thành phố
        Select citySelect = new Select(citySelectElement);
        // Chọn thành phố
        citySelect.selectByValue(cityValue);

        // Chờ cho dropdown quận hiển thị tùy theo thành phố đã chọn
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement districtSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("districtId")));

        // Khởi tạo đối tượng Select từ phần tử select quận
        Select districtSelect = new Select(districtSelectElement);
        // Chọn quận
        districtSelect.selectByValue(districtValue);
    }

    // Hàm kiểm tra kết quả thực tế
    public static void kiemTraKetQuaThucTe(WebDriver driver) {
        // Kết quả mong đợi
        List<String> expect_result = Arrays.asList("Lào Cai", "Huyện Bát Xát");

        // Kết quả thực tế
        WebElement cityDropdown = driver.findElement(By.id("cityId"));
        Select citySelect = new Select(cityDropdown);
        WebElement districtDropdown = driver.findElement(By.id("districtId"));
        Select districtSelect = new Select(districtDropdown);

        String actualCity = citySelect.getFirstSelectedOption().getText();
        String actualDistrict = districtSelect.getFirstSelectedOption().getText();
        
        

        List<String> actual_result = new ArrayList<>();
        actual_result.add(actualCity);
        actual_result.add(actualDistrict);

        // Kiểm tra kết quả thực tế
        if (expect_result.equals(actual_result)) {
            System.out.println("Kết quả thực tế giống với kỳ vọng.");
        } else {
            System.out.println("Kết quả thực tế không giống với kỳ vọng!");
            System.out.println("Kết quả kỳ vọng: " + expect_result);
            System.out.println("Kết quả thực tế: " + actual_result);
        }
    }
}
