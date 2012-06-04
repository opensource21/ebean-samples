package de.ppi.psec.helper;
/**
 * 
 */


import java.util.List;

import models.psec.PsecPermission;
import models.psec.PsecRole;
import models.psec.PsecUser;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;


/**
 * Helperclass to create Permissions, Roles and Users for Ebean. 
 * @author niels
 *
 */
public class EbeanInitDBHelper {

    private final  EbeanServer server;
    
    
    public EbeanInitDBHelper() {
        this(null);
    }
    
    public EbeanInitDBHelper(String server) {
        this.server = Ebean.getServer(server);
    }
 
    /**
     * Creates or update a permission-object, as long as auto-updates isn't 
     * forbidden. 
     * @param name the name of the permission.
     * @param target the target for which the permission is responsible
     * @param actions the action which the permission allow
     * @return the permission-object.
     */
    public PsecPermission createOrUpdatePermission(String name, String target, String actions) {
        PsecPermission result = server.find(PsecPermission.class).
                where().eq("name", name).findUnique();
        if (result == null) {
            result = new PsecPermission();
            result.setName(name);
        } 
        
        if (!result.getAutoUpdatesForbidden()) {
            result.setActions(actions);
            result.setTarget(target);
        } else {
            System.out.println("Can't update Permission " + name);
        }
        server.save(result);

        return result;
    }
    
    
    /**
     * Creates or update a role-object, as long as auto-updates isn't 
     * forbidden. 
     * @param name the name of the role.
     * @param defaultRole flag if this role should be a default-role.
     * @param permissions the permissions
     * @return the role-object.
     */   
    public PsecRole createOrUpdateRole(String name, boolean defaultRole, String... permissions) {
        PsecRole result = server.find(PsecRole.class).
                where().eq("name", name).findUnique();
        if (result == null) {
            result = new PsecRole();
            result.setName(name);
        }
        if (!result.getAutoUpdatesForbidden()) {
            final List<PsecPermission> permissionObjects = server.find(PsecPermission.class).
                    where().in("name", (Object[])permissions).findList();
            result.setPsecPermissions(permissionObjects);
            result.setDefaultRole(defaultRole);
        } else {
            System.out.println("Can't update Role " + name);
        }
        server.save(result);
        server.saveManyToManyAssociations(result, "psecPermissions");
        return result;
    }

   

    /**
     * Creates or update a user. 
     * @param loginName the loginName
     * @param emailAddress the email-address
     * @param firstName the firstname
     * @param lastName the lastname
     * @param description the description
     * @param password the password.
     * @param enabled flag if the user is enabled
     * @param permission a permission the user should have
     * @param roles the roles a user should have.
     * @return the user.
     */
    public PsecUser createOrUpdateUser(String loginName, String emailAddress, 
            String firstName, String lastName, String description, 
            String password, boolean enabled, String permission, String... roles) {        
        PsecUser result = server.find(PsecUser.class).
                where().eq("loginName", loginName).findUnique();
        if (result == null) {
            result = new PsecUser();
            result.setLoginname(loginName);
            result.setPassword(password);
        }
        if (!result.getAutoUpdatesForbidden()) {
            final List<PsecPermission> permissionObjects = server.find(PsecPermission.class).
                    where().eq("name", permission).findList();
            result.setPsecPermissions(permissionObjects);
            result.setEmailAddress(emailAddress);
            result.setFirstname(firstName);
            result.setLastname(lastName);
            result.setDescription(description);
            result.setEnabled(enabled);
            final List<PsecRole> roleObjects = server.find(PsecRole.class).
                    where().in("name", (Object[])roles).findList();
            result.setPsecRoles(roleObjects);
        } else {
            System.out.println("Can't update User " + loginName);
        }
        server.save(result);
        return result;
    }

}
