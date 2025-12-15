package com.marotech.vending.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.util.Properties;

@Configuration("appConfig")
public class Config {
    private Properties props = new Properties();

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public String getProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return props.getProperty(property.trim());
    }

    public Long getLongProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return Long.valueOf(props.getProperty(property.trim()));
    }

    public boolean getBooleanProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return false;
        }
        return Boolean.valueOf(props.getProperty(property.trim()));
    }

    public BigDecimal getBigDecimalProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return new BigDecimal(props.getProperty(property.trim()));
    }

    public Float getFloatProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return Float.valueOf(props.getProperty(property.trim()));
    }

    public Double getDoubleProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return Double.valueOf(props.getProperty(property.trim()));
    }
    public Short getShortProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return Short.valueOf(props.getProperty(property.trim()));
    }

    public Integer getIntegerProperty(String property) {
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return Integer.valueOf(props.getProperty(property.trim()));
    }
    @Bean(name = "configProperties")
    public PropertiesFactoryBean configProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("config.properties"));
        return propertiesFactoryBean;
    }

    @Bean(name = "config")
    public Config config() throws Exception {
        Config config = new Config();
        // getObject() returns the loaded Properties instance from PropertiesFactoryBean
        config.setProps(configProperties().getObject());
        return config;
    }
    public void setProperty(String key, String value) {
        props.setProperty(key.trim(), value.trim());
    }
}
