package com.srct.ril.poas.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.web.multipart.MultipartFile;

import com.srct.ril.poas.ai.nlp.NLPAnalysis;
import com.srct.ril.poas.ai.nlp.NLPAnalysis.Item;
import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.utils.category.Category.Sentiment;
import com.srct.ril.poas.dao.utils.origin.Origin;
import com.srct.ril.poas.utils.log.Log;

public class ExcelUtils {

	private static List<String> sColumnTitle = Arrays.asList("ID","Time duration","Origin","title","comment","Sentiment","Category","Url");

	private static void testReadFromExcel(String modelName, String file) {

		ArrayList<NLPItem> ma = ReadFromExcel(modelName, file);
		Iterator<NLPItem> it = ma.iterator();
		int maIndex = 0;
		System.out.println("maIndex" + maIndex);
		while (it.hasNext() && maIndex < ma.size()) {
			System.out.println(ma.get(maIndex).getTitle());
			maIndex++;
		}
		System.out.println("maIndex" + maIndex);

	}

	private static String baseDir = "excel_log/";
	// public static void main(String[] args) {
	// ArrayList<String> arrayList = new ArrayList<String>();
	// arrayList.add("lalalla");
	// arrayList.add("lbbb");
	// arrayList.add("lacccla");
	// arrayList.add("ldd");
	// WriteToExcel(arrayList);
	// testReadFromExcel("./src/main/webapp/G9500_ALL_2018-02-26 00_00_00_.xls")
	// }

	private static HSSFCellStyle GetNormalCellStyle(HSSFWorkbook wb) {

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(BorderStyle.THIN);// 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框

		return cellStyle;
	}

	private static HSSFCellStyle GetClolorCellStyle(HSSFWorkbook wb) {

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(BorderStyle.THIN);// 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setFillForegroundColor(HSSFColorPredefined.PALE_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	private static HSSFSheet GetSheetOfNlpItem(HSSFWorkbook wb) {

		HSSFSheet sheet = wb.createSheet("NLP_Item");
		sheet.setColumnWidth(0, 5 * 512);// ID
		sheet.setColumnWidth(1, 15 * 512);// Time duration
		sheet.setColumnWidth(2, 5 * 512);// Origin
		sheet.setColumnWidth(3, 30 * 512);// title
		sheet.setColumnWidth(4, 35 * 512);// comment
		sheet.setColumnWidth(5, 5 * 512);// sentiment
		sheet.setColumnWidth(6, 5 * 512);// category
		sheet.setColumnWidth(7, 15 * 512);// URL

		return sheet;
	}

