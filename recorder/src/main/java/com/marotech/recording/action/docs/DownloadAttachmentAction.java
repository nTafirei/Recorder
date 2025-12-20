package com.marotech.recording.action.docs;

import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.converters.AttachmentConverter;
import com.marotech.recording.model.Attachment;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import java.io.ByteArrayInputStream;

@UrlBinding("/web/download")
public class DownloadAttachmentAction extends BaseActionBean {

    @Getter
    @Setter
    @Validate(required = true, converter = AttachmentConverter.class)
    private Attachment attachment;

    @DefaultHandler
    public Resolution downloadAttachment() {
        return new StreamingResolution(attachment.getContentType(),
                new ByteArrayInputStream(attachment.getData()));
    }

    @Override
    public String getNavSection() {
        return "users";
    }
}
