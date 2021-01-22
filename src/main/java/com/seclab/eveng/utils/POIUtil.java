package com.seclab.eveng.utils;

/**
 * @program: eveng
 * @description: POI 读取  word
 * @author: Icey
 * @create: 2020-12-09 10:30
 **/

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class POIUtil {
    public static List<String> readWord(String filePath) throws Exception{

        List<String> linList = new ArrayList<String>();
        String buffer = "";
        try {
            if (filePath.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(filePath));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                               //ex.close();

                if(buffer.length() > 0){
                    //使用回车换行符分割字符串
                    String [] arry = buffer.split("\\r\\n");
                    for (String string : arry) {
                        linList.add(string.trim());
                    }
                }
            } else if (filePath.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();

                //extractor.close();

                if(buffer.length() > 0){
                    //使用换行符分割字符串
                    String [] arry = buffer.split("\\n");
                    for (String string : arry) {
                        linList.add(string.trim());
                    }
                }
            } else {
                return null;
            }

            return linList;
        } catch (Exception e) {
            System.out.print("error---->"+filePath);
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String args[]){
        String path = "/home/icey/Desktop/projectTestData/experiment1.doc";
        try{
            List<String> linList = readWord(path);
            System.out.println(linList);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}