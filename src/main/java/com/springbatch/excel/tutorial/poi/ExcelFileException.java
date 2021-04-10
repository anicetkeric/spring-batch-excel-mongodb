package com.springbatch.excel.tutorial.poi;

public class ExcelFileException extends RuntimeException {

    private final String message;

    public ExcelFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
