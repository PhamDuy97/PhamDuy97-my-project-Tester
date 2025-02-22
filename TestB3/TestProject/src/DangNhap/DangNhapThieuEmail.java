package DangNhap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DangNhapThieuEmail {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\KoDau\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("http://didoshop.vn/");

		// Nhấp nút đăng nhập
		driver.findElement(By.className("btnSignin")).click();

		// Thay thế email hợp lệ bằng chuỗi rỗng
		driver.findElement(By.id("username")).sendKeys("");
		Thread.sleep(2000);
		driver.findElement(By.id("password")).sendKeys("phamduy0907");
		Thread.sleep(2000);
		// Nhấp nút đăng nhập
		driver.findElement(By.id("btnSubmit")).click();

		// Kiểm tra lỗi email trống
		// Kiểm tra đăng nhập thành công
		String currentURL = driver.getCurrentUrl();
		String expectedErrorMessage = "Bạn chưa nhập email";

		// Kiểm tra lỗi email trống
		if (driver.findElement(By.className("errors")).getText().contains(expectedErrorMessage)) {
			System.out.println("Đăng nhập thất bại với email trống!");
			//        Kiểm tra kết quả
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
			System.out.println("Đăng nhập thành công!");
		}
		// Đóng trình duyệt
		driver.close();

	}
}