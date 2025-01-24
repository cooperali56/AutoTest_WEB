package gmmtest.tools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 * 读取excel测试用例数据
 */
public class ExcelUtils {

    public static void main(String[] args) {
        File relative = new File("");
        String fileName = "gmmh5test.xlsx";
        String sheetName = "login";
        String path = relative.getAbsolutePath() + File.separator + "\\src\\main\\java\\datas\\" + fileName;
        Object[][] dataA = getExcelData(path, fileName, sheetName, 3, 4, 6, 7);
        for (Object[] data : dataA) {
            for (Object o : data) {
                System.out.println((String) o);
            }
        }
    }

    /**
     * 返回单个单元格内容
     *
     * @param fileName  文件名
     * @param sheetName 工作表
     * @param Row       哪行
     * @param Col       哪列
     * @return
     */
    public static String getExcelCell(String fileName, String sheetName, int Row, int Col) {
        File relative = new File("");
        String path = relative.getAbsolutePath() + File.separator + "\\testcase\\" + fileName;
        Object[][] data = getExcelData(path, fileName, sheetName, Row, Row, Col, Col);
        String s = null;
        for (Object[] objects : data) {
            for (Object object : objects) {
                s = (String) object;
            }
        }
        return s;
    }

    /**
     * 返回多个单元格集合
     *
     * @param fileName   文件名
     * @param sheetName  工作表
     * @param statRow    起始行
     * @param endRow     结束行
     * @param statColumn 起始列
     * @param endColumn  结束列
     * @return
     */
    public static Object[][] getExcelObject(String fileName, String sheetName, int statRow, int endRow, int statColumn, int endColumn) {
        File relative = new File("");
        String path = relative.getAbsolutePath() + File.separator + "\\testcase\\" + fileName;
        return getExcelData(path, fileName, sheetName, statRow, endRow, statColumn, endColumn);
    }

    /**
     * 返回指定excel数据
     *
     * @param path       文件地址
     * @param fileName   文件名
     * @param sheetName  工作表名
     * @param sheetName  起始行
     * @param endRow     结束行
     * @param statColumn 起始列
     * @param endColumn  结束列
     * @return
     */
    private static Object[][] getExcelData(String path, String fileName, String sheetName, int statRow, int endRow, int statColumn, int endColumn) {
        try {
            //创建一个工作簿对象
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = null;
            String fileExtensionName = fileName.substring(fileName.indexOf("."));
            if (fileExtensionName.equals(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (fileExtensionName.equals(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            }
            //获取表单对象 getSheetAt(Sheet)
            org.apache.poi.ss.usermodel.Sheet sheets = workbook.getSheet(sheetName);
            //创建保存数据
            Object[][] datas = new Object[endRow - statRow + 1][endColumn - statColumn + 1];
            //循环得到数据
            for (int i = statRow; i <= endRow; i++) {
                //拿一行
                Row row = sheets.getRow(i - 1);
                //循环所有列
                for (int j = statColumn; j <= endColumn; j++) {
                    //取出一列
                    Cell cell = row.getCell(j - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    //取出列的值
                    String value = cell.getStringCellValue();
                    //保存数据
                    datas[i - statRow][j - statColumn] = value;
                }
            }
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}