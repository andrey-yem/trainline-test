package com.trainlinetest.trainlinetest;

/**
 * Created by andrey on 07/12/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TSVReader {

    private final static String SEPARATOR = "\t";

    private BufferedReader _bufferedReader = null;

    public TSVReader(InputStreamReader inputStream) {
        _bufferedReader = new BufferedReader(inputStream);
    }

    public void close() throws IOException {
        if (_bufferedReader != null) {
            _bufferedReader.close();
            _bufferedReader = null;
        }
    }

    public String[] readRow() throws IOException {
        if (_bufferedReader == null) {
            throw new IllegalStateException("Can't read from TSVReader after it was closed");
        }

        String line = readLine();
        if (line == null) {
            return null;
        }
        return line.split(SEPARATOR);
    }

    private String readLine() throws IOException {
        return _bufferedReader.readLine();
    }
}
