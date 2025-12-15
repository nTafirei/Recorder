package com.marotech.recording.util;


import com.marotech.recording.model.KeyValuePair;

import java.io.InputStream;
import java.util.List;

public interface TemplateParser {

    List<KeyValuePair> parseTemplate(InputStream input);
}
