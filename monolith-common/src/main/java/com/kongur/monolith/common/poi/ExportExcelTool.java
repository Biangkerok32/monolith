package com.kongur.monolith.common.poi;

import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ����excel������
 * 
 * @author zhengwei
 */
public class ExportExcelTool {

    private static final Logger log = Logger.getLogger(ExportExcelTool.class);

    /**
     * ����excel�� ����Ҫ���ļ�����
     * 
     * @param fields �ֶ�����
     * @param datas ����
     * @param response HttpServletResponse
     * @param callback
     * @return
     * @throws ExportException
     */
    public static <T> boolean export(String[] fields, List<T> datas, HttpServletResponse response,
                                     ExportCallback<T> callback) throws ExportException {

        String fileName = UUID.randomUUID().toString();
        return export(fileName, fields, datas, response, callback);

    }

    /**
     * ����excel��HttpServletResponse
     * 
     * @param fileName�� �ļ�����
     * @param fields, �ֶ�����
     * @param datas, ����
     * @param response, HttpServletResponse
     * @param callback
     * @return
     * @throws ExportException
     */
    public static <T> boolean export(String fileName, String[] fields, List<T> datas, HttpServletResponse response,
                                     ExportCallback<T> callback) throws ExportException {

        boolean success = false;

        try {
            response.setHeader("Content-disposition", "attachment; filename="
                                                      + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
            response.setContentType("application/msexcel");// �����������

            success = export(fields, datas, response.getOutputStream(), callback);

        } catch (Exception e) {
            log.error("export excel error", e);
            throw new ExportException(e);
        }

        return success;
    }

    /**
     * ����excel
     * 
     * @param fields, �ֶ�����
     * @param datas, ����
     * @param out, ����������Ե������ļ���response��
     * @param callback
     * @return
     * @throws ExportException
     */
    public static <T> boolean export(String[] fields, List<T> datas, OutputStream out, ExportCallback<T> callback)
                                                                                                                  throws ExportException {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            // ��workbook.setSheetName(0,sheetName,HSSFWorkbook.ENCODING_UTF_16);

            if (fields != null && fields.length > 0) {
                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = null;
                for (int i = 0; i < fields.length; i++) {
                    cell = row.createCell((short) i);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    // cell.setCellStyle(HSSFCellStyle.ALIGN_CENTER);
                    cell.setCellValue(fields[i]);
                }
            }

            if (datas != null && !datas.isEmpty()) {

                for (int i = 0; i < datas.size(); i++) {
                    HSSFRow row = sheet.createRow(i + 1); // ���ݴӵ�2�п�ʼ

                    callback.fillRowData(row, datas.get(i));
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            log.error("export excel error", e);
            throw new ExportException(e);
        }

        return true;
    }

}
