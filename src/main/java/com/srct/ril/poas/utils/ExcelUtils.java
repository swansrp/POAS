package com.srct.ril.poas.utils;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.NLPAnalysis.Item;



public class ExcelUtils {

	private static void wf(HSSFWorkbook wb)
	{
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("workbook1.xls");
			wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中   
			fileOut.close(); 
			System.out.println("write to excel done !");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("请先关闭excel文件!，然后运行");
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

	
	public static void NLP_WriteToExcel(Object object){
		
		HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
		HSSFSheet sheet = wb.createSheet("NLP_Analysis");
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
		int excelIndex=0;
		
		int NLPIndex = 0;
		//利用迭代器读取 list中的每个NLPAnalysis
		while(it.hasNext()&&NLPIndex<listNLP.size()) {
			
			
			boolean flag = false;
			//ArrayList<String> line = new ArrayList<String> ();//一行
			List<Item> itemlist = listNLP.get(NLPIndex).getItems();//获取这个NLPAnalysis的item的list
			Iterator<Item> itemit = itemlist.iterator();
			
	        //setStyle(cell[10][1], "TAN", HSSFColor.TAN.index); 
			
			
			int j=0;
			//循环NLPAnalysis中的items这个list
			while(itemit.hasNext()&&j<itemlist.size() ){
				

				HSSFRow row = sheet.createRow(excelIndex);
				HSSFCell cell0=row.createCell(0);
				cell0.setCellStyle(cellStyle);
				if(!flag)
				{
					//line.add(listNLP.get(rowIndex).getContent().toString()); 
					cell0.setCellValue(listNLP.get(NLPIndex).getContent().toString());
					flag =true;
				}
				else
				{
					cell0.setCellValue(" ");
				}
				
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
				case 0:
					cell4.setCellValue("消极");
					break;
				case 1:
					cell4.setCellValue("中性");
					break;
				case 2:
					cell4.setCellValue("积极");
					break;
				default:
					cell4.setCellValue("没啥说的");
				}
				cell4.setCellStyle(cellStyle);
//				line.add( itemlist.get(j).getSubContent().toString() );
//				line.add( itemlist.get(j).getProp().toString());
//				line.add( itemlist.get(j).getKey().toString());
				excelIndex++;
				j++;
				
			}//while(itemit.hasNext()便利item
			
			//excel_lines.add(line);
			NLPIndex++;
		}//while(it.hasNext()便利NLP
		
		wf(wb);

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
	
	
}
