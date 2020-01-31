package org.apereo.cas.config;

import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.ServiceFactoryConfigurer;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.ws.idp.authentication.principal.WsFederationApplicationServiceFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * Configuration class for Saml20WebapplicationServiceFactory.
 *
 * @author Travis Schmidt
 * @since 6.1.0
 */
@Configuration("wsFedApplicationServiceFactoryConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CoreWsSecurityIdentityProviderWebApplicationServiceFactoryConfiguration implements ServiceFactoryConfigurer {
    @Bean
    public ServiceFactory wsFedApplicationServiceFactory() {
        return new WsFederationApplicationServiceFactory();
    }

    @Override
    public Collection<ServiceFactory<? extends WebApplicationService>> buildServiceFactories() {
        return CollectionUtils.wrap(wsFedApplicationServiceFactory());
    }
}
