package com.marotech.vending.action.converters;

import com.marotech.vending.service.RepositoryService;
import net.sourceforge.stripes.integration.spring.SpringBean;

public class ConverterBase {
    @SpringBean
    protected RepositoryService repositoryService;
}