	public static void DelayWriteFile(HSSFWorkbook o, int time) {
		try {
			Thread.sleep(time);
			wf(o);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void wf(HSSFWorkbook wb) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(baseDir + "workbook1.xls");
			wb.write(fileOut);// 把Workbook对象输出到文件workbook.xls中
			fileOut.close();
			System.out.println("write to excel done !");
		} catch (FileNotFoundException e) {
			System.out.println("请先关闭excel文件，然后稍等!");
			DelayWriteFile(wb, 5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void wf(HSSFWorkbook wb, String filename) {
		FileOutputStream fileOut;
		String puth = baseDir + filename + ".xls";
		System.out.print(puth);
		try {
			fileOut = new FileOutputStream(puth);
			wb.write(fileOut);// 把Workbook对象输出到文件puth中
			fileOut.close();
			System.out.println("write to excel done !");
		} catch (FileNotFoundException e) {
			System.out.println("请先关闭excel文件，然后稍等!");
			DelayWriteFile(wb, 5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HSSFWorkbook NLPItem_WriteToExcel(Object object, String filename) {

		int excel_line = 0;
		int NLPIndex = 0;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;

		if (filename != null) {
			String puth = baseDir + filename + ".xls";
			FileInputStream fs;
			try {
				fs = new FileInputStream(puth);
				POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
				wb = new HSSFWorkbook(ps);
				sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
				excel_line = sheet.getLastRowNum() + 1;// 从这行开始写 新的数据
				fs.close();

			} catch (FileNotFoundException e) {
				wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
				sheet = GetSheetOfNlpItem(wb);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
			sheet = GetSheetOfNlpItem(wb);
		}

		HSSFCellStyle cellStyle = GetNormalCellStyle(wb);
		HSSFCellStyle titleStyle = GetClolorCellStyle(wb);
		@SuppressWarnings("unchecked")
		ArrayList<NLPItem> listNLP = (ArrayList<NLPItem>) object;
		Iterator<NLPItem> it = listNLP.iterator();

		
		HSSFRow titleRow = sheet.createRow(excel_line++);
		// =================
		for (int i = 0; i < sColumnTitle.size(); i++) {
			HSSFCell titleCell = titleRow.createCell(i);
			titleCell.setCellStyle(titleStyle);
			titleCell.setCellValue(sColumnTitle.get(i));
		}

		// =================
		// 利用迭代器读取 list中的每个NLPAnalysis
		while (it.hasNext() && NLPIndex < listNLP.size()) {

			HSSFRow row = sheet.createRow(excel_line++);

			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getId());

			cell = row.createCell(1);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getTimestamp());

			cell = row.createCell(2);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(Origin.displayOrigin(listNLP.get(NLPIndex).getOrigin()));

			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getTitle());

			cell = row.createCell(4);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getFirstcomment());

			cell = row.createCell(5);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getSentiment().getDisplay());

			cell = row.createCell(6);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getCategory());

			cell = row.createCell(7);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(listNLP.get(NLPIndex).getLink());

			NLPIndex++;
		}
		return wb;
	}

	/*
	 * flag is false , items will be ignored flag is true , items will be printed to
	 * excel file
	 */
	public static HSSFWorkbook NLP_WriteToExcel(Object object, boolean f, String filename) {

		String puth = baseDir + filename + ".xls";
		int excelIndex = 0;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;

		FileInputStream fs;
		try {
			fs = new FileInputStream(puth);
			POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
			wb = new HSSFWorkbook(ps);
			sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
			excelIndex = sheet.getLastRowNum() + 1;// 从这行开始写 新的数据
			System.out.println("continue output at excel index : " + excelIndex);
			fs.close();
		} catch (FileNotFoundException e) {
			wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
			sheet = wb.createSheet("NLP_Item");
			System.out.println("建立新表");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sheet.setColumnWidth(0, 25 * 512);// 第一列宽度
		sheet.setColumnWidth(1, 15 * 512);// 第二列宽度
		sheet.setColumnWidth(3, 10 * 512);// 第四列宽度
		HSSFCellStyle cellStyle = GetNormalCellStyle(wb);
		HSSFCellStyle titleStyle = GetClolorCellStyle(wb);
		@SuppressWarnings("unchecked")
		ArrayList<NLPAnalysis> listNLP = (ArrayList<NLPAnalysis>) object;
		Iterator<NLPAnalysis> it = listNLP.iterator();

		// ArrayList<ArrayList<String>> excel_lines = new
		// ArrayList<ArrayList<String>>();

		int NLPIndex = 0;
		// 利用迭代器读取 list中的每个NLPAnalysis
		while (it.hasNext() && NLPIndex < listNLP.size()) {
			// ---------------------------------------------------------------------
			// 每个NLPAnalysis句子的第一行
			HSSFRow row1 = sheet.createRow(excelIndex++);
			HSSFCell cell00 = row1.createCell(0);
			cell00.setCellStyle(titleStyle);
			// Log.ii(listNLP);
			cell00.setCellValue(listNLP.get(NLPIndex).getContent());

			/// --------------------------------------------------------------------

			if (f) {
				List<Item> itemlist = listNLP.get(NLPIndex).getItems();// 获取这个NLPAnalysis的item的list
				Iterator<Item> itemit = itemlist.iterator();

				int j = 0;
				// 循环NLPAnalysis中的items这个list
				while (itemit.hasNext() && j < itemlist.size()) {

					HSSFRow row = sheet.createRow(excelIndex);
					HSSFCell cell0 = row.createCell(0);
					cell0.setCellStyle(cellStyle);
					cell0.setCellValue(" ");

					HSSFCell cell1 = row.createCell(1);
					cell1.setCellValue(itemlist.get(j).getSubContent());
					cell1.setCellStyle(cellStyle);

					HSSFCell cell2 = row.createCell(2);
					cell2.setCellValue(itemlist.get(j).getProp());
					cell2.setCellStyle(cellStyle);

					HSSFCell cell3 = row.createCell(3);
					cell3.setCellValue(itemlist.get(j).getCategory());
					cell3.setCellStyle(cellStyle);

					HSSFCell cell4 = row.createCell(4);
					cell4.setCellValue(listNLP.get(NLPIndex).getSentiment().getDisplay());
					cell4.setCellStyle(cellStyle);

					excelIndex++;
					j++;

				} // while(itemit.hasNext()便利item
					// excel_lines.add(line);
			} // if(f)

			NLPIndex++;
		} // while(it.hasNext()便利NLP

		wf(wb, filename);
		return wb;

	}

	public static void WriteToExcel(Object object) {

		@SuppressWarnings("unchecked")
		ArrayList<String> listarray = (ArrayList<String>) object;
		System.out.println(listarray.size());

		HSSFWorkbook wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
		HSSFSheet sheet = wb.createSheet("NLP_Analysis");
		HSSFCellStyle cellStyle = GetNormalCellStyle(wb);
		HSSFCellStyle titleStyle = GetClolorCellStyle(wb);

		Iterator<String> it = listarray.iterator();
		int Index = 0;

		while (it.hasNext() && Index < listarray.size()) {
			HSSFRow row = sheet.createRow(Index);
			HSSFCell cell = row.createCell(0);
			HSSFCell cell1 = row.createCell(1);
			cell.setCellValue(listarray.get(Index).toString());
			cell.setCellStyle(cellStyle);
			cell1.setCellStyle(titleStyle);
			cell1.setCellValue("obq");
			System.out.println(listarray.get(Index).toString());
			Index++;
		}
		wf(wb);
	}

	public static ArrayList<NLPItem> ReadFromExcel(String modelName, HSSFWorkbook wb) {

		ArrayList<NLPItem> listNLP = new ArrayList<NLPItem>();

		HSSFSheet sheet = null;
		sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
		int maxline = sheet.getLastRowNum();// 从这行开始写 新的数据

		for (int i = 1; i <= maxline; i++) {// 第一行要跳过，都是title
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				int id = -1;
				if(row.getCell(0)!=null)
					id = new Double(row.getCell(0).getNumericCellValue()).intValue();
				String origin = null;
				if(row.getCell(2)!=null)
					origin = Origin.getOrigin(row.getCell(2).getStringCellValue());
				Sentiment sentiment = Sentiment.UNKNOWN;
				if(row.getCell(5)!=null)
					sentiment = Sentiment.getSetiment(row.getCell(5).getStringCellValue());
				String category = null;
				if(row.getCell(8)!=null) {
					category = row.getCell(8).getStringCellValue();
					if(!category.equals(""))
						sentiment = Sentiment.NEGATIVE;
				}
				NLPItem temp = new NLPItem(modelName, id, origin, sentiment, category);
				listNLP.add(temp);
			}
		}
		return listNLP;
	}

	public static ArrayList<NLPItem> ReadFromExcel(String modelName,String filepath) {

		HSSFWorkbook wb = null;
		Log.i("{}--{}",modelName, filepath);
		FileInputStream fs;
		try {
			fs = new FileInputStream(filepath);
			POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
			wb = new HSSFWorkbook(ps);
			// System.out.println("file is reading");
			// sheet=wb.getSheetAt(0); //获取到工作表，因为一个excel可能有多个工作表
			// excel_line = sheet.getLastRowNum()+1;//从这行开始写 新的数据
			fs.close();

		} catch (FileNotFoundException e) {
			System.out.println("wrong file puth or file name!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<NLPItem> listNLP = ReadFromExcel(modelName,wb);
		return listNLP;
	}
	
	public static ArrayList<NLPItem> ReadFromExcel(MultipartFile file) {
		File f;
		ArrayList<NLPItem> nlpItemList = null;
		try {
			f = File.createTempFile("tmp", null);
			file.transferTo(f);
			String modelName = file.getOriginalFilename().substring(0, 5);
			nlpItemList = ReadFromExcel(modelName, f.getAbsolutePath());
			f.deleteOnExit();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nlpItemList;
	}

}
