package com.victor.jbank.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final IpAddressFilter ipAddressFilter;

    public FilterConfig(IpAddressFilter ipAddressFilter) {
        this.ipAddressFilter = ipAddressFilter;
    }

    @Bean
    public FilterRegistrationBean<IpAddressFilter> registrationIpAddressFilter() {
        var registrationBean = new FilterRegistrationBean<IpAddressFilter>();

        registrationBean.setFilter(ipAddressFilter);
        registrationBean.setOrder(0);

        return registrationBean;
    }
}
