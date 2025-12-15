package com.marotech.vending.action.docs;

import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.converters.AttachmentConverter;
import com.marotech.vending.model.Attachment;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import java.io.ByteArrayInputStream;

@UrlBinding("/web/download-attachment")
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
