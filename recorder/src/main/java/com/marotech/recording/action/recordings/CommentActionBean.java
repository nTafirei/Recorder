package com.marotech.recording.action.recordings;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.converters.CommentConverter;
import com.marotech.recording.action.converters.RecordingConverter;
import com.marotech.recording.model.Comment;
import com.marotech.recording.model.Recording;
import com.marotech.recording.service.RepositoryService;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

@UrlBinding("/web/comment/{documents}/{parent}")
public class CommentActionBean extends BaseActionBean {
    @Getter
    @Setter
    @Validate(required = true, converter = RecordingConverter.class)
    private Recording recording;
    @Getter
    @Setter
    @Validate(on = SAVE, converter = CommentConverter.class)
    private Comment parent;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String title;
    @Getter
    @Setter
    @Validate(on = SAVE, required = true)
    private String body;

    @DefaultHandler
    public Resolution view() {
        if (parent != null) {
            title = "RE: " + parent.getTitle();
        }
        return new ForwardResolution(COMMENT_JSP);
    }

    @HandlesEvent(SAVE)
    public Resolution save() throws Exception {
        Comment comment = new Comment();
        comment.setCreatedBy(getCurrentUser());
        comment.setTitle(title);
        comment.setBody(body);
        comment.setParent(parent);
        repositoryService.save(comment);
        return new RedirectResolution("/web/comments/" + recording.getId());
    }

    @Override
    protected String getErrorPage() {
        return COMMENT_JSP;
    }

    @Override
    public String getNavSection() {
        return "recordings";
    }

    @SpringBean
    private RepositoryService repositoryService;

    public static final String COMMENT_JSP = "/WEB-INF/jsp/user/pubs/comment.jsp";
}