package com.marotech.vending.dataset;


import com.marotech.vending.security.FeatureValidator;
import com.marotech.vending.security.ProtectedElementParser;
import com.marotech.vending.security.RoleNameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//DO NOT DELETE!!!
@Component
public class BaseDataSet {

    @Autowired
    private FeatureValidator featureValidator;
    @Autowired
    private RoleNameParser roleNameParser;

    @Autowired
    private ProtectedElementParser protectedElementParser;

}
