package models.psec;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"}),
    @UniqueConstraint(columnNames={"target", "actions"})})
public class PsecPermission  {

    @Id
    @GeneratedValue
    private Long id;
	
    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String type = "Basic";

    @Column(nullable=false)
    private String target;

    @Column(nullable=false)
    private String actions;

//    @ManyToMany(mappedBy="permissions")
//    public List<PsecRole> roles;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    
    private boolean autoUpdatesForbidden = false;


    @Override
    public String toString()  {
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
        result = prime * result + ((actions == null) ? 0 : actions.hashCode());
        result = prime * result + (autoUpdatesForbidden ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        PsecPermission other = (PsecPermission) obj;
        if (actions == null) {
            if (other.actions != null)
                return false;
        } else if (!actions.equals(other.actions))
            return false;
        if (autoUpdatesForbidden != other.autoUpdatesForbidden)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (target == null) {
            if (other.target != null)
                return false;
        } else if (!target.equals(other.target))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

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

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    
    public String getTarget() {
        return target;
    }

    
    public void setTarget(String target) {
        this.target = target;
    }

    
    public String getActions() {
        return actions;
    }

    
    public void setActions(String actions) {
        this.actions = actions;
    }

    
    public Date getLastUpdate() {
        return lastUpdate;
    }

    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    
       
}
