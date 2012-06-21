package de.ppi.psec.helper;


import static org.fest.assertions.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import models.psec.PsecPermission;
import models.psec.PsecRole;
import models.psec.PsecUser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;


public class EbeanInitDBHelperTest {

    EbeanInitDBHelper helper = new EbeanInitDBHelper("pgtest");
    public static EbeanServer server;

    private static final String ROLE = "Role";

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ServerConfig config = new ServerConfig();  
        config.setName("pgtest");  
          
        // Define DataSource parameters  
        DataSourceConfig dbConfig = initH2();  
          
        config.setDataSourceConfig(dbConfig);  
          
        // specify a JNDI DataSource   
        // config.setDataSourceJndiName("someJndiDataSourceName");  
          
        // set DDL options...  
        config.setDdlGenerate(true);  
        config.setDdlRun(true);  
          
        config.setDefaultServer(false);  
        config.setRegister(true);
        config.addClass(PsecUser.class);
        config.addClass(PsecRole.class);
        config.addClass(PsecPermission.class);
        // create the EbeanServer instance  
        server = EbeanServerFactory.create(config);  
    }

    private static DataSourceConfig initPostgres() {
        DataSourceConfig postgresDb = new DataSourceConfig();  
        postgresDb.setDriver("org.postgresql.Driver");  
        postgresDb.setUsername("permsec");  
        postgresDb.setPassword("permsec");  
        postgresDb.setUrl("jdbc:postgresql://localhost:5432/permsec");  
        postgresDb.setHeartbeatSql("select count(*) from psec_user");
        return postgresDb;
    }
    
    private static DataSourceConfig initH2() {
        DataSourceConfig h2DB = new DataSourceConfig();  
        h2DB.setDriver("org.h2.Driver");  
        h2DB.setUsername("permsec");  
        h2DB.setPassword("permsec");  
        h2DB.setUrl("jdbc:h2:mem:play");  
        h2DB.setHeartbeatSql("select count(*) from psec_user");
        return h2DB;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateOrUpdatePermission() {
        helper.createOrUpdatePermission("Test", "target", "actions");
        PsecPermission perm = server.find(PsecPermission.class).where().eq("name", "Test").findUnique();
        assertThat(perm.getTarget()).isEqualTo("target");
        assertThat(perm.getActions()).isEqualTo("actions");
        server.delete(perm);
        
    }

    @Test
    public void testCreateOrUpdateRole() {
        String[] permNames = {"Test1", "Test2", "Test3"};
        List <PsecPermission> permissions = new ArrayList <PsecPermission>();
        for (int i = 0; i < permNames.length; i++) {
            helper.createOrUpdatePermission(permNames[i], "target"+ i, "actions" +i);
            PsecPermission perm = server.find(PsecPermission.class).where().eq("name", permNames[i]).findUnique();
            assertThat(perm.getTarget()).isEqualTo("target" + i);
            assertThat(perm.getActions()).isEqualTo("actions" + i);
            permissions.add(perm);
        }
        
        PsecRole orgRole = helper.createOrUpdateRole(ROLE, false, permNames);
        testRole(permNames, orgRole);
        PsecRole role = server.find(PsecRole.class).where().eq("name", ROLE).findUnique();
        testRole(permNames, role);
        
    }

    private void testRole(String[] permNames, PsecRole role) {
        assertThat(role).isNotNull();
        assertThat(role.getName()).isEqualTo(ROLE);
        assertThat(role.isDefaultRole()).isEqualTo(false);
        assertThat(role.getPsecPermissions()).hasSize(permNames.length);
    }


}
