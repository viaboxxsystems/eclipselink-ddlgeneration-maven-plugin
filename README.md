eclipselink-ddlgeneration-maven-plugin
======================================

fork from https://code.google.com/p/eclipselink-ddlgeneration-maven-plugin/

additional feature:
optional parameter persistenceXMLLocation

example:
run with
     mvn process-classes

pom.xml
                    <plugin>
                        <groupId>us.hornerscorners.mojo</groupId>
                        <artifactId>eclipselink-ddlgeneration-maven-plugin</artifactId>
                        <version>1.3-SNAPSHOT</version>
                        <configuration>
                            <jdbcUrl>jdbc:...your db connect url ...</jdbcUrl>
                            <jdbcDriver>...your jdbc driver class ...</jdbcDriver>
                            <jdbcUser>...your db user...</jdbcUser>
                            <jdbcPassword>...your db pw...</jdbcPassword>
                            <outputDir>src/main/resources/database</outputDir>
                            <createFilename>schema.sql</createFilename>
                            <deleteFilename>drop-schema.sql</deleteFilename>
                            <unitName>...unit name in persistence.xml ...</unitName>
                            <!-- see http://eclipse.org/eclipselink/documentation/2.4/jpa/extensions/p_persistencexml.htm#persistencexml -->
                            <persistenceXMLLocation>database/persistence.xml</persistenceXMLLocation>
                        </configuration>
                        <executions>
                            <execution>
                                <id>ddl-generation</id>
                                <goals>
                                    <goal>execute</goal>
                                </goals>
                                <phase>process-classes</phase>
                            </execution>
                        </executions>
                        <dependencies>
                           <!-- jdbc driver, domain classes -->
                        </dependencies>
                    </plugin>
