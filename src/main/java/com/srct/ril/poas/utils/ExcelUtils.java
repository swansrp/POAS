package com.srct.ril.poas.utils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;

import com.mchange.rmi.Checkable;
import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.NLPAnalysis.Item;
import com.srct.ril.poas.ai.NLPItem;
import com.srct.ril.poas.utils.log.Log;




public class ExcelUtils {
	
	
	private static String baseDir = "src/main/webapp/";
//	public static void main(String[] args) {
//		ArrayList<String> arrayList = new ArrayList<String>();
//		arrayList.add("lalalla");
//		arrayList.add("lbbb");
//		arrayList.add("lacccla");
//		arrayList.add("ldd");
//		WriteToExcel(arrayList);
//		
//	}
	
	public static void DelayWriteFile(HSSFWorkbook o,int time){
		 try {
			Thread.sleep(time);
			wf(o);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	private static void wf(HSSFWorkbook wb)
	{
		FileOutputStream fileOut;
		
			try {
				fileOut = new FileOutputStream("src/main/webapp/workbook1.xls");
				wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中   
				fileOut.close(); 
				System.out.println("write to excel done !");
				
			} catch (FileNotFoundException e) {

				System.out.println("请先关闭excel文件，然后稍等!");
				DelayWriteFile(wb,5000);

			} catch (IOException e) {

				e.printStackTrace();
				
			} 
		
	}
	

	private static void wf(HSSFWorkbook wb,String filename)
	{
		FileOutputStream fileOut;
		String puth = "src/main/webapp/"+filename+".xls";
		System.out.print(puth);
			try {
				fileOut = new FileOutputStream(puth);
				wb.write(fileOut);//把Workbook对象输出到文件puth中   
				fileOut.close(); 
				System.out.println("write to excel done !");
				
			} catch (FileNotFoundException e) {

				System.out.println("请先关闭excel文件，然后稍等!");
				DelayWriteFile(wb,5000);

			} catch (IOException e) {

				e.printStackTrace();
				
			} 
		
	}

	public static HSSFWorkbook NLPItem_WriteToExcel(Object object){
		
		
		HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
		HSSFSheet sheet = wb.createSheet("NLP_Item");

		
		sheet.setColumnWidth(0, 5 * 512);//ID
		sheet.setColumnWidth(1, 15 * 512);//Time duration
		sheet.setColumnWidth(2, 5 * 512);//Origin
		sheet.setColumnWidth(3, 35 * 512);//comment
		sheet.setColumnWidth(4, 5 * 512);//sentiment
		sheet.setColumnWidth(5, 5 * 512);//category
		sheet.setColumnWidth(6, 15 * 512);//URL
		HSSFCellStyle cellStyle=wb.createCellStyle(); 
		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(BorderStyle.THIN);//下边框        
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框        
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框        
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框 
		
		@SuppressWarnings("unchecked")
		ArrayList<NLPItem> listNLP = (ArrayList<NLPItem>) object;
		Iterator<NLPItem> it = listNLP.iterator();


		int NLPIndex = 0;
		HSSFRow row0 = sheet.createRow(NLPIndex++);
		//=================
		HSSFCell cell00=row0.createCell(0);
		cell00.setCellStyle(cellStyle);
		cell00.setCellValue("ID");
		
		HSSFCell cell10=row0.createCell(1);
		cell10.setCellStyle(cellStyle);
		cell10.setCellValue("Time duration");
		
		HSSFCell cell20=row0.createCell(2);
		cell20.setCellStyle(cellStyle);
		cell20.setCellValue("Origin");
		
		HSSFCell cell30=row0.createCell(3);
		cell30.setCellStyle(cellStyle);
		cell30.setCellValue("comment");
		
		HSSFCell cell40=row0.createCell(4);
		cell40.setCellStyle(cellStyle);
		cell40.setCellValue("Sentiment");
		
		HSSFCell cell50=row0.createCell(5);
		cell50.setCellStyle(cellStyle);
		cell50.setCellValue("Category");
		
		HSSFCell cell60=row0.createCell(6);
		cell60.setCellStyle(cellStyle);
		cell60.setCellValue("Url");
		//=================
		//利用迭代器读取 list中的每个NLPAnalysis
		while(it.hasNext()&&NLPIndex<listNLP.size()) {
			
			HSSFRow row = sheet.createRow(NLPIndex);
			
			HSSFCell cell9=row.createCell(0);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue(listNLP.get(NLPIndex-1).getId());
			
			HSSFCell cell0=row.createCell(1);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue(listNLP.get(NLPIndex-1).getTimestamp());
			
			HSSFCell cell1=row.createCell(2);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue(listNLP.get(NLPIndex-1).getOrigin());
			
			HSSFCell cell2=row.createCell(3);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(listNLP.get(NLPIndex-1).getFirstcomment());
			
			HSSFCell cell3=row.createCell(4);
			cell3.setCellStyle(cellStyle);
			switch(listNLP.get(NLPIndex-1).getSentiment()){
			case NEGATIVE:
				cell3.setCellValue("消极");
				break;
			case NEUTRAL:
				cell3.setCellValue("中性");
				break;
			case POSITIVE:
				cell3.setCellValue("积极");
				break;
			default:
				cell3.setCellValue("没啥说的");
			}
			
			
			HSSFCell cell5=row.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue(listNLP.get(NLPIndex).getCategory());
			
			HSSFCell cell6=row.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue(listNLP.get(NLPIndex-1).getLink());
			
			NLPIndex++;
		}

		return wb;
	}
	
	
	public static HSSFWorkbook NLPItem_WriteToExcel(Object object, String filename){
		
		String puth = baseDir +filename+".xls";
		int excel_line = 0;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		
		FileInputStream fs;
		try {
			fs = new FileInputStream(puth);
			POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        wb=new HSSFWorkbook(ps);    
	        sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
	        excel_line = sheet.getLastRowNum()+1;//从这行开始写 新的数据
	        fs.close();
		
		} catch (FileNotFoundException e) {
			wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
			sheet = wb.createSheet("NLP_Item");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
          
		
		
		
		sheet.setColumnWidth(0, 5 * 512);//ID
		sheet.setColumnWidth(1, 15 * 512);//Time duration
		sheet.setColumnWidth(2, 5 * 512);//Origin
		sheet.setColumnWidth(3, 35 * 512);//comment
		sheet.setColumnWidth(4, 5 * 512);//sentiment
		sheet.setColumnWidth(5, 5 * 512);//category
		sheet.setColumnWidth(6, 15 * 512);//URL
		
		
		HSSFCellStyle cellStyle=wb.createCellStyle(); 
		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(BorderStyle.THIN);//下边框        
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框        
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框        
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框 
		
		@SuppressWarnings("unchecked")
		ArrayList<NLPItem> listNLP = (ArrayList<NLPItem>) object;
		Iterator<NLPItem> it = listNLP.iterator();


		int NLPIndex = 0;
		HSSFRow row0 = sheet.createRow(excel_line++);
		//=================
		HSSFCell cell00=row0.createCell(0);
		cell00.setCellStyle(cellStyle);
		cell00.setCellValue("ID");
		
		HSSFCell cell10=row0.createCell(1);
		cell10.setCellStyle(cellStyle);
		cell10.setCellValue("Time duration");
		
		HSSFCell cell20=row0.createCell(2);
		cell20.setCellStyle(cellStyle);
		cell20.setCellValue("Origin");
		
		HSSFCell cell30=row0.createCell(3);
		cell30.setCellStyle(cellStyle);
		cell30.setCellValue("comment");
		
		HSSFCell cell40=row0.createCell(4);
		cell40.setCellStyle(cellStyle);
		cell40.setCellValue("Sentiment");
		
		HSSFCell cell50=row0.createCell(5);
		cell50.setCellStyle(cellStyle);
		cell50.setCellValue("Category");
		
		HSSFCell cell60=row0.createCell(6);
		cell60.setCellStyle(cellStyle);
		cell60.setCellValue("Url");
		
		//=================
		//利用迭代器读取 list中的每个NLPAnalysis
		while(it.hasNext()&&NLPIndex<listNLP.size()) {
			
			HSSFRow row = sheet.createRow(excel_line++);
			
			HSSFCell cell0=row.createCell(0);
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue(listNLP.get(NLPIndex).getId());
			
			HSSFCell cell1=row.createCell(1);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue(listNLP.get(NLPIndex).getTimestamp());
			
			HSSFCell cell2=row.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(listNLP.get(NLPIndex).getOrigin());
			
			HSSFCell cell3=row.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(listNLP.get(NLPIndex).getFirstcomment());
			
			HSSFCell cell4=row.createCell(4);
			cell4.setCellStyle(cellStyle);
			switch(listNLP.get(NLPIndex).getSentiment()){
			case NEGATIVE:
				cell4.setCellValue("消极");
				break;
			case NEUTRAL:
				cell4.setCellValue("中性");
				break;
			case POSITIVE:
				cell4.setCellValue("积极");
				break;
			default:
				cell4.setCellValue("Unknown");
			}
			
			HSSFCell cell5=row.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue(listNLP.get(NLPIndex).getCategory());
			
			HSSFCell cell6=row.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue(listNLP.get(NLPIndex).getLink());
			
			NLPIndex++;
		}

		return wb;
	}
	
	

	/*
	 * flag is false , items will be ignored
	 * flag is true , items will be printed to excel file
	*/
	public static HSSFWorkbook NLP_WriteToExcel(Object object, boolean f,String filename){
		
		
		String puth = baseDir +filename+".xls";
		int excelIndex = 0;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		
		FileInputStream fs;
		try {
			fs = new FileInputStream(puth);
			POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
	        wb=new HSSFWorkbook(ps);    
	        sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
	        excelIndex = sheet.getLastRowNum()+1;//从这行开始写 新的数据
	        System.out.println("continue output at excel index : "+excelIndex);
	        fs.close();
		
		} catch (FileNotFoundException e) {
			wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
			sheet = wb.createSheet("NLP_Item");
			System.out.println("建立新表");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
          
		
		
		
		sheet.setColumnWidth(0, 25 * 512);//第一列宽度
		sheet.setColumnWidth(1, 15 * 512);//第二列宽度
		sheet.setColumnWidth(3,10 * 512);//第四列宽度
		HSSFCellStyle cellStyle=wb.createCellStyle(); 
		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(BorderStyle.THIN);//下边框        
		cellStyle.setBorderLeft(BorderStyle.THIN);//左边框        
		cellStyle.setBorderRight(BorderStyle.THIN);//右边框        
		cellStyle.setBorderTop(BorderStyle.THIN);//上边框 
		
		@SuppressWarnings("unchecked")
		ArrayList<NLPAnalysis> listNLP = (ArrayList<NLPAnalysis>) object;
		Iterator<NLPAnalysis> it = listNLP.iterator();
		
		
		//ArrayList<ArrayList<String>> excel_lines = new ArrayList<ArrayList<String>>();
		
		
		int NLPIndex = 0;
		//利用迭代器读取 list中的每个NLPAnalysis
		while(it.hasNext()&&NLPIndex<listNLP.size()) {
			
			
	//---------------------------------------------------------------------
			//每个NLPAnalysis句子的第一行
			HSSFRow row1 = sheet.createRow(excelIndex++);
			HSSFCell cell00=row1.createCell(0);
			cell00.setCellStyle(cellStyle);
			//Log.ii(listNLP);
			cell00.setCellValue(listNLP.get(NLPIndex).getContent());
			
	///--------------------------------------------------------------------		
			
			
			if(f){
				List<Item> itemlist = listNLP.get(NLPIndex).getItems();//获取这个NLPAnalysis的item的list
				Iterator<Item> itemit = itemlist.iterator();

				
				int j=0;
				//循环NLPAnalysis中的items这个list
				while(itemit.hasNext()&&j<itemlist.size() ){
					
	
					HSSFRow row = sheet.createRow(excelIndex);
					HSSFCell cell0=row.createCell(0);
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
					switch(itemlist.get(j).getSentiment()){
					case NEGATIVE:
						cell4.setCellValue("消极");
						break;
					case NEUTRAL:
						cell4.setCellValue("中性");
						break;
					case POSITIVE:
						cell4.setCellValue("积极");
						break;
					default:
						cell4.setCellValue("Unknown");
					}
					cell4.setCellStyle(cellStyle);

					excelIndex++;
					j++;
					
				}//while(itemit.hasNext()便利item
				//excel_lines.add(line);
			}//if(f)
			
			NLPIndex++;
		}//while(it.hasNext()便利NLP
		
		wf(wb,filename);
		return wb;

	}
	
	
	public static void WriteToExcel(Object object) {
		
		@SuppressWarnings("unchecked")
		ArrayList<String> listarray = (ArrayList<String>) object;
		System.out.println(listarray.size());
		
		HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
		HSSFSheet sheet = wb.createSheet("NLP_Analysis");
		HSSFCellStyle cellStyle=wb.createCellStyle(); 
		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		
		
		Iterator<String> it = listarray.iterator();
		int Index = 0;
		
		
		while(it.hasNext()&&Index<listarray.size()) {
			HSSFRow row = sheet.createRow(Index);
			HSSFCell cell=row.createCell(0);
			cell.setCellValue(listarray.get(Index).toString());
			cell.setCellStyle(cellStyle);
			System.out.println(listarray.get(Index).toString());
			Index++;
		}
		
		wf(wb);
		
	}
	
	
	public static String FindLatestResultFilenameForSession(String sessionname) {
		String filename = "";
		
		return filename;
		
	}
	
}
