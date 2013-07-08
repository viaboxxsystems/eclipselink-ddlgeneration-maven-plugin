eclipselink-ddlgeneration-maven-plugin
======================================

fork from https://code.google.com/p/eclipselink-ddlgeneration-maven-plugin/

additional feature:
optional parameter persistenceXMLLocation

example:
run with
     mvn process-classes

pom.xml
    <pre>
                    &lt;plugin&gt;
                        &lt;groupId&gt;us.hornerscorners.mojo&lt;/groupId&gt;
                        &lt;artifactId&gt;eclipselink-ddlgeneration-maven-plugin&lt;/artifactId&gt;
                        &lt;version&gt;1.3-SNAPSHOT&lt;/version&gt;
                        &lt;configuration&gt;
                            &lt;jdbcUrl&gt;jdbc:...your db connect url ...&lt;/jdbcUrl&gt;
                            &lt;jdbcDriver&gt;...your jdbc driver class ...&lt;/jdbcDriver&gt;
                            &lt;jdbcUser&gt;...your db user...&lt;/jdbcUser&gt;
                            &lt;jdbcPassword&gt;...your db pw...&lt;/jdbcPassword&gt;
                            &lt;outputDir&gt;src/main/resources/database&lt;/outputDir&gt;
                            &lt;createFilename&gt;schema.sql&lt;/createFilename&gt;
                            &lt;deleteFilename&gt;drop-schema.sql&lt;/deleteFilename&gt;
                            &lt;unitName&gt;...unit name in persistence.xml ...&lt;/unitName&gt;
                            &lt;!-- see http://eclipse.org/eclipselink/documentation/2.4/jpa/extensions/p_persistencexml.htm#persistencexml --&gt;
                            &lt;persistenceXMLLocation&gt;database/persistence.xml&lt;/persistenceXMLLocation&gt;
                        &lt;/configuration&gt;
                        &lt;executions&gt;
                            &lt;execution&gt;
                                &lt;id&gt;ddl-generation&lt;/id&gt;
                                &lt;goals&gt;
                                    &lt;goal&gt;execute&lt;/goal&gt;
                                &lt;/goals&gt;
                                &lt;phase&gt;process-classes&lt;/phase&gt;
                            &lt;/execution&gt;
                        &lt;/executions&gt;
                        &lt;dependencies&gt;
                           &lt;!-- jdbc driver, domain classes --&gt;
                        &lt;/dependencies&gt;
                    &lt;/plugin&gt;
</pre>
