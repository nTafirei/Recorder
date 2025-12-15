package com.marotech.vending.action.converters;

import com.marotech.vending.model.Org;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Locale;

public class OrgConverter extends ConverterBase implements TypeConverter<Org> {

    @Override
    public Org convert(String input, Class<? extends Org> targetType,
                       Collection<ValidationError> errors) {

        if (StringUtils.isBlank(input)) {
            errors.add(new LocalizableError("orgnotfound"));
            return null;
        }

        errors.add(new LocalizableError("orgnotfound", input));
        return null;
    }

    @Override
    public void setLocale(Locale locale) {
    }

}
