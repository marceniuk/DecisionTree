/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gwt.decisiontree.model;

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
@Table(name = "categorised_data", catalog = "mysql", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategorisedData.findAll", query = "SELECT c FROM CategorisedData c"),
    @NamedQuery(name = "CategorisedData.findById", query = "SELECT c FROM CategorisedData c WHERE c.id = :id"),
    @NamedQuery(name = "CategorisedData.findByA1", query = "SELECT c FROM CategorisedData c WHERE c.a1 = :a1"),
    @NamedQuery(name = "CategorisedData.findByA2", query = "SELECT c FROM CategorisedData c WHERE c.a2 = :a2"),
    @NamedQuery(name = "CategorisedData.findByA3", query = "SELECT c FROM CategorisedData c WHERE c.a3 = :a3"),
    @NamedQuery(name = "CategorisedData.findByA4", query = "SELECT c FROM CategorisedData c WHERE c.a4 = :a4"),
    @NamedQuery(name = "CategorisedData.findByA5", query = "SELECT c FROM CategorisedData c WHERE c.a5 = :a5"),
    @NamedQuery(name = "CategorisedData.findByClass1", query = "SELECT c FROM CategorisedData c WHERE c.class1 = :class1")})
public class CategorisedData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "A1", length = 12)
    private String a1;
    @Column(name = "A2", length = 8)
    private String a2;
    @Column(name = "A3", length = 7)
    private String a3;
    @Column(name = "A4", length = 7)
    private String a4;
    @Column(name = "A5", length = 7)
    private String a5;
    @Column(name = "class", length = 8)
    private String class1;

    public CategorisedData() {
    }

    public CategorisedData(Integer id) {
        this.id = id;
    }
    
     public CategorisedData(Integer id, String A1, String A2, String A3, String A4, String A5, String class1) {
        this.id = id;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.class1 = class1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
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
        if (!(object instanceof CategorisedData)) {
            return false;
        }
        CategorisedData other = (CategorisedData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "decision_tree.model.CategorisedData[ id=" + id + " ]";
    }
    
}
