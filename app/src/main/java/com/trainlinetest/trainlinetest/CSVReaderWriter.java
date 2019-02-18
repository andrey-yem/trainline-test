package com.trainlinetest.trainlinetest;

/**
 * Created by andrey on 07/12/2017.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * This class is deprecated. Use {@link TSVReader} and {@link TSVWriter} instead.
 */
@Deprecated
public class CSVReaderWriter {
    private TSVReader tsvReader = null;
    private TSVWriter tsvWriter = null;

    /* 1. Better to have 2 separate simple classes - one for each job: one for read; another for write
     * rather than having one class with many parameters. Then you can remove this enum
     *
     * 2. Why do you need Mode from int (can't you use ENUM values straightaway).
     * If you still do need int -> Mode then use factory method which will do validity checks
     */
    public enum Mode {
        Read(1), Write(2);

        private final int _mode;

        @Deprecated
        Mode(int mode) {
            this._mode = mode;
        }

        public static Mode fromInteger(int x) {
            switch (x) {
                case 1:
                    return Read;
                case 2:
                    return Write;
            }
            return null;
        }

        public int getMode() {
            return _mode;
        }
    }

    public void open(String fileName, Mode mode) throws Exception {
        close(); // cleanup resources first

        if (mode == Mode.Read) {
            tsvReader = new TSVReader(new InputStreamReader(new FileInputStream(fileName)));
        } else if (mode == Mode.Write) {
            tsvWriter = new TSVWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        } else {
            throw new Exception("Unknown file mode for " + fileName);
        }
    }

    public void close() throws IOException {
        if (tsvReader != null) {
            tsvReader.close();
            tsvReader = null;
        }
        if (tsvWriter != null) {
            tsvWriter.close();
            tsvWriter = null;
        }
    }

    public void write(String... columns) throws IOException {
        if (tsvWriter == null) {
            throw new IllegalStateException("You need to open CSVReaderWriter for WRITE first");
        }
        tsvWriter.writeRow(columns);
    }

    public boolean read(String[] columns) throws IOException {
        final int columnsToRead = 2;
        if (tsvReader == null) {
            throw new IllegalStateException("You need to open CSVReaderWriter for READ first");
        }
        if (columns == null || columns.length < columnsToRead) {
            throw new IllegalArgumentException(String.format("You need to provide an array for at least %d String values", columnsToRead));
        }

        String[] valuesFromTsv = tsvReader.readRow();
        if (valuesFromTsv == null || valuesFromTsv.length < columnsToRead) {
            for (int columnIdx = 0; columnIdx < columnsToRead; columnIdx++) {
                columns[columnIdx] = null;
            }
            return false;
        }

        for (int columnIdx = 0; columnIdx < columnsToRead; columnIdx++) {
            columns[columnIdx] = valuesFromTsv[columnIdx];
        }
        return true;
    }

    /* NOTE: The return value would be consistent with read(String []) function, but it
     * won't change column parameters
     */
    public boolean read(String column1, String column2) throws IOException {
        return read(new String[2]);
    }
}
