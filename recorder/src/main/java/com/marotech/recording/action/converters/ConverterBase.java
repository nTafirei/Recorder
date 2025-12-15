package com.marotech.recording.action.converters;

import com.marotech.recording.service.RepositoryService;
import net.sourceforge.stripes.integration.spring.SpringBean;

public class ConverterBase {
    @SpringBean
    protected RepositoryService repositoryService;
}
