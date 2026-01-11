package com.marotech.recording.action.docs;


import com.marotech.recording.action.BaseActionBean;
import com.marotech.recording.model.Activity;
import com.marotech.recording.model.ActivityType;
import com.marotech.recording.model.Attachment;
import com.marotech.recording.model.Recording;
import com.marotech.recording.service.RepositoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
            List<String> types = getContentTypes();

            if (!types.contains(fileBean.getContentType())) {
                getContext().getValidationErrors().add("fileBean",
                        new SimpleError("Files of type " + fileBean.getContentType() +
                                " cannot be accepted. Please try again with an audio or video file"));
                return new ForwardResolution(JSP);
            }
            createDocument(bytes);
            message = "File has been accepted for processing";
        } catch (Exception e) {
            LOG.error("Error", e);
            getContext().getValidationErrors().add("fileBean",
                    new SimpleError(e.getMessage()));
        }

        if (getContext().getValidationErrors().keySet().size() > 0) {
            return new ForwardResolution(JSP);
        }
        return null;
    }
    public List<String> getContentTypes(){
        String str = config.getProperty("app.content.types");
        String[] array = str.split(",");
        return Arrays.asList(array);
    }

    private void createDocument(byte[] bytes) {

/*        Attachment attachment = new Attachment();
        attachment.setData(bytes);
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setName(fileBean.getFileName());
        repositoryService.save(attachment);*/
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
        recording.setUser(getCurrentUser());
        recording.setName(fileBean.getFileName());
        recording.setMediaType(fileBean.getContentType());
        repositoryService.save(recording);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.UPLOADED_RECORDING);
            activity.setActor(getCurrentUser());
            activity.setTitle(fileBean.getFileName());
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