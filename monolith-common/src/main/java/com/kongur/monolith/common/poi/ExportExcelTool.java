package com.kongur.monolith.common.poi;

import java.io.OutputStream;
import java.util.List;

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
     * ����excel
     * 
     * @param fields, �ֶ�����
     * @param datas, ����
     * @param out, �����
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

            if (fields != null) {
                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = null;
                for (int i = 0; i < fields.length; i++) {
                    cell = row.createCell((short)i);
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
