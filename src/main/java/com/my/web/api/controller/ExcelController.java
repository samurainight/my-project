package com.my.web.api.controller;


import com.my.web.api.util.ApiReponse;
import com.my.web.dto.StudentDto;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExcelController {

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //表格名称
        String fileName="text.xlsx".toString();
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle cellStyle=wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("成绩表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        cell.setCellStyle(cellStyle);
        sheet.setDefaultRowHeightInPoints(20);//设置缺省列高sheet.setDefaultColumnWidth(20);//设置缺省列宽
        //设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一
        //sheet.setColumnWidth(cell.getColumnIndex(), 256 * 50);
        //设置单元格内容
        cell.setCellValue("学员考试成绩一览表");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        //在sheet里创建第二行之后的表格数据
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置单元格内容
        for (int i=0;i<=3;i++){
            HSSFCell cell2=row2.createCell(i);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue("列"+i);
        }
        for (int i=1;i<=3;i++){
            HSSFRow row3=sheet.createRow(i+1);
            for (int j=0;j<=3;j++){
                HSSFCell cell3=row3.createCell(j);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue("属性"+i+""+j);
            }
        }
        OutputStream output=null;
        try {
            output=response.getOutputStream();
            response.reset();
            //response.setHeader("Content-disposition", "attachment; filename=details.xls");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/import/excel")
    public ApiReponse importExcel(@RequestParam("file")MultipartFile file) throws IOException{
        List<StudentDto> list=new ArrayList<>();
        Workbook wb=new HSSFWorkbook(file.getInputStream());
        //获取excel文档中的第一个表单
        Sheet sheet=wb.getSheetAt(0);
        //对表单中的每一行进行迭代
        for (Row row:sheet){
            //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
            if(row.getRowNum()<1){
                continue;
            }
            StudentDto studentDto=new StudentDto();
            studentDto.setName(row.getCell(0).getStringCellValue());
            studentDto.setClassName(row.getCell(1).getStringCellValue());
            studentDto.setiScore(row.getCell(2).getStringCellValue());
            studentDto.setRScore(row.getCell(3).getStringCellValue());
            list.add(studentDto);
        }
        ApiReponse api=new ApiReponse();
        api.setData(list);
        return api;
    }
}
