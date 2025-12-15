package com.marotech.recording.dataset;


import com.marotech.recording.security.FeatureValidator;
import com.marotech.recording.security.ProtectedElementParser;
import com.marotech.recording.security.RoleNameParser;
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
