package com.marotech.recording.action.docs;


import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.action.RequiresOneRoleOf;
import com.marotech.recording.action.converters.EnumConverter;
import com.marotech.recording.action.converters.UserConverter;
import com.marotech.recording.model.*;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@WebServlet
@MultipartConfig(fileSizeThreshold = 20971520) // 20MB
@UrlBinding("/web/upload")
public class UploadActionBean extends BaseActionBean {

    @Setter
    private FileBean fileBean;
    @Getter
    private String message;

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }

    @HandlesEvent(UPLOAD)
    public Resolution upload() throws ServletException, IOException {

        ForwardResolution resolution = processFile();
        if (resolution != null) {
            message = "Could not process the uploaded file";
            return resolution;
        }
        return new RedirectResolution("/web/my-recordings");
    }

    private ForwardResolution processFile() throws ServletException, IOException {

        InputStream inputStream;
        try {
            inputStream = fileBean.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            String fileName = fileBean.getFileName();
            boolean valid = false;
            if (fileName.toLowerCase().endsWith(".pdf")
                    || fileName.toLowerCase().endsWith(".txt")) {
                valid = true;
            }
            if (!valid) {
                getContext().getValidationErrors().add("fileBean",
                        new LocalizableError("invalidformatfound"));
                return new ForwardResolution(JSP);
            }
            createDocument(bytes);
            message = "File has been accepted for processing";
        } catch (Exception e) {
            LOG.error("Error", e);
            getContext().getValidationErrors().add("fileBean",
                    new LocalizableError("filenotfound"));
        }

        if (getContext().getValidationErrors().keySet().size() > 0) {
            return new ForwardResolution(JSP);
        }
        return null;
    }

    private void createDocument(byte[] bytes) {

        Attachment attachment = new Attachment();
        attachment.setData(bytes);
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setName(fileBean.getFileName());
        repositoryService.save(attachment);
        String path = config.getProperty("app.audio.storage.path");
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(path + File.separator + fileBean.getFileName()));
            writer.write(new String(bytes, StandardCharsets.UTF_8));
            writer.close();
        } catch (Exception ex) {
            LOG.error("Error", ex);
        }
        Recording recording = new Recording();
        recording.setAttachment(attachment);
        recording.setUser(getCurrentUser());
        repositoryService.save(recording);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.UPLOADED_RECORDING);
            activity.setActor(getCurrentUser());
            activity.setAttachment(attachment);
            activity.setTitle(getCurrentUser().getFullName()
                    + " for " + getCurrentUser().getFullName() + " on " + LocalDate.now());
            repositoryService.save(activity);
        }
    }

    @Override
    public String getNavSection() {
        return "uploads";
    }

    @Override
    protected String getErrorPage() {
        return JSP;
    }

    @SpringBean
    private RepositoryService repositoryService;

    private static final Logger LOG = LoggerFactory.getLogger(UploadActionBean.class);
    public static final String UPLOAD = "upload";
    public static final String JSP = "/WEB-INF/jsp/docs/upload.jsp";
}