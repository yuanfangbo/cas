package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.support.saml.BaseSamlIdPConfigurationTests;
import org.apereo.cas.support.saml.services.SamlRegisteredService;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link BaseCasSamlSPConfigurationTests}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@Tag("SAML")
@SpringBootTest(classes = {
    RefreshAutoConfiguration.class,
    BaseSamlIdPConfigurationTests.SharedTestConfiguration.class
})
@EnableConfigurationProperties(CasConfigurationProperties.class)
public abstract class BaseCasSamlSPConfigurationTests {
    protected static String SERVICE_PROVIDER;

    @Autowired
    protected CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @DynamicPropertySource
    @SuppressWarnings("UnusedMethod")
    private static void configurePropertySource(final DynamicPropertyRegistry registry) {
        registry.add("cas.samlSp." + SERVICE_PROVIDER + ".metadata", () -> "classpath:/metadata/sp-metadata.xml");
        registry.add("cas.samlSp." + SERVICE_PROVIDER + ".nameIdAttribute", () -> "cn");
        registry.add("cas.samlSp." + SERVICE_PROVIDER + ".nameIdFormat", () -> "transient");
    }

    @BeforeAll
    public static void beforeThisClass() throws Exception {
        BaseSamlIdPConfigurationTests.setMetadataDirectory(
            new FileSystemResource(new File(FileUtils.getTempDirectory(), "idp-metadata-sps")));
    }

    @AfterEach
    public void afterEach() {
        servicesManager.deleteAll();
    }

    @Test
    public void verifyOperation() {
        assertNotNull(servicesManager.findServiceBy(getServiceProviderId(), SamlRegisteredService.class));
    }

    protected String getServiceProviderId() {
        return "https://example.org/shibboleth";
    }
}
