package models.psec;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
        @UniqueConstraint(columnNames={"name"})})
public class PsecRole {
	
    @Id
    @GeneratedValue
    private Long id;
	
    @Column(nullable=false)
    private  String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<PsecPermission> psecPermissions;

    private boolean defaultRole = false;

    private boolean autoUpdatesForbidden = false;

    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Date getLastUpdate() {
        return lastUpdate;
    }

    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    
    public List <PsecPermission> getPsecPermissions() {
        return psecPermissions;
    }

    
    public void setPsecPermissions(List <PsecPermission> permissions) {
        this.psecPermissions = permissions;
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

    
    
    public boolean isDefaultRole() {
        return defaultRole;
    }

    
    public void setDefaultRole(boolean defaultRole) {
        this.defaultRole = defaultRole;
    }

    @Override
    public String toString() {
        return name;
    }

    @PreUpdate
    @PrePersist
    protected void onUpdate() {
        lastUpdate = new Date();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (autoUpdatesForbidden ? 1231 : 1237);
        result = prime * result + (defaultRole ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + 
                ((psecPermissions == null) ? 0 : psecPermissions.hashCode());
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
        PsecRole other = (PsecRole) obj;
        if (autoUpdatesForbidden != other.autoUpdatesForbidden)
            return false;
        if (defaultRole != other.defaultRole)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (psecPermissions == null) {
            if (other.psecPermissions != null)
                return false;
        } else if (!psecPermissions.equals(other.psecPermissions))
            return false;
        return true;
    }


}
