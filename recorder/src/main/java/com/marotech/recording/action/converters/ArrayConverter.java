package com.marotech.recording.action.converters;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ArrayConverter implements TypeConverter<Set<String>> {

    @Override
    public Set<String> convert(String input,
                               Class<? extends Set<String>> targetType,
                               Collection<ValidationError> errors) {

        if (StringUtils.isBlank(input)) {
            return new HashSet<String>();
        }

        Set<String> out = new HashSet<String>();
        String[] all = input.split(",");
        for (String str : all) {
            out.add(str);
        }
        return out;
    }

    @Override
    public void setLocale(Locale locale) {
    }

}
