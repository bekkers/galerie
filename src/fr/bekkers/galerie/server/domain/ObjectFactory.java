//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2014.08.04 à 07:00:50 AM CEST 
//


package fr.bekkers.galerie.server.domain;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.bekkers.galerie.server.domain package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.bekkers.galerie.server.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Galerie }
     * 
     */
    public Galerie createGalerie() {
        return new Galerie();
    }

    /**
     * Create an instance of {@link Aquarelle }
     * 
     */
    public Aquarelle createAquarelle() {
        return new Aquarelle();
    }

    /**
     * Create an instance of {@link Adresse }
     * 
     */
    public Adresse createAdresse() {
        return new Adresse();
    }

    /**
     * Create an instance of {@link Photo }
     * 
     */
    public Photo createPhoto() {
        return new Photo();
    }

}
