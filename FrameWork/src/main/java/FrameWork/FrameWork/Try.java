package FrameWork.FrameWork;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;




public class Try {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		System.setProperty("webdriver.ie.driver",
				"C:\\Program Files (x86)\\Internet Explorer\\IEDriverServer.exe");

		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		WebDriver driver = new InternetExplorerDriver(capabilities);
		
		driver.get("https://ptl01omsap01a:30343/yantra/console/login.jsp");
		Thread.sleep(10000);
		//driver.findElement(By.linkText("Continue to this website (not recommended).")).click();
		driver.findElement(By.xpath("//*[contains(text(), 'Continue to this website (not recommended).')]")).click();
	
		 driver.findElement(By.name("UserId")).sendKeys("BIZ_CONFIG_USER");
		
		driver.findElement(By.name("Password")).sendKeys("h2N2scvi");
		driver.findElement(By.name("btnLogin")).click();
		driver.findElement(By.linkText("Order")).click();
		driver.findElement(By.linkText("Order Console")).click();
	}

}
