/*
 * @(#)EclipselinkDdlGenerationMojo.java
 * Date 2012-07-26
 * Version 1.0
 * Author Jim Horner
 * Copyright (c)2012
 */


package us.hornerscorners.mojo.eclipselink;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.project.MavenProject;

import org.eclipse.persistence.config.PersistenceUnitProperties;


/**
 *
 * @goal execute
 * @author jim
 */
public class EclipselinkDdlGenerationMojo extends AbstractMojo {

    /**
     * @parameter
     * @required
     */
    private String createFilename;

    /**
     * @parameter
     * @required
     */
    private String deleteFilename;

    /**
     * @parameter
     * @required
     */
    private String jdbcDriver;

    /**
     * @parameter
     * @required
     */
    private String jdbcPassword;

    /**
     * @parameter
     * @required
     */
    private String jdbcUrl;

    /**
     * @parameter
     * @required
     */
    private String jdbcUser;

    /**
     * @parameter expression="${project}"
     */
    private MavenProject mavenProject;

    /**
     * @parameter
     * @required
     */
    private File outputDir;

    /**
     * @parameter
     * @required
     */
    private String unitName;

    /**
     * Method description
     *
     *
     * @return
     */
    private ClassLoader buildEntityClassLoader() throws MalformedURLException {

        List<URL> urls = new ArrayList<>();

        File classesFile =
            new File(this.mavenProject.getBuild().getOutputDirectory());

        urls.add(classesFile.toURI().toURL());

        return new URLClassLoader(urls.toArray(new URL[urls.size()]),
                                  this.getClass().getClassLoader());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    private Map<String, Object> buildProperties() throws IOException {

        Map<String, Object> props = new HashMap<>();

        // override with local transaction
        props.put(PersistenceUnitProperties.TRANSACTION_TYPE, "RESOURCE_LOCAL");
        props.put(PersistenceUnitProperties.JTA_DATASOURCE, null);
        props.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, null);
        props.put(PersistenceUnitProperties.VALIDATION_MODE, "NONE");

        // configure jdbc connection pool
        props.put(PersistenceUnitProperties.JDBC_DRIVER, this.jdbcDriver);
        props.put(PersistenceUnitProperties.JDBC_URL, this.jdbcUrl);
        props.put(PersistenceUnitProperties.JDBC_USER, this.jdbcUser);
        props.put(PersistenceUnitProperties.JDBC_PASSWORD, this.jdbcPassword);

        // Enable DDL Generation
        props.put(PersistenceUnitProperties.DDL_GENERATION,
                  PersistenceUnitProperties.DROP_AND_CREATE);
        props.put(PersistenceUnitProperties.DDL_GENERATION_MODE,
                  PersistenceUnitProperties.DDL_SQL_SCRIPT_GENERATION);
        props.put(PersistenceUnitProperties.APP_LOCATION,
                  this.outputDir.getAbsolutePath());
        props.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE,
                  this.createFilename);
        props.put(PersistenceUnitProperties.DROP_JDBC_DDL_FILE,
                  this.deleteFilename);

        return props;
    }

    /**
     * Method description
     *
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {

            if (this.outputDir.exists() == false) {

                this.outputDir.mkdirs();
            }

            Map<String, Object> props = buildProperties();

            Thread currThread = Thread.currentThread();
            ClassLoader origClassLoader = currThread.getContextClassLoader();

            try {

                ClassLoader cl = buildEntityClassLoader();

                props.put(PersistenceUnitProperties.CLASSLOADER, cl);
                Thread.currentThread().setContextClassLoader(cl);

                emf = Persistence.createEntityManagerFactory(this.unitName,
                        props);


            } finally {

                currThread.setContextClassLoader(origClassLoader);
            }

            em = emf.createEntityManager();

        } catch (Exception e) {

            throw new MojoExecutionException("Exception", e);

        } finally {

            if (em != null) {
                em.close();
            }

            if (emf != null) {
                emf.close();
            }
        }
    }
}
