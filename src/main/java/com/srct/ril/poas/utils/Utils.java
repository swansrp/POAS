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

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.NLPAnalysis.Item;



public class Utils {
	
	public static void NLP_WriteToExcel(Object object){
		
		HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象  
		HSSFSheet sheet = wb.createSheet("NLP_Analysis");
		HSSFCellStyle cellStyle=wb.createCellStyle(); 
		cellStyle.setShrinkToFit(true);
		cellStyle.setWrapText(true);
		
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
				cell1.setCellValue(itemlist.get(j).getSubContent().toString());
				cell1.setCellStyle(cellStyle);
				
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellValue(itemlist.get(j).getProp().toString());
				cell2.setCellStyle(cellStyle);
				
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellValue(itemlist.get(j).getCategory().toString());
				cell3.setCellStyle(cellStyle);
				
//				line.add( itemlist.get(j).getSubContent().toString() );
//				line.add( itemlist.get(j).getProp().toString());
//				line.add( itemlist.get(j).getKey().toString());
				
				 j++;
				
			}//while(itemit.hasNext()便利item
			
			//excel_lines.add(line);
			NLPIndex++;
		}//while(it.hasNext()便利NLP
		

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("workbook1.xls");
			wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中   
			fileOut.close(); 
			System.out.println("OK!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
 
		

	}
	
	
	
	
	public static void WriteToExcel(Object object) throws IOException{
		
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
			//cell.setCellStyle(cellStyle);
			cell.setCellValue(listarray.get(Index).toString());
			cell.setCellStyle(cellStyle);
			System.out.println(listarray.get(Index).toString());
			Index++;
		}
		
		FileOutputStream fileOut = new FileOutputStream("workbook1.xls");  
		wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中   
		fileOut.close();  
		
		System.out.println("OK!");
		
	}
	
	
}
