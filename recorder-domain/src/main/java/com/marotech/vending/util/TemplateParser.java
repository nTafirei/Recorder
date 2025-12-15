package com.marotech.vending.util;


import com.marotech.vending.model.KeyValuePair;

import java.io.InputStream;
import java.util.List;

public interface TemplateParser {

    List<KeyValuePair> parseTemplate(InputStream input);
}
