/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decision_tree.msr.children_with_asthma;

import decision_tree.model.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1
 */
@Entity
@Table(name = "attribute", catalog = "mysql", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attribute.findAll", query = "SELECT a FROM Attribute a"),
    @NamedQuery(name = "Attribute.findById", query = "SELECT a FROM Attribute a WHERE a.id = :id"),
    @NamedQuery(name = "Attribute.findByAttributeName", query = "SELECT a FROM Attribute a WHERE a.attributeName = :attributeName"),
    @NamedQuery(name = "Attribute.findByAttributeFieldName", query = "SELECT a FROM Attribute a WHERE a.attributeFieldName = :attributeFieldName")})
public class Attribute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "attribute_name", length = 25)
    private String attributeName;
    @Column(name = "attribute_field_name", length = 25)
    private String attributeFieldName;

    public Attribute() {
    }

    public Attribute(Integer id) {
        this.id = id;
    }
    
    public Attribute(Integer id, String attributeName, String attributeFieldName) {
        this.id = id;
        this.attributeName = attributeName;
        this.attributeFieldName = attributeFieldName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeFieldName() {
        return attributeFieldName;
    }

    public void setAttributeFieldName(String attributeFieldName) {
        this.attributeFieldName = attributeFieldName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attribute)) {
            return false;
        }
        Attribute other = (Attribute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "decision_tree.model.Attribute[ id=" + id + " ]";
    }
    
}
