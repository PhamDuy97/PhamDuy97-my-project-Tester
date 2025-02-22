package DangNhap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CapNhatThongTinThanhcong {

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
            
         // Nhấn vào ô ngày để hiển thị datepicker
            WebElement datePicker = driver.findElement(By.xpath("//input[@class='validate[required] txtBirthday hasDatepicker']"));
            datePicker.click(); 
            // Chờ cho trang đăng nhập load hoàn tất
            Thread.sleep(2000);
            
            // Chọn tháng từ dropdown
            WebElement monthDropdown = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
            monthDropdown.click();
            Thread.sleep(2000);
            Select selectMonth = new Select(monthDropdown);
            selectMonth.selectByVisibleText("Sáu");                       
            Thread.sleep(2000);            
            
            // Chọn năm từ dropdown
            WebElement yearDropdown = driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
            yearDropdown.click();          
            Thread.sleep(2000);
            Select selectYear = new Select(yearDropdown);
            selectYear.selectByVisibleText("2004");

            // Chờ cho trang đăng nhập load hoàn tất
            Thread.sleep(2000);            
            
            // Chọn ngày trong datepicker có thể sửa tr[3] và td[5] để thay đổi ngày 
            WebElement dayInDatePicker = driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[3]/td[5]"));
            dayInDatePicker.click();
            Thread.sleep(2000);

//			Thay đổi thông tin cá nhân
//          SDT
            driver.findElement(By.name("mobile")).clear();
            driver.findElement(By.name("mobile")).sendKeys("0945873988");
            
//          TP
            // Tìm phần tử select bằng ID
            WebElement citySelectElement = driver.findElement(By.id("cityId"));
            // Khởi tạo đối tượng Select từ phần tử select
            Select citySelect = new Select(citySelectElement);
            // Chọn thành phố (ví dụ)
            citySelect.selectByValue("255"); // Chọn Hồ Chí Minh
            
//          Quận
            // Tìm phần tử select bằng ID
            WebElement selectElement = driver.findElement(By.name("districtId"));
            // Khởi tạo đối tượng Select từ phần tử select
            Select select = new Select(selectElement);
            // Chọn option theo giá trị (ví dụ: "Quận 8")
            select.selectByValue("359");
           
 
//          Địa Chỉ
            driver.findElement(By.name("address")).clear();
            driver.findElement(By.name("address")).sendKeys("VLUhuhu");
            
//            Bấm nút Submit
            driver.findElement(By.className("btn-profile")).click();
            Thread.sleep(2000);
            
            // Kiểm tra cập nhật thành công
            currentURL = driver.getCurrentUrl();
            if (currentURL.equals("http://didoshop.vn/profile")) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thất bại!");
            }
         // Kiểm tra kết quả
            String expect_result = currentURL;
            String actual_result = "http://didoshop.vn/profile";

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
        driver.quit(); // Sử dụng driver.quit() để đóng tất cả các cửa sổ trình duyệt
    }
}
