package pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class searchProduct {

	WebDriver ldriver;

	public searchProduct(WebDriver rDriver) {

		ldriver = rDriver;
		PageFactory.initElements(rDriver, this);
	}

	@FindBy(xpath = "//input[@id='twotabsearchtextbox']")
	WebElement searchBox;

	@FindBy(xpath = "//input[@id='nav-search-submit-button']")
	WebElement searchIcon;


	// Action Methods//

	public void clickSearchBox() {
		searchBox.click();
	}

	public void enterProduct(String product) {
		searchBox.sendKeys(product);
	}

	public void clickSearchButton() {
		searchIcon.click();
	}

	public boolean isSearchResultDisplayed() {
	    try {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

	        WebElement firstResult = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//div[@data-component-type='s-search-result'])[1]")
	            )
	        );

	        return firstResult.isDisplayed();

	    } catch (Exception e) {
	        return false;
	    }
	}
	
	    // 🔥 MAIN LOGIC (IMPORTANT)
	public void selectProduct() {

	    List<WebElement> products = ldriver.findElements(
	        By.xpath("//div[@data-component-type='s-search-result']")
	    );

	    for (WebElement product : products) {
	        try {

	            // ❌ Skip sponsored
	            if (product.getText().toLowerCase().contains("sponsored")) {
	                continue;
	            }

	            // ✅ Get product name
	            String name = product.findElement(By.xpath(".//h2//span")).getText();

	            WebElement clickable = product.findElement(By.xpath(".//h2//span"));

	            // Scroll
	            ((JavascriptExecutor) ldriver)
	                .executeScript("arguments[0].scrollIntoView(true);", clickable);

	            // Click
	            ((JavascriptExecutor) ldriver)
	                .executeScript("arguments[0].click();", clickable);

	            System.out.println("✅ CLICKED PRODUCT: " + name);

	            // Switch tab
	            for (String windowHandle : ldriver.getWindowHandles()) {
	                ldriver.switchTo().window(windowHandle);
	            }

	            return; // ✅ stop after first product

	        } catch (Exception e) {
	            continue;
	        }
	    }

	    throw new RuntimeException("❌ No product clicked!");
	}
	   
	public boolean isProductPageOpened() {
	    try {
	        WebElement productTitle = ldriver.findElement(By.id("productTitle"));

	        // ✅ Print actual product title
	        System.out.println("Product Title: " + productTitle.getText());

	        return productTitle.isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}
}
