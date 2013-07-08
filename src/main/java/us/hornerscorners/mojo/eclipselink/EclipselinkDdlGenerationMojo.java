/*
 * @(#)EclipselinkDdlGenerationMojo.java
 * Date 2012-07-26
 * Version 1.0
 * Author Jim Horner
 * Copyright (c)2012
 */


package us.hornerscorners.mojo.eclipselink;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.project.MavenProject;


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

        List<URL> urls = new ArrayList<URL>();

        File classesFile =
            new File(this.mavenProject.getBuild().getOutputDirectory());

        urls.add(classesFile.toURI().toURL());

        return new URLClassLoader(
            urls.toArray(new URL[urls.size()]),
            Thread.currentThread().getContextClassLoader());
    }

    /**
     * Method description
     *
     *
     */

    /**
     * Method description
     *
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {

            DDLGenerator generator = new DDLGenerator(this.unitName,
                                         buildEntityClassLoader());

            // configure jdbc connection pool
            generator.setJdbcDriver(this.jdbcDriver);
            generator.setJdbcPassword(this.jdbcPassword);
            generator.setJdbcUrl(this.jdbcUrl);
            generator.setJdbcUser(this.jdbcUser);

            // configure output parameters
            generator.setOutputDir(this.outputDir);
            generator.setCreateFile(this.createFilename);
            generator.setDeleteFile(this.deleteFilename);

            generator.start();
            generator.join();

        } catch (Exception e) {

            throw new MojoExecutionException("Exception", e);

        }
    }
}
