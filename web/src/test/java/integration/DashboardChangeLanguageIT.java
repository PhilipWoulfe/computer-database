package integration;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class DashboardChangeLanguageIT {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private static final int SLEEP = 3000;

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8181";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testDashboardChangeLanguageIT() throws Exception {
    driver.get(baseUrl + "/dashboard");
    Thread.sleep(SLEEP);
    assertTrue(driver.findElement(By.xpath("//h1")).getText().endsWith("Computers found"));

    driver.findElement(By.xpath("//img[@onclick=\"$.fn.changeLanguage('fr')\"]")).click();
    Thread.sleep(SLEEP);
    assertTrue(driver.findElement(By.xpath("//h1")).getText().endsWith("Ordinateurs trouv\u00E9s"));

    driver.findElement(By.cssSelector("img")).click();
    Thread.sleep(SLEEP);
    assertTrue(driver.findElement(By.xpath("//h1")).getText().endsWith("Computers found"));
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
