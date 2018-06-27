package test.java;

import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class UAT {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setJavascriptEnabled(true);                
    caps.setCapability("takesScreenshot", true);  
    caps.setCapability(
                            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                            "/usr/local/share/phantomjs-1.9.8-linux-x86_64/bin/phantomjs"
                        );
    File file = new File("/usr/local/share/phantomjs-1.9.8-linux-x86_64/bin/phantomjs");
    System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
    driver = new PhantomJSDriver();
    baseUrl = "http://www.gts.fiorentina.test/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUAT() throws Exception {
    Thread.sleep(1000000);
	driver.get("http://192.168.50.105:8080/");
    driver.findElement(By.id("cfg-size-length")).click();
    driver.findElement(By.id("cfg-size-length")).clear();
    driver.findElement(By.id("cfg-size-length")).sendKeys("10");
    driver.findElement(By.id("cfg-size-height")).clear();
    driver.findElement(By.id("cfg-size-height")).sendKeys("10");
    driver.findElement(By.id("cfg-size-depth")).clear();
    driver.findElement(By.id("cfg-size-depth")).sendKeys("10");
    driver.findElement(By.xpath("//h3")).click();
    Thread.sleep(5000);
    assertEquals("S", driver.findElement(By.id("cfg-category")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
