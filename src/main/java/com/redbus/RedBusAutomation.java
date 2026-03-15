package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomation {
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://www.redbus.in/");

		By sourceButtonlocator = By.xpath("//div[contains(@class,'inputAndSwapWrapper')]");
		WebElement sourceButton = driver.findElement(sourceButtonlocator);
		sourceButton.click();

		WebElement searchButton = driver.switchTo().activeElement();
		searchButton.sendKeys("Mumbai");

		By destLocationLocator = By.xpath("//div[@aria-label='Mumbai' and @role='heading' ]");
		WebElement destLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(destLocationLocator));
		destLocation.click();

		selectLocation(driver, wait, "Pune");

	}

	public static void selectLocation(WebDriver driver, WebDriverWait wait, String locationdata) {
		WebElement totextBox = driver.switchTo().activeElement();
		totextBox.sendKeys(locationdata);

		By toSearchCategoryLocator = By.xpath("//div[contains(@class,'searchCategory')]");
		List<WebElement> toSearchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(toSearchCategoryLocator, 2));

		WebElement toLocationCategory = toSearchList.get(0);
		By toLocationNameLocator = By.xpath("//div[contains(@class,'listHeader')]");
		List<WebElement> toLocationList = toLocationCategory.findElements(toLocationNameLocator);

		for (WebElement location : toLocationList) {
			if (location.getText().equalsIgnoreCase(locationdata)) {
				location.click();
				break;
			}
		}

		By searchButtonLocator = By.xpath("//button[@aria-label='Search buses']");
		WebElement searchbutton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
		searchbutton.click();

		By PrimoButtonLocator = By.xpath("//div[contains(text(),'Primo Bus')]");
		WebElement Primobutton = wait.until(ExpectedConditions.elementToBeClickable(PrimoButtonLocator));
		Primobutton.click();

		By EveningButtonLocator = By.xpath("//div[contains(text(),'18:00-24:00')]");
		WebElement EveningButton = wait.until(ExpectedConditions.elementToBeClickable(EveningButtonLocator));
		EveningButton.click();

		By subtitleLocator = By.xpath("//span[contains(@class,'subtitle')]");
		WebElement subtitle = null;
		if (wait.until(ExpectedConditions.textToBePresentInElementLocated(subtitleLocator, "buses"))) {
			subtitle = wait.until(ExpectedConditions.visibilityOfElementLocated(subtitleLocator));
		}
		System.out.println(subtitle.getText());

		By tupleWrapper = By.xpath("//li[contains(@class,'tupleWrapper')]");
		By busesNameLocator = By.xpath("//div[contains(@class,'travelsName')]");

		while (true) {
			List<WebElement> rowlist = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tupleWrapper));
			List<WebElement> endOfList = driver.findElements(By.xpath("//span[text()='End of list']"));

			if (!endOfList.isEmpty()) {
				break;
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowlist.get(rowlist.size() - 3));

		}
		List<WebElement> rowlist = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tupleWrapper));
		for (WebElement row : rowlist) {
			String busName = row.findElement(busesNameLocator).getText();
			System.out.println(busName);
		}
		System.out.println("Total no of Buses Loaded with Prime and Evening filter: " + rowlist.size());
	}
}
