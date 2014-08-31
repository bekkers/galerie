//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2014.08.04 à 07:00:50 AM CEST 
//


package fr.bekkers.galerie.server.domain;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (fr.bekkers.galerie.server.DateConverter.read(value));
    }

    public String marshal(Date value) {
        return (fr.bekkers.galerie.server.DateConverter.write(value));
    }

}
