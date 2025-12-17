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
@UrlBinding("/web/recordings")
public class RecordingsActionBean extends BaseActionBean {

    @Getter
    private List<Recording> recordings;
    @Getter
    @Setter
    @Validate(on = DELETE, converter = RecordingConverter.class, required = true)
    private Recording recording;
    @Validate
    @Getter
    @Setter
    private String startDate;
    @Validate
    @Getter
    @Setter
    private String endDate;
    @Getter
    @Setter
    @Validate
    private int currPage;

    private LocalDate fromDate, toDate;
    @Getter
    private Page page = new Page();

    @DefaultHandler
    public Resolution list() {
        configurePagination();
        page.setTotalItemsFound(repositoryService.countRecordings());
        recordings = repositoryService.fetchRecordings(page);
        page.setNumItemsShowing(recordings.size());
        return new ForwardResolution(LIST_JSP);
    }

    @HandlesEvent(SEARCH)
    public Resolution search() {
        configurePagination();
        validateDates();
        if (fromDate != null && toDate != null) {
            page.setTotalItemsFound(repositoryService.countRecordingsForDates(fromDate, toDate));
            recordings = repositoryService.fetchRecordingsForDates(fromDate, toDate, page);
            page.setNumItemsShowing(recordings.size());
        } else {
            page.setTotalItemsFound(repositoryService.countRecordings());
            recordings = repositoryService.fetchRecordings( page);
            page.setNumItemsShowing(recordings.size());
        }
        return new ForwardResolution(LIST_JSP);
    }

    @HandlesEvent(DELETE)
    public Resolution delete() {
        repositoryService.getRepository().delete(recording);
        return new RedirectResolution(getListPage());
    }
    public String getListPage(){
        return "/web/recordings";
    }

    public long getRecordingsSize() {
        return recordings.size();
    }

    protected void configurePagination() {
        String pageNumber = getContext().getRequest().getParameter(Constants.CURR_PAGE);
        if (StringUtils.isBlank(pageNumber)) {
            pageNumber = "1";
        }

        int pageSize = config.getIntegerProperty(Constants.PAGINATION_SIZE);
        page.setCurrPage(Integer.valueOf(pageNumber));
        page.setItemsPerPage(pageSize);
    }

    public String getSearchDates() {
        if (StringUtils.isNoneBlank(startDate, endDate)) {
            return startDate + " and " + endDate;
        }
        return "up to" + getFormattedDay();
    }

    public String getFormattedDay() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return fmt.format(LocalDate.now());
    }

    public void validateDates() {
        if (startDate != null && endDate == null) {
            getContext().getValidationErrors().add("startDate",
                    new LocalizableError("alldatesmustbespecified"));
        } else if (startDate == null && endDate != null) {
            getContext().getValidationErrors().add("startDate",
                    new LocalizableError("alldatesmustbespecified"));
        }
        if (startDate != null && endDate != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
                fromDate = LocalDate.parse(startDate, formatter);
            } catch (Exception e) {
                getContext().getValidationErrors().add("startDate",
                        new LocalizableError("notabletoparsedatefrom"));
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
                toDate = LocalDate.parse(endDate, formatter);
            } catch (Exception e) {
                getContext().getValidationErrors().add("endDate",
                        new LocalizableError("notabletoparsedateto"));
            }
        }
        if (fromDate != null && toDate != null) {
            if (toDate.isBefore(fromDate)) {
                getContext().getValidationErrors().add("endDate",
                        new LocalizableError("enddatebeforestartdate"));
            }
        }
    }

    @Override
    public String getNavSection() {
        return "recordings";
    }

    @Override
    protected String getErrorPage() {
        return LIST_JSP;
    }

    public String getPageTitle(){
        return "Recordings";
    }

    @SpringBean
    private RepositoryService repositoryService;
    private static final String PATTERN = "dd-MM-yyyy";
    private static final String LIST_JSP = "/WEB-INF/jsp/recordings/other-list.jsp";
}
