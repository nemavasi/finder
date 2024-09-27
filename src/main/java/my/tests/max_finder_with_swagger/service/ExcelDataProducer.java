package my.tests.max_finder_with_swagger.service;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

@Component
@Primary
public class ExcelDataProducer implements DataProducer<Integer> {

    @Override
    public Stream<Integer> resolveStream(String urlStore) {
//        checkPath();
        int[] numbers;
        try (FileInputStream file = new FileInputStream(urlStore)) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            numbers = new int[sheet.getLastRowNum() + 1];
            int counter = 0;
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case CellType.NUMERIC:
                        numbers[counter] = ((int) cell.getNumericCellValue());
                        break;
                    case CellType.STRING:
                        numbers[counter] = (Integer.parseInt(cell.getStringCellValue()));
                        break;
                }
                counter++;
            }

            return Arrays.stream(numbers).boxed();
        } catch (IOException e) {
            throw new WrongParameterException(e.getMessage());
        }
    }

    //todo check vulnerabilities and add additional checking
//    private void checkPath() {
//        try {
//            Paths.get(path);
//
//        } catch (InvalidPathException e) {
//            throw new WrongParameterException("invalid path parameter");
//        }
//    }

//    private File loadFileFromClasspath(String path)
//            throws FileNotFoundException {
//        return ResourceUtils.getFile(
//                "classpath:" + path);
//    }
}
