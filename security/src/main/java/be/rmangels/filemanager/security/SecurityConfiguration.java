package be.rmangels.filemanager.security;

import be.rmangels.filemanager.config.ApplicationProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private OAuth2ClientContext oauth2ClientContext;

    @Inject
    private ApplicationProperties applicationProperties;

    // @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
		.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
		.authorizeRequests()
			.antMatchers("/api/connect**")
			.permitAll()
		.anyRequest()
			.authenticated()
		.and()
			.logout()
		    .logoutSuccessUrl("/").permitAll().and().csrf()
		 .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
    // @formatter:on

    private Filter ssoFilter() {

        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/api/connect/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        facebookFilter.setAuthenticationSuccessHandler(createSuccessHandler());
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
                facebook().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);

        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/api/connect/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setAuthenticationSuccessHandler(createSuccessHandler());
        tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);

        filters.add(facebookFilter);
        filters.add(googleFilter);

        filter.setFilters(filters);

        return filter;
    }

    private AuthenticationSuccessHandler createSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                this.setDefaultTargetUrl(applicationProperties.getLoginSuccessRedirectUrl());
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }
}
