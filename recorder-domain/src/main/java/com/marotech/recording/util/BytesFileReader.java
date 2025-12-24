package com.marotech.recording.util;

import java.io.IOException;
import java.io.InputStream;

public class BytesFileReader {

    public byte[] readFileFromClasspathAsBytes(String fileName) throws IOException {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new IOException("Resource not found: " + fileName);
            }
            return in.readAllBytes();
        }
    }
}
