package TestScripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Excel {
    String driverPath = "C:\\KoDau\\chromedriver.exe";
    public WebDriver driver;

    @Test(dataProvider = "dp")
    public void f(String keyWord1, String keyWord2) throws InterruptedException {
        String url = "http://didoshop.vn/";
        driver.get(url);
        driver.findElement(By.className("btnSignin")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys(keyWord1);
        Thread.sleep(1000);
        driver.findElement(By.id("password")).sendKeys(keyWord2);
        Thread.sleep(1000);
        driver.findElement(By.id("btnSubmit")).click();
        
        try {
            Thread.sleep(2000);
            driver.switchTo().alert().accept();
            System.out.println("Đăng nhập thất bại cho tài khoản: " + keyWord1);
        } catch (NoAlertPresentException e) {
            System.out.println("Đăng nhập thành công cho tài khoản: " + keyWord1);
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Bắt đầu test");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
    }

    @DataProvider(name = "dp")
    public Object[][] dp() {
        return getExcelData("D:\\dangnhap.xlsx", "Sheet1");
    }

    private Object[][] getExcelData(String fileName, String sheetName) {
        Object[][] data = null;

        try {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int numRows = sheet.getPhysicalNumberOfRows();
            int numCols = sheet.getRow(0).getLastCellNum();

            data = new Object[numRows - 1][2]; // Chỉ có 2 cột keyWord1 và keyWord2

            for (int i = 1; i < numRows; i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell1 = row.getCell(0); // Lấy dữ liệu từ cột 1
                XSSFCell cell2 = row.getCell(1); // Lấy dữ liệu từ cột 2

                if (cell1 != null && cell2 != null) {
                    data[i - 1][0] = cell1.getStringCellValue(); // Lưu dữ liệu vào cột keyWord1
                    data[i - 1][1] = cell2.getStringCellValue(); // Lưu dữ liệu vào cột keyWord2
                }
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
