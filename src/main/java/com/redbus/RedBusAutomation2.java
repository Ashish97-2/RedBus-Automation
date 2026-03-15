package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomation2 {
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--start-maximized");
		// Launch the browser
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// visit redbus.in website
		driver.get("https://www.redbus.in/");

		By sourceButtonlocator = By.xpath("//div[contains(@class,'inputAndSwapWrapper')]");
		WebElement sourceButton = driver.findElement(sourceButtonlocator);
		sourceButton.click();

		By searchsuggestionLocator = By.xpath("//div[contains(@class,\"suggestionsWrapper___20e43c\")]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchsuggestionLocator));
		WebElement searchButton = driver.switchTo().activeElement();
		searchButton.sendKeys("Mumbai");

		By destLocationLocator = By.xpath("//div[@aria-label='Mumbai' and @role='heading' ]");
		WebElement destLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(destLocationLocator));
		destLocation.click();

//		Destination Selection
		WebElement totextBox = driver.switchTo().activeElement();
		totextBox.sendKeys("Pune");

		By toSearchCategoryLocator = By.xpath("//div[contains(@class,'searchCategory')]");
		List<WebElement> toSearchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(toSearchCategoryLocator, 2));

		WebElement toLocationCategory = toSearchList.get(0);
		By toLocationNameLocator = By.xpath("//div[contains(@class,'listHeader')]");
		List<WebElement> toLocationList = toLocationCategory.findElements(toLocationNameLocator);

		for (WebElement location : toLocationList) {
			if (location.getText().equalsIgnoreCase("pune")) {
				location.click();
				break;
			}
		}

	}
}
