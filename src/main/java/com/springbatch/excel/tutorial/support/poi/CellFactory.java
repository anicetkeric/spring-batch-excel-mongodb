package com.springbatch.excel.tutorial.support.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.EnumMap;
import java.util.function.Function;

/**
 * Extract cell value
 * @author aek
 */
public class CellFactory {

    private static final EnumMap<CellType, Function<Cell,Object>>
            EXCEL_CELL_VALUE = new EnumMap<>(CellType.class);

    static {
        EXCEL_CELL_VALUE.put(CellType.BLANK, null);
        EXCEL_CELL_VALUE.put(CellType.FORMULA, Cell::getCellFormula);
        EXCEL_CELL_VALUE.put(CellType.BOOLEAN, Cell::getBooleanCellValue);
        EXCEL_CELL_VALUE.put(CellType.STRING, Cell::getStringCellValue);
        EXCEL_CELL_VALUE.put(CellType.ERROR, Cell::getErrorCellValue);
        EXCEL_CELL_VALUE.put(CellType.NUMERIC, cell -> {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        });
    }


    /**
     * @param cell Row cell
     * @return Object row
     */
    public Object getCellValue(Cell cell) {
        try{
            if(cell == null){
                return null;
            }
            return EXCEL_CELL_VALUE.get(cell.getCellType()).apply(cell);
        }catch (Exception e){
            throw new UnknownCellTypeException(String.format("Unknown cell type ( %s )",cell.getCellType()));
        }
    }

}
