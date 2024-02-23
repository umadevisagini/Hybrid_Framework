package commonFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {

	public static Properties conprop;
	public static WebDriver driver;

	public static WebDriver StartBrowser() throws Throwable
	{
		conprop = new Properties();
		conprop.load(new FileInputStream("./PropertyFile/Enviorment.properties"));
		if(conprop.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}else if(conprop.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new ChromeDriver();
		}
		else
		{
			Reporter.log("Browser values is not matching");
		}
		return driver;
	}
	public static void OpenUrl()
	{
		driver.get(conprop.getProperty("Url"));
	}

	public static void WaitForElement(String LocatorType,String LocatorValue,String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt("TestData")));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
	}
	public static void TypeAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
	}
	public static void ClickAction(String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).click();

		}
		if(LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	public static void ValidateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title, "Title is not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	public static void closeBroswer() {

		driver.quit();
	}
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
	}
	//method for listboxes
	public static void dropDownAction(String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value =Integer.parseInt(Test_Data);
			Select element =new Select(driver.findElement(By.xpath(Locator_Value)));
			element.selectByIndex(value);
			
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value =Integer.parseInt(Test_Data);
			Select element =new Select(driver.findElement(By.name(Locator_Value)));
			element.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value =Integer.parseInt(Test_Data);
			Select element =new Select(driver.findElement(By.id(Locator_Value)));
			element.selectByIndex(value);
		}
	}
	//method for capturing stock number into note pad
	public static void captureStockNum(String Locator_Type,String Locator_Value)throws Throwable
	{
		String StockNum ="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			StockNum =driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			StockNum =driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			StockNum =driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNum);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void stockTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data= br.readLine();
		if(!driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conprop.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conprop.getProperty("search-button") )).click();
		Thread.sleep(4000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
		Assert.assertEquals(Exp_Data, Act_Data,"Stock Number Not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for capture supplier number
	public static void capturesup(String Locator_Type,String Locator_Value)throws Throwable
	{
	String SupplierNum="";
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		SupplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		SupplierNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		SupplierNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		
	}
	FileWriter fw = new FileWriter("./CaptureData/suppliernumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(SupplierNum);
	bw.flush();
	bw.close();
	}
	//method for supplier table
	public static void supplierTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conprop.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conprop.getProperty("search-button") )).click();
		Thread.sleep(4000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
		Assert.assertEquals(Exp_Data, Act_Data, "Supplier number Not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for capture customer number
	public static void capturecus(String Locator_Type,String Locator_Value)throws Throwable
	{
	String CustomerNum="";
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		CustomerNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		CustomerNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		CustomerNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		
	}
	FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(CustomerNum);
	bw.flush();
	bw.close();
	}
	//method for customer table
	public static void customerTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/customernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conprop.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conprop.getProperty("search-button") )).click();
		Thread.sleep(4000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
		Assert.assertEquals(Exp_Data, Act_Data, "Customer number Not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	

}
