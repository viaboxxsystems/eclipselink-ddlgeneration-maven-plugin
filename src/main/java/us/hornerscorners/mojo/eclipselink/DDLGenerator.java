/*
 * @(#)DDLGenerator.java
 * Date 2012-07-27
 * Version 1.0
 * Author Jim Horner
 * Copyright (c)2012
 */


package us.hornerscorners.mojo.eclipselink;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;


/**
 * Class description
 *
 *
 * @version        v1.0, 2012-07-27
 * @author Jim Horner
 * @version v1.0, 2012-07-27
 */
public class DDLGenerator extends Thread {

    /**
     * Field description
     */
    private final Map<String, Object> properties;

    /**
     * Field description
     */
    private final String unitName;

    /**
     * Constructs ...
     *
     * @param cl
     */
    public DDLGenerator(String unitName, ClassLoader cl) {

        super();
        this.unitName = unitName;
        this.properties = buildProperties();

        setContextClassLoader(cl);
    }

    /**
     * Method description
     *
     * @return
     */
    private Map<String, Object> buildProperties() {

        Map<String, Object> props = new HashMap<String, Object>();

        // override with local transaction
        props.put(PersistenceUnitProperties.TRANSACTION_TYPE, "RESOURCE_LOCAL");
        props.put(PersistenceUnitProperties.JTA_DATASOURCE, null);
        props.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, null);
        props.put(PersistenceUnitProperties.VALIDATION_MODE, "NONE");

        // Enable DDL Generation
        props.put(PersistenceUnitProperties.DDL_GENERATION,
                PersistenceUnitProperties.DROP_AND_CREATE);
        props.put(PersistenceUnitProperties.DDL_GENERATION_MODE,
                PersistenceUnitProperties.DDL_SQL_SCRIPT_GENERATION);

        return props;
    }

    /**
     * Method description
     */
    @Override
    public void run() {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {

            emf = Persistence.createEntityManagerFactory(this.unitName,
                    this.properties);

            try {

                em = emf.createEntityManager();

            } finally {

                if (em != null) {
                    em.close();
                }
            }

        } finally {

            if (emf != null) {
                emf.close();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setCreateFile(String val) {

        this.properties.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE,
                            val);
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setDeleteFile(String val) {

        this.properties.put(PersistenceUnitProperties.DROP_JDBC_DDL_FILE, val);
    }

    /**
     * Method description
     *
     * @param val
     */
    public void setJdbcDriver(String val) {
        this.properties.put(PersistenceUnitProperties.JDBC_DRIVER, val);
    }

    /**
     * Method description
     *
     * @param val
     */
    public void setJdbcPassword(String val) {

        this.properties.put(PersistenceUnitProperties.JDBC_PASSWORD, val);
    }

    /**
     * Method description
     *
     * @param val
     */
    public void setJdbcUrl(String val) {

        this.properties.put(PersistenceUnitProperties.JDBC_URL, val);
    }

    /**
     * Method description
     *
     * @param val
     */
    public void setJdbcUser(String val) {

        this.properties.put(PersistenceUnitProperties.JDBC_USER, val);
    }

    /**
     * Method description
     *
     * @param dir
     */
    public void setOutputDir(File dir) {

        if (dir.exists() == false) {

            dir.mkdirs();
        }

        this.properties.put(PersistenceUnitProperties.APP_LOCATION,
                dir.getAbsolutePath());
    }
}
