package com.trainlinetest.trainlinetest;

/**
 * Created by andrey on 07/12/2017.
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TSVWriter {

    private final static String SEPARATOR = "\t";
    private final static String NEWLINE = "\n";

    private BufferedWriter _bufferedWriter = null;

    public TSVWriter(OutputStreamWriter outputStream) {
        _bufferedWriter = new BufferedWriter(outputStream);
    }

    public void close() throws IOException {
        if (_bufferedWriter != null) {
            _bufferedWriter.close();
            _bufferedWriter = null;
        }
    }

    public void writeRow(String... columns) throws IOException {
        if (_bufferedWriter == null) {
            throw new IllegalStateException("Can't write to TSVWriter after it was closed");
        }

        StringBuilder builder = new StringBuilder("");
        if (columns != null) {
            String savedSeparator = "";
            for (String column : columns) {
                builder.append(savedSeparator);
                builder.append(column);
                savedSeparator = SEPARATOR;
            }
        }
        writeLine(builder.toString());
    }

    private void writeLine(String line) throws IOException {
        _bufferedWriter.write(line + NEWLINE);
    }
}
