package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RedBus {
public static void main(String[] args) throws InterruptedException {
	WebDriver driver=new ChromeDriver();
	driver.get("https://www.redbus.in/");
	
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	
	driver.findElement(By.xpath("//div[contains(@class,'inputAndSwapWrapper')]")).click();
	
	driver.findElement(By.xpath("//div[contains(@class,'inputWrapper')]//input[@id='srcinput']")).sendKeys("Mumbai");
	
	driver.findElement(By.xpath("//div[@aria-label='Mumbai' and @role='heading' ]")).click();
	
	WebElement toTextbox=driver.switchTo().activeElement();
	toTextbox.sendKeys("Pune");
	
	driver.findElement(By.xpath("//div[@aria-label=\"Pune\"]")).click();
	
	driver.findElement(By.xpath("//button[@aria-label=\"Search buses\"]")).click();
	
//	driver.findElement(By.xpath("//button[@aria-label=\"Close\"]")).click();
	//select Primo Bus
	driver.findElement(By.xpath("//div[contains(text(),'Primo Bus')]")).click();
	
	//select Evening filter
	driver.findElement(By.xpath("//div[contains(text(),'18:00-24:00 ')]")).click();
	
	Thread.sleep(2000);
	//print no of Buses available
	WebElement noOfBuses=driver.findElement(By.xpath("//span[contains(@class,'subtitle')]"));
	System.out.println(noOfBuses.getText());
	
	//List all available buses
	List<WebElement>  listBuses=driver.findElements(By.xpath("//div[contains(@class,'travelsName')]"));
	
	for(WebElement bus:listBuses) {
		System.out.println(bus.getText());
	}
}
}
