package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Poloczek
 */

@Entity
@NamedQueries(
{
    @NamedQuery(name = "Kategorie.findAllKategorie", query = "SELECT * FROM Kategorie"),
})
public class Kategorie implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String name;

    public Kategorie() {
    }

    public Kategorie(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
     
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

   @Override
   public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorie))
        {
            return false;
        }
        Kategorie other = (Kategorie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }
}
