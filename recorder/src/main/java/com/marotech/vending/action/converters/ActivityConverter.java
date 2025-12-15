package com.marotech.vending.action.converters;

import com.marotech.vending.model.Activity;
import com.marotech.vending.service.RepositoryService;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Locale;

public class ActivityConverter implements TypeConverter<Activity> {

    @SpringBean
    private RepositoryService objRepository;

    @Override
    public Activity convert(String input, Class<? extends Activity> targetType,
                            Collection<ValidationError> errors) {

        if (StringUtils.isBlank(input)) {
            errors.add(new LocalizableError("activitynotfound"));
            return null;
        }
        Activity obj = objRepository.fetchActivityById(input);

        if (obj == null) {
            errors.add(new LocalizableError("activitynotfound", input));
            return null;
        }

        return obj;
    }

    @Override
    public void setLocale(Locale locale) {
    }

}
