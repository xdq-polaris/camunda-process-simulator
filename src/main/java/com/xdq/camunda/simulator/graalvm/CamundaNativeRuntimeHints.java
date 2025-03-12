package com.xdq.camunda.simulator.graalvm;

import java.util.Set;
import java.util.regex.Pattern;

//import org.camunda.bpm.engine.rest.impl.NamedProcessEngineRestServiceImpl;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Component;

@Component
@ImportRuntimeHints(CamundaNativeRuntimeHints.class)
//reference: https://forum.camunda.io/t/running-camunda-as-native-using-graalvm/59357
public class CamundaNativeRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        registerCamundaClasses(hints);
        registerResources(hints);

    }

    private void registerCamundaClasses(RuntimeHints hints) {

        Class<?>[] filterClasses = {
                com.fasterxml.jackson.databind.ObjectMapper.class,
//                com.fasterxml.jackson.jakarta.rs.base.JsonMappingExceptionMapper.class,
//                com.fasterxml.jackson.jakarta.rs.base.JsonParseExceptionMapper.class,
//                com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider.class,
//                com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector.class,
                jakarta.servlet.Filter.class,
                jakarta.servlet.http.HttpServlet.class,
                jakarta.servlet.ServletContext.class,
                jakarta.servlet.ServletContextListener.class,
                java.lang.Boolean.class,
                java.lang.Integer.class,
                java.lang.String.class,
                java.lang.Byte.class,
                java.lang.Character.class,
                java.lang.Double.class,
                java.lang.Float.class,
                java.lang.Short.class,
                java.util.ArrayList.class,
                java.util.List.class,
                org.apache.catalina.core.ApplicationContext.class,
                org.apache.catalina.core.ApplicationContextFacade.class,
                org.apache.catalina.core.StandardContext.class,
                org.apache.catalina.core.StandardWrapper.class,
                org.apache.catalina.loader.WebappLoader.class,
                org.apache.catalina.servlets.DefaultServlet.class,
                org.apache.catalina.webresources.DirResourceSet.class,
                org.apache.catalina.webresources.JarResourceSet.class,
                org.apache.catalina.webresources.StandardRoot.class,
                org.apache.ibatis.javassist.util.proxy.ProxyFactory.class,
                org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl.class,
                org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl.class,
                org.apache.ibatis.logging.log4j.Log4jImpl.class,
                org.apache.ibatis.logging.log4j2.Log4j2Impl.class,
                org.apache.ibatis.logging.nologging.NoLoggingImpl.class,
                org.apache.ibatis.logging.slf4j.Slf4jImpl.class,
                org.apache.ibatis.plugin.Interceptor.class,
                org.apache.ibatis.scripting.defaults.RawLanguageDriver.class,
                org.apache.ibatis.scripting.xmltags.XMLLanguageDriver.class,
                org.apache.ibatis.session.Configuration.class,
//                org.camunda.bpm.spring.boot.starter.actuator.JobExecutorHealthIndicator.class,
//                org.camunda.bpm.spring.boot.starter.actuator.JobExecutorHealthIndicator.Details.class,
                //org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig.class,
                org.camunda.bpm.spring.boot.starter.util.SpringBootProcessEngineLogger.class,
                org.camunda.bpm.application.impl.ProcessApplicationLogger.class,
                org.camunda.bpm.container.impl.ContainerIntegrationLogger.class,
                //org.camunda.bpm.spring.boot.starter.webapp.CamundaBpmWebappInitializer.class,
                //org.glassfish.jersey.internal.JaxrsProviders.class,
        };

        for (Class<?> clazz : filterClasses) {
            hints.reflection().registerType(clazz,
                    MemberCategory.values());
        }
        // CamundaIntegrationDeterminator c;
        registerClassesInPackage(hints, "jakarta.ws.rs", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.dmn", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.engine", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.spring.boot.starter.webapp.filter", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.admin", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.webapp", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.tasklist", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.cockpit", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.tasklist", ".*");
        registerClassesInPackage(hints, "org.camunda.bpm.engine.rest", ".*");
        registerClassesInPackage(hints, "org.camunda.commons", ".*");
        registerClassesInPackage(hints, "org.camunda.connect", ".*");
        registerClassesInPackage(hints, "org.camunda.spin", ".*");
        registerClassesInPackage(hints, "org.glassfish.hk2", ".*");
        registerClassesInPackage(hints, "org.glassfish.jersey", ".*");
        registerClassesInPackage(hints, "org.glassfish.jersey.internal", ".*"); // internal dont get picked up
        // otherwise?
        registerClassesInPackage(hints, "org.jvnet", ".*");
        registerClassesInPackage(hints, "org.jvnet.hk2.spring.bridge.internal", ".*");
        registerClassesInPackage(hints, "org.joda.time", ".*");
        registerClassesInPackage(hints, "java.lang", ".*");
        registerClassesInPackage(hints, "java.util", ".*");

//        hints.serialization().registerType(TypeReference
//                .of(org.camunda.bpm.spring.boot.starter.actuator.JobExecutorHealthIndicator.Details.class));

        hints.proxies().registerJdkProxy(
//                jakarta.ws.rs.ext.ParamConverterProvider.class,
//                jakarta.ws.rs.ext.Provider.class,
//                jakarta.ws.rs.ext.Providers.class,
//                jakarta.ws.rs.ext.ContextResolver.class,
//                org.glassfish.hk2.api.ProxyCtl.class,
//                org.glassfish.jersey.internal.inject.InjectionManager.class,
//                org.glassfish.jersey.internal.inject.InjectionResolver.class,
                jakarta.servlet.ServletContext.class,
                org.springframework.web.context.ServletContextAware.class

        );

        // org.camunda.bpm.engine.impl.identity.db.DbUserQueryImpl a;

        // Add this to your existing hints
        // AbstractQueryDto a;
        // hints.reflection()
        // .registerType(org.camunda.bpm.engine.rest.dto.task.TaskQueryDto.class,
        // MemberCategory.values())
        // .registerField(TypeReference.of("org.camunda.bpm.engine.rest.dto.task.TaskQueryDto").field("firstResult"))
        // .registerField(TypeReference.of("org.camunda.bpm.engine.rest.dto.task.TaskQueryDto").field("maxResults"));

        // hints.registerType(TypeReference.of(org.camunda.bpm.engine.rest.dto.task.TaskQueryDto.class),
        // typeHint -> typeHint.withField(field.getName()));

        // // You might also need the AbstractQueryDto
        // hints.reflection()
        // .registerType(org.camunda.bpm.engine.rest.dto.AbstractQueryDto.class,
        // MemberCategory.DECLARED_FIELDS,
        // MemberCategory.DECLARED_METHODS);

    }

    private void registerResources(RuntimeHints hints) {

        hints.resources()
                // .registerPattern(builder -> builder.excludes("*.class"))
                // lots of random nonsense that might or might not be helping. some came from
                // llm suggestions
                .registerPattern("*.properties")
                .registerPattern("*.xml")
                .registerPattern("webapps/**")
                .registerPattern("camunda/app/**")
                .registerPattern("META-INF/maven/*")
                .registerPattern("META-INF/resources/camunda/**")
                .registerPattern("META-INF/services/*")
                .registerPattern("org/apache/ibatis/builder/xml/mybatis-3-config.dtd")
                .registerPattern("org/apache/ibatis/builder/xml/mybatis-3-mapper.dtd")
                .registerPattern("org/camunda/bpm/**")
                .registerPattern("org/camunda/bpm/engine/impl/scripting/**")
                .registerPattern("org/camunda/bpm/engine/rest/**")
                .registerPattern("org/camunda/bpm/spring/boot/starter/webapp/**")
                .registerPattern("org/camunda/spin/**/*.xsl")
                .registerPattern("public/**")
                .registerPattern("scripts/**")
                .registerPattern("static/**")
                .registerPattern("WEB-INF/**")

                // from looking at camunda-webapp-webjar-7.21.0.jar
                .registerPattern("META-INF/resources/camunda-welcome/**")
                .registerPattern("META-INF/resources/plugin/**")
                .registerPattern("META-INF/resources/webjars/**")

                // used by spin for script envs. camunda complains about script/env/groovy/spin.groovy
                .registerPattern("script/env/**");

    }

    //NamedProcessEngineRestServiceImpl x;

    // add function to add all classes in a package, based on regex filter
    private void registerClassesInPackage(RuntimeHints hints, String packageName, String regex) {

        // Add all the entity classes
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return super.isCandidateComponent(beanDefinition)
                        || beanDefinition.getMetadata().isAbstract();
            }
        };
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(regex)));

        final Set<BeanDefinition> classes = provider.findCandidateComponents(packageName);

        for (BeanDefinition bean : classes) {
            // System.out.println("x: " + bean.getBeanClassName() + " - " + packageName);

            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                hints.reflection().registerType(clazz,
                        MemberCategory.values());

                // System.out.println(clazz.getName());
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
                System.out.println("Error: Could not find class: " + bean.getBeanClassName());
                // throw new RuntimeException(e);
            } catch (java.lang.NoClassDefFoundError e) {
                // is ok. some dependency not there
                System.out.print(" XXX " + e.getMessage());
            }
        }
    }

}