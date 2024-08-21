package erlikh.yaroslav.common.openapi

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springdoc.core.utils.Constants
import org.springframework.beans.factory.BeanFactory
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.function.Supplier

class InfoOpenApiCustomizer implements OpenApiCustomizer, ApplicationContextAware {
    private static String microserviceName
    private static BuildProperties buildProperties
    private static GitProperties gitProperties

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneId.systemDefault())

    @Override
    void customise(OpenAPI openApi) {
        if (openApi.getInfo() == null) {
            openApi.setInfo(new Info())
        }
        Info info = openApi.getInfo()
        info.setTitle(computeIfAbsentOrDefault(info.getTitle(), Constants.DEFAULT_TITLE, () -> microserviceName != null && !microserviceName.isEmpty() ? microserviceName : "unknown"))
        info.setVersion(computeIfAbsentOrDefault(info.getVersion(), Constants.DEFAULT_VERSION, () -> buildProperties != null ? buildProperties.getVersion() : gitProperties != null ? gitProperties.get("build.version") : null))

        if (gitProperties != null) {
            if (info.getContact() == null) {
                info.setContact(new Contact())
            }
            Contact contact = info.getContact()
            contact.setName(computeIfAbsent(contact.getName(), gitProperties.get("commit.user.name")))
            contact.setEmail(computeIfAbsent(contact.getEmail(), gitProperties.get("commit.user.email")))
        }

        if (gitProperties != null) {
            if (openApi.getExternalDocs() == null) {
                openApi.setExternalDocs(new ExternalDocumentation())
            }
            ExternalDocumentation externalDocs = openApi.getExternalDocs()
            externalDocs.setDescription(computeIfAbsent(externalDocs.getDescription(), "Репозиторий"))
            externalDocs.setUrl(computeIfAbsent(externalDocs.getUrl(), gitProperties.get("remote.origin.url")))
        }
    }

    @Override
    void setApplicationContext(ApplicationContext context) {
        microserviceName = getFirstPresentProperty(context, "spring.application.name")
        BeanFactory beanFactory = context.getAutowireCapableBeanFactory()
        buildProperties = beanFactory.getBean(BuildProperties.class)
        gitProperties = beanFactory.getBean(GitProperties.class)
    }

    private String getFirstPresentProperty(ApplicationContext context, String... properties) {
        for (String property : properties) {
            String value = context.getEnvironment().getProperty(property, "")
            if (!value.isBlank()) {
                return value
            }
        }
        return ""
    }

    private String computeIfAbsentOrDefault(String value, String defaultValue, Supplier<String> custom) {
        if (value == null || value.isBlank() || value.equals(defaultValue)) {
            return custom.get()
        }
        return value
    }

    private String computeIfAbsent(String value, String defaultValue) {
        return value != null ? value : defaultValue
    }

    private String format(Instant instant) {
        return formatter.format(instant)
    }
}

