package com.valencra.recipes.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
public class FileUploadConfig {
  public CommonsMultipartResolver commonsMultipartResolver() {
    final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
    commonsMultipartResolver.setMaxUploadSize(-1);
    return commonsMultipartResolver;
  }

  public FilterRegistrationBean filterRegistrationBean() {
    final MultipartFilter multipartFilter = new MultipartFilter();
    final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
    filterRegistrationBean.addInitParameter("multipartResolverBeanName", "commonsMultipartResolver");
    return filterRegistrationBean;
  }
}
