package utilities;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHandler {

	PropHandler ph = new PropHandler();

//	public static void main(String args[]) {
//		DataFileHandler dh = new DataFileHandler();
//		for(Object[] x:dh.readDataFromExcel("list")) {
//			for (Object y : x) {
//				System.out.println(y);
//			}
//		}
//	}


	private File excelFile;
	private FileInputStream fis;
	private FileOutputStream fos;
	private XSSFWorkbook wb = null;
	private XSSFSheet sheet;

	public Object[][] readData_Excel(String sheetName) {

		excelFile = new File(ph.readDataFromPropFile("inputDataWb"));

		try{
			fis = new FileInputStream(excelFile);
			wb = new XSSFWorkbook(fis);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		sheet = wb.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum();// index
		int colCount = sheet.getRow(0).getLastCellNum();// physical
		Object[][] data = new Object[rowCount][colCount];
		for (int r = 1; r <= rowCount; r++) {

			XSSFRow row = sheet.getRow(r);
			for (int c = 0; c < colCount; c++) {
				XSSFCell cell = row.getCell(c);
				CellType cellType = cell.getCellType();

				switch (cellType) {
				case STRING:
					data[r - 1][c] = cell.getStringCellValue();
					break;
				case NUMERIC:
					data[r - 1][c] = cell.getNumericCellValue();
					break;
				case BLANK:
					data[r - 1][c] = null;
					break;
				default:
					break;
				}
			}
		}

		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}

	public void writeData_NewExcel(List<String> data) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook();
		try (wb) {
			XSSFSheet sheet = wb.createSheet("result");
			File excelFile = new File(ph.readDataFromPropFile("outputDataWb"));
			Object[][] convertedData = convertListToArray(data);
			for (int r = 0; r < convertedData.length; r++) {
				XSSFRow row = sheet.createRow(r + 1);
				for (int c = 0; c < convertedData[r].length; c++) {
					XSSFCell cell = row.createCell(c);
					if (convertedData[r][c] instanceof String) {
						cell.setCellValue((String) convertedData[r][c]);
					} else if (convertedData[r][c] instanceof Double) {
						cell.setCellValue((Double) convertedData[r][c]);
					} else if (convertedData[r][c] instanceof Integer) {
						cell.setCellValue((Integer) convertedData[r][c]);
					} else if (convertedData[r][c] instanceof Boolean) {
						cell.setCellValue((Boolean) convertedData[r][c]);
					}

				}
			}

			try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
				wb.write(fileOut);
			}
		}
	}

	private Object[][] convertListToArray(List<String> dataList) {
		Object[][] array = new Object[dataList.size()][1]; // Assuming each string is a single column entry
		for (int i = 0; i < dataList.size(); i++) {
			array[i][0] = dataList.get(i);
		}
		return array;
	}
		}





































