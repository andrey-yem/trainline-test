package com.trainlinetest.trainlinetest;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TSVWriterTest {

    private void runMultiRowTest(List<String []> rowsToStore, String expectedOutput) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TSVWriter TSVWriter = new TSVWriter(new OutputStreamWriter(outputStream));
        for (String[] row : rowsToStore) {
            TSVWriter.writeRow(row);
        }
        TSVWriter.close();
        String output = outputStream.toString();
        assertEquals(expectedOutput, output);
    }

    // Convenience method
    private void runSingleRowTest(String [] rowToStore, String expectedOutput) throws Exception {
        runMultiRowTest(Arrays.asList(new String[][]{rowToStore}), expectedOutput);
    }

    @Test
    public void writeSingleRow() throws Exception {
        String[] values = {"value1", "value2", "value3"};
        String expectedOutput = "value1\tvalue2\tvalue3\n";
        runSingleRowTest(values, expectedOutput);
    }

    @Test
    public void writeMultipleRows() throws Exception {
        List<String []> values = Arrays.asList(
                new String[] {"value1", "value2"},
                new String[] {"value3", "value4"});
        String expectedOutput = "value1\tvalue2\nvalue3\tvalue4\n";
        runMultiRowTest(values, expectedOutput);
    }

    @Test
    public void writeEmptyString() throws Exception {
        String expectedOutput = "\n";
        runSingleRowTest(null, expectedOutput);
    }
}