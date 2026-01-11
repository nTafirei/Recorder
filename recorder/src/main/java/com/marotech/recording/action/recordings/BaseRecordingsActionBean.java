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
abstract class BaseRecordingsActionBean extends BaseActionBean {

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
        page.setTotalItemsFound(repositoryService.countRecordingsForUser(getUser()));
        recordings = repositoryService.fetchRecordingsForUser(getUser(), page);
        page.setNumItemsShowing(recordings.size());
        return new ForwardResolution(getJsp());
    }

    @HandlesEvent(SEARCH)
    public Resolution search() {
        validateDates();
        if (context.getValidationErrors().size() > 0) {
            return new ForwardResolution(getJsp());
        }
        configurePagination();
        if (fromDate != null && toDate != null) {
            page.setTotalItemsFound(repositoryService.countRecordingsForUser(getUser(),
                    fromDate, toDate));
            recordings = repositoryService.fetchRecordingsForUser(getUser(),
                    fromDate, toDate, page);
            page.setNumItemsShowing(recordings.size());
        } else {
            page.setTotalItemsFound(repositoryService.countRecordingsForUser(getUser()));
            recordings = repositoryService.fetchRecordingsForUser(getUser(), page);
            page.setNumItemsShowing(recordings.size());
        }
        return new ForwardResolution(getJsp());
    }

    public User getUser() {
        return getCurrentUser();
    }

    @HandlesEvent(DELETE)
    public Resolution delete() {
        repositoryService.getRepository().delete(recording);
        return new RedirectResolution(getListPage());
    }

    protected abstract String getListPage();

    public long getRecordingsSize() {
        if (recordings == null || recordings.isEmpty()) {
            return 0;
        }
        return recordings.size();
    }

    protected void configurePagination() {
        String pageNumber = getContext().getRequest().getParameter(Constants.CURR_PAGE);
        if (StringUtils.isBlank(pageNumber)) {
            pageNumber = "1";
        }

        int pageSize = config.getIntegerProperty(Constants.PAGINATION_SIZE);
        try {
            page.setCurrPage(Math.max(1, Integer.parseInt(pageNumber)));
        } catch (NumberFormatException e) {
            page.setCurrPage(1);
        }
        page.setItemsPerPage(pageSize);
    }

    public String getSearchDates() {
        if (StringUtils.isNoneBlank(startDate, endDate)) {
            return startDate + " and " + endDate;
        }
        return "up to" + getFormattedDay();
    }

    public String getFormattedDay() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(PATTERN);
        return fmt.format(LocalDate.now());
    }

    private void validateDates() {
        if ((startDate == null) != (endDate == null)) {
            getContext().getValidationErrors()
                    .add("startDate", new LocalizableError("alldatesmustbespecified"));
            return;
        }
        if (startDate == null || endDate == null) return;

        fromDate = parseDate(startDate, "startDate", "from");
        toDate = parseDate(endDate, "endDate", "to");

        if (fromDate != null && toDate != null && toDate.isBefore(fromDate)) {
            getContext().getValidationErrors()
                    .add("endDate", new LocalizableError("enddatebeforestartdate"));
        }
    }

    private LocalDate parseDate(String dateStr, String field, String tag) {
        try {
            return LocalDate.parse(dateStr, FMT);
        } catch (Exception e) {
            getContext().getValidationErrors().add(field,
                    new LocalizableError("notabletoparsedate" + tag));
            return null;
        }
    }

    @Override
    public String getNavSection() {
        return "recordings";
    }

    @Override
    protected String getErrorPage() {
        return getJsp();
    }

    protected abstract String getPageTitle();

    public abstract String getJsp();

    @SpringBean
    private RepositoryService repositoryService;
    private static final String PATTERN = "dd-MM-yyyy";
    public static DateTimeFormatter FMT = DateTimeFormatter.ofPattern(PATTERN);
}
