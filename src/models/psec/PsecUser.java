package models.psec;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames={"loginname"}),
        @UniqueConstraint(columnNames={"email_address"})})
public class PsecUser {
	
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String loginname = "";
    @Column(name="email_address")
    private String emailAddress;
    private String lastname = "";
    private String firstname = "";
    private String description = "";
    @Column(nullable = false)
    private boolean enabled = false;
    private int loginFailedCount = 0;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    /** The passwordhash */
    private String password;

    @ManyToMany()
    private List<PsecRole> psecRoles = new ArrayList <PsecRole>();

    @ManyToMany()
    private List<PsecPermission> psecPermissions;

    @SuppressWarnings("unused")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    private boolean autoUpdatesForbidden = false;

    @PreUpdate
    @PrePersist
    protected void onUpdate() {
        lastUpdate = new Date();
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List <PsecRole> getPsecRoles() {
        return psecRoles;
    }

    
    public void setPsecRoles(List <PsecRole> roles) {
        this.psecRoles = roles;
    }
    
    /**
     * Return true if updates at application updates are forbidden.
     * @return true if updates at application updates are forbidden.
     */
    public boolean getAutoUpdatesForbidden() {
        return autoUpdatesForbidden;
    }

    /**
     * Set the flag, that updates at application updates are forbidden.
     * @param autoUpdatesForbidden flag, that updates at application updates are forbidden.
     */
    public void setAutoUpdatesForbidden(boolean autoUpdatesForbidden) {
        this.autoUpdatesForbidden = autoUpdatesForbidden;
    }



    /**
     * 
     *{@inheritDoc}
     */
    public void addToRoles(PsecRole permsecRole) {
        psecRoles.add(permsecRole);
    }

    public List <PsecPermission> getPsecPermissions() {
        return psecPermissions;
    }


    public void setPsecPermissions(List <PsecPermission> permissions) {
        this.psecPermissions = permissions;
    }

    
    /**
     * 
     *{@inheritDoc}
     */
    public String getLoginname() {
        return loginname;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public String getEmailAddress() {
        return emailAddress;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public void setEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.trim().length() == 0) {
            this.emailAddress = null;
        } else {
            this.emailAddress = emailAddress;
        }
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public String getLastname() {
        return lastname;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public String getFirstname() {
        return firstname;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public String getDescription() {
        return description;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public void setDescription(String description) {
        this.description = description;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public int getLoginFailedCount() {
        return loginFailedCount;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public void setLoginFailedCount(int loginFailedCount) {
        this.loginFailedCount = loginFailedCount;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public boolean isEnabled() {
        return enabled;
    }


    
    /**
     * 
     *{@inheritDoc}
     */
    public String getPasswordHash() {
        return password;
    }


    /**
     * 
     *{@inheritDoc}
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            expiredDate = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * 
     *{@inheritDoc}
     */
    public Date getExpiredDate() {
        return expiredDate;
    }


    /**
     * 
     *{@inheritDoc}
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (autoUpdatesForbidden ? 1231 : 1237);
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result
                + ((expiredDate == null) ? 0 : expiredDate.hashCode());
        result = prime * result
                + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + loginFailedCount;
        result = prime * result
                 + ((loginname == null) ? 0 : loginname.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result
                + ((psecPermissions == null) ? 0 : psecPermissions.hashCode());
        result = prime * result
                + ((psecRoles == null) ? 0 : psecRoles.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PsecUser other = (PsecUser) obj;
        if (autoUpdatesForbidden != other.autoUpdatesForbidden)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        if (enabled != other.enabled)
            return false;
        if (expiredDate == null) {
            if (other.expiredDate != null)
                return false;
        } else if (!expiredDate.equals(other.expiredDate))
            return false;
        if (firstname == null) {
            if (other.firstname != null)
                return false;
        } else if (!firstname.equals(other.firstname))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        if (loginFailedCount != other.loginFailedCount)
            return false;
        if (loginname == null) {
            if (other.loginname != null)
                return false;
        } else if (!loginname.equals(other.loginname))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (psecPermissions == null) {
            if (other.psecPermissions != null)
                return false;
        } else if (!psecPermissions.equals(other.psecPermissions))
            return false;
        if (psecRoles == null) {
            if (other.psecRoles != null)
                return false;
        } else if (!psecRoles.equals(other.psecRoles))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "PsecUser [loginname=" + loginname + ", emailAddress="
                + emailAddress + ", lastname=" + lastname + ", firstname="
                + firstname + ", description=" + description + ", enabled="
                + enabled + ", expiredDate=" + expiredDate + ", password="
                + password + ", psecRoles=" + psecRoles + ", psecPermissions="
                + psecPermissions + "]";
    }


    


}
