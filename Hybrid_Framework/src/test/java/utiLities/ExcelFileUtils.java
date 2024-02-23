package utiLities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {
	Workbook wb;
	public ExcelFileUtils(String excelPath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(excelPath);
		wb = WorkbookFactory.create(fi);
	}
	//method for counting number of row
	public int rowCount(String sheet)
	{
		return wb.getSheet(sheet).getLastRowNum();

	}
	public String getCellData(String sheet,int row,int cell)
	{
		String data="";
		if(wb.getSheet(sheet).getRow(row).getCell(cell).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int)wb.getSheet(sheet).getRow(row).getCell(cell).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();

		}
		return data;	
	}
	public void setcellDaata(String sheet,int row,int Column,String status,String writeExcel) throws Throwable {
		Sheet ws = wb.getSheet(sheet);
		Row rownum = ws.getRow(row);
		Cell cell = rownum.createCell(Column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			CellStyle stylee = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			stylee.setFont(font);
			ws.getRow(row).getCell(Column).setCellStyle(stylee);
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			CellStyle stylee = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			stylee.setFont(font);
			ws.getRow(row).getCell(Column).setCellStyle(stylee);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			CellStyle stylee = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			stylee.setFont(font);
			ws.getRow(row).getCell(Column).setCellStyle(stylee);
		}
		FileOutputStream fo=new FileOutputStream(writeExcel);
		wb.write(fo);


	}

	public static void main(String[] args) throws Throwable 
	{
		ExcelFileUtils fi = new ExcelFileUtils("D:/sample.xlsx");
		int rc = fi.rowCount("emp");
		System.out.println(rc);
		for(int i=1;i<=rc;i++)
		{
			String fname = fi.getCellData("emp", i, 0);
			String mname  = fi.getCellData("emp", i, 1);
			String dob = fi.getCellData("emp", i, 2);
			System.out.println(fname+" "+mname+" "+dob);
			fi.setcellDaata("emp", i, 4,"Pass","D:\\sample.output\\empresult.xlsx");

		}



	}
}