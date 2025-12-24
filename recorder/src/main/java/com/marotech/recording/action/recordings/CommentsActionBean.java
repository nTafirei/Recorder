package com.marotech.recording.action.recordings;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.converters.RecordingConverter;
import com.marotech.recording.model.Recording;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

@UrlBinding("/web/comments/{documents}")
public class CommentsActionBean extends BaseActionBean {
    @Getter
    @Setter
    @Validate(converter = RecordingConverter.class, required = true)
    private Recording recording;

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(COMMENTS_JSP);
    }

    @Override
    protected String getErrorPage() {
        return COMMENTS_JSP;
    }

    @Override
    public String getNavSection() {
        return "recordings";
    }

    public static final String COMMENTS_JSP = "/WEB-INF/jsp/user/pubs/comments.jsp";
}