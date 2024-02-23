package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import utiLities.ExcelFileUtils;

public class DriverScript {
	
	public static WebDriver driver;
	String inputpath = "./FileInput/ExcelmasterTestCases.xlsx";
	String outputpath = "./FileOutput/Hybridframeworkresults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	@Test
	public void starttest() throws Throwable {
		String Modulestatus = "";
		ExcelFileUtils xl = new ExcelFileUtils(inputpath);
		String Testcases = "MasterTestCases";
		for(int i=1;i<=xl.rowCount(Testcases);i++)
		{
			if(xl.getCellData(Testcases, i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule = xl.getCellData(Testcases, i, 1);
				report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger =report.startTest(TCModule);
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_type = xl.getCellData(TCModule, j, 1);
					String Locator_type = xl.getCellData(TCModule, j, 2);
					String Locator_value = xl.getCellData(TCModule, j, 3);
					String Test_data = xl.getCellData(TCModule, j,4);
					try
					{
					if(Object_type.equalsIgnoreCase("StartBrowser"))
					{
						driver = FunctionLibrary.StartBrowser();
						logger.log(LogStatus.INFO, Description);
						
					}
					if(Object_type.equalsIgnoreCase("OpenUrl"))
					{
						FunctionLibrary.OpenUrl();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("WaitForElement"))
					{
						FunctionLibrary.WaitForElement(Locator_type, Locator_value, Test_data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("TypeAction"))
					{
						FunctionLibrary.TypeAction(Locator_type, Locator_value, Test_data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("ClickAction"))
					{
						FunctionLibrary.ClickAction(Locator_type,Locator_value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("ValidateTitle"))
					{
						FunctionLibrary.ValidateTitle(Test_data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("CloseBrowser"))
					{
						FunctionLibrary.closeBroswer();
						logger.log(LogStatus.INFO, Description);
					}if(Object_type.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(Locator_type, Locator_value, Test_data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("captureStockNum"))
					{
						FunctionLibrary.captureStockNum(Locator_type, Locator_value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("capturesup"))
					{
						FunctionLibrary.capturesup(Locator_type, Locator_value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("capturecus"))
					{
						FunctionLibrary.capturecus(Locator_type, Locator_value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_type.equalsIgnoreCase("customerTable"))
					{
						FunctionLibrary.customerTable();
						logger.log(LogStatus.INFO, Description);
					}
					
					xl.setcellDaata(TCModule, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);
					Modulestatus ="True";
					}
					
				  catch (Exception e) {
					System.out.println(e.getMessage());
					xl.setcellDaata(TCModule, j, 5,"Fail",outputpath);
					logger.log(LogStatus.FAIL, Description);
					Modulestatus ="False";
				}
				
					if(Modulestatus.equalsIgnoreCase("true"))
					{
						xl.setcellDaata(Testcases, i, 3,"Pass",outputpath);
					}
					else 
					{
						xl.setcellDaata(Testcases, i, 3,"Fail",outputpath);
					}
					report.endTest(logger);
					report.flush();
				}
				
			}
			
			else
			{
				xl.setcellDaata(Testcases, i, 3, "Blocked", outputpath);
				
			}
			 
		
	}
	

}
}
