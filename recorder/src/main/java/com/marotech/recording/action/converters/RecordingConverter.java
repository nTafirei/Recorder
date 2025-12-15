package com.marotech.recording.action.converters;

import com.marotech.recording.model.Recording;
import com.marotech.recording.service.RepositoryService;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Locale;

public class RecordingConverter implements TypeConverter<Recording> {

    @SpringBean
    private RepositoryService objRepository;

    @Override
    public Recording convert(String input, Class<? extends Recording> targetType,
                             Collection<ValidationError> errors) {

        if (StringUtils.isBlank(input)) {
            errors.add(new LocalizableError("recordingnotfound"));
            return null;
        }
        Recording obj = objRepository.fetchRecordingById(input);

        if (obj == null) {
            errors.add(new LocalizableError("recordingnotfound", input));
            return null;
        }

        return obj;
    }

    @Override
    public void setLocale(Locale locale) {
    }

}
