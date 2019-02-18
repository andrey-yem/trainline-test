package com.trainlinetest.trainlinetest;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TSVReaderTest {

    private void runMultiRowTest(String inputString, List<String []> expectedValues) throws Exception {
        InputStreamReader inputStream = new InputStreamReader(new ByteArrayInputStream(inputString.getBytes()));
        TSVReader TSVReader = new TSVReader(inputStream);
        List<String[]> actualRows = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < expectedValues.size(); rowIdx++) {
            actualRows.add(TSVReader.readRow());
        }
        TSVReader.close();

        int rowsCount = expectedValues.size();
        assertEquals(rowsCount, actualRows.size());
        for (int rowIdx = 0; rowIdx < rowsCount; rowIdx++) {
            int columnsCount = expectedValues.get(rowIdx).length;
            assertEquals(columnsCount, actualRows.get(rowIdx).length);
            for (int columnIdx = 0; columnIdx < columnsCount; columnIdx++) {
                assertEquals(expectedValues.get(rowIdx)[columnIdx], actualRows.get(rowIdx)[columnIdx]);
            }
        }
    }

    // Convenience method
    private void runSingleRowTest(String inputString, String [] expectedValues) throws Exception {
        runMultiRowTest(inputString, Arrays.asList(new String [][] {expectedValues}));
    }

    @Test
    public void readSingleRow() throws Exception {
        String testInput = "value1\tvalue2\tvalue3";
        String [] expectedValues = {"value1", "value2", "value3"};
        runSingleRowTest(testInput, expectedValues);
    }

    @Test
    public void readMultipleRows() throws Exception {
        String testInput = "value1\tvalue2\nvalue3\tvalue4";
        List<String []> expectedRows = Arrays.asList(
                new String[] {"value1", "value2"},
                new String[] {"value3", "value4"});
        runMultiRowTest(testInput, expectedRows);
    }

    @Test
    public void readEmptyValue() throws Exception {
        String testInput = "value1\t\tvalue3";
        String [] expectedValues = {"value1", "", "value3"};
        runSingleRowTest(testInput, expectedValues);
    }
}