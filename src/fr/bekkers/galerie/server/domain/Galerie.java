//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2014.08.04 à 07:00:50 AM CEST 
//


package fr.bekkers.galerie.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aquarelle" type="{}aquarelle" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="next-id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "aquarelle"
})
@XmlRootElement(name = "galerie")
public class Galerie {

    protected List<Aquarelle> aquarelle;
    @XmlAttribute(name = "next-id", required = true)
    protected int nextId;

    /**
     * Gets the value of the aquarelle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aquarelle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAquarelle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Aquarelle }
     * 
     * 
     */
    public List<Aquarelle> getAquarelle() {
        if (aquarelle == null) {
            aquarelle = new ArrayList<Aquarelle>();
        }
        return this.aquarelle;
    }

    /**
     * Obtient la valeur de la propriété nextId.
     * 
     */
    public int getNextId() {
        return nextId;
    }

    /**
     * Définit la valeur de la propriété nextId.
     * 
     */
    public void setNextId(int value) {
        this.nextId = value;
    }

}
