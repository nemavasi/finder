package my.tests.max_finder_with_swagger.service;


import my.tests.max_finder_with_swagger.App;
import my.tests.max_finder_with_swagger.service.alg.SetOfNMax;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Component
@Primary
public class ExcelDataProducer implements DataProducer<Integer> {

    @Override
    public void resolveStream(String urlStore, SetOfNMax<Integer> set) {
        try (FileInputStream file = new FileInputStream(dangerousLoadFileFromOther(urlStore))) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case CellType.NUMERIC:
                        set.addItem((int) cell.getNumericCellValue());
                        break;
                    case CellType.STRING:
                        set.addItem(Integer.parseInt(cell.getStringCellValue()));
                        break;
                }
            }
        } catch (IOException e) {
            throw new WrongParameterException(e.getMessage());
        }
    }

    //todo check vulnerabilities and add additional checking
//    private void checkPath(String path) {
//        try {
//            Paths.get(path);
//        } catch (InvalidPathException e) {
//            throw new WrongParameterException("invalid path parameter");
//        }
//    }

    private File loadFileFromClasspath(String path)
            throws FileNotFoundException {
        return ResourceUtils.getFile(
                "classpath:" + path);
    }

    private File dangerousLoadFileFromOther(String path)
            throws FileNotFoundException {
        return ResourceUtils.getFile(
                "file:" + path);
    }

    private File loadFileFromJarDir(String path) throws FileNotFoundException {

        ApplicationHome home = new ApplicationHome(App.class);
        home.getDir();    // returns the folder where the jar is.
        home.getSource(); // returns the jar absolute path.

        return ResourceUtils.getFile(
                "file:" + home.getDir() + path);
    }
}
