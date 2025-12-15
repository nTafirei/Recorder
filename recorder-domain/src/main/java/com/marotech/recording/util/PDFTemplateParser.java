package com.marotech.recording.util;

import com.marotech.recording.model.KeyValuePair;
import org.apache.commons.lang.Validate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFTemplateParser implements TemplateParser {

    @Override
    public List<KeyValuePair> parseTemplate(InputStream input) {
        Validate.notNull(input != null, "Input stream must not be null");

        return new ArrayList<KeyValuePair>();
    }
}
