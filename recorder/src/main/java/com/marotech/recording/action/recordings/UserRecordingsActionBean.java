package com.marotech.recording.action.recordings;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.converters.RecordingConverter;
import com.marotech.recording.model.Recording;
import com.marotech.recording.model.User;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import com.marotech.recording.util.Page;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@UrlBinding("/web/user-recordings")
public class UserRecordingsActionBean extends BaseRecordingsActionBean {

    @Getter
    @Setter
    private User user;
    public User getUser(){
        return user;
    }

    public String getPageTitle(){
        return "User Recordings";
    }

    @Override
    public String getListPage(){
        return  LIST_PAGE;
    }
    private static final String LIST_PAGE ="/web/user-recordings";
}
