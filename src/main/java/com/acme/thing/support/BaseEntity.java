package com.acme.thing.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @JsonIgnore
    protected String id = UUID.randomUUID().toString();

    @CreatedDate
    @JsonIgnore
    protected Instant created;

    @LastModifiedDate
    @JsonIgnore
    protected Instant modified;

    // object/entity identity
    // https://web.archive.org/web/20180402144531/http://www.onjava.com:80/pub/a/onjava/2006/09/13/dont-let-hibernate-steal-your-identity.html
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) {
            return false;
        }
        BaseEntity other = (BaseEntity) o;
        // if the id is missing, return false
        if (id == null) {
            return false;
        }
        // equivalence by id
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }
}
