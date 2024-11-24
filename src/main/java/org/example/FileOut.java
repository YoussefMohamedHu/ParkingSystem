package org.example;

import java.io.FileWriter;
import java.io.IOException;

public  class FileOut {
    static FileWriter writer;

    static {
        try {
            writer = new FileWriter("output.txt",true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

synchronized static void Print(String str) throws IOException {
        writer.write(str);
        writer.write(System.lineSeparator());
        writer.flush();
    }
}
