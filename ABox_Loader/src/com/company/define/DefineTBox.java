package com.company.define;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;

public class DefineTBox {
    static OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

    public static void run() {


        final String ns = "http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#";

        OntClass Definition = ontModel.createClass(ns + "Definition");
        OntClass Formation = ontModel.createClass(ns + "Formation");
        OntClass Geolocation = ontModel.createClass(ns + "Geolocation");
        OntClass Group = ontModel.createClass(ns + "Group");
        OntClass Lithology = ontModel.createClass(ns + "Lithology");
        OntClass Member = ontModel.createClass(ns + "Member");
        OntClass Period = ontModel.createClass(ns + "Period");
        OntClass StudyField = ontModel.createClass(ns + "StudyField");
        OntClass Well = ontModel.createClass(ns + "Well");

        /*Defining the object properties*/
        ObjectProperty CharacterizedBy = ontModel.createObjectProperty(ns + "CharacterizedBy"); //done
        CharacterizedBy.addDomain(Formation);
        CharacterizedBy.setRange(Lithology);

        ObjectProperty FormedDuring = ontModel.createObjectProperty(ns + "FormedDuring"); //done
        FormedDuring.addDomain(Formation);
        FormedDuring.addRange(Period);

        ObjectProperty PartOf = ontModel.createObjectProperty(ns + "PartOf"); //done
        PartOf.addDomain(Formation);
        PartOf.addRange(Group);

        ObjectProperty LocatedIn = ontModel.createObjectProperty(ns + "LocatedIn");
        LocatedIn.addDomain(Formation);
        LocatedIn.addRange(Geolocation);


//        ontModel.read(ns + "Well");
        ObjectProperty Crosses = ontModel.createObjectProperty(ns + "Crosses");//done
        Crosses.addDomain(Well);
        Crosses.addRange(Formation);

//        ontModel.read(ns + "StudyField");
        ObjectProperty DefinedAs = ontModel.createObjectProperty(ns + "DefinedAs");
        DefinedAs.addDomain(StudyField);
        DefinedAs.addRange(Definition);
        ObjectProperty SubfieldOf = ontModel.createObjectProperty(ns + "SubfieldOf");
        SubfieldOf.addDomain(StudyField);
        SubfieldOf.addRange(StudyField);
        ObjectProperty RelaterTo = ontModel.createObjectProperty(ns + "RelaterTo");
        RelaterTo.addDomain(StudyField);
        RelaterTo.addRange(StudyField);

//        ontModel.read(ns + "Member");
        ObjectProperty BelongsTo = ontModel.createObjectProperty(ns + "BelongsTo");
        BelongsTo.addDomain(Member);
        BelongsTo.addRange(Formation);


        /*Defining the data properties*/

        DatatypeProperty BottomDepth = ontModel.createDatatypeProperty(ns + "BottomDepth"); //done
        BottomDepth.setDomain(Well);
        BottomDepth.setRange(XSD.xdouble);

        DatatypeProperty Description = ontModel.createDatatypeProperty(ns + "Description");
        Description.addDomain(Member);
        Description.addDomain(Lithology);
        Description.addDomain(Definition);
        Description.setRange(XSD.xstring);

        DatatypeProperty Latitude = ontModel.createDatatypeProperty(ns + "Latitude");
        Latitude.setDomain(Geolocation);
        Latitude.setDomain(Well);
        Latitude.setRange(XSD.xstring);

        DatatypeProperty Longitude = ontModel.createDatatypeProperty(ns + "Longitude");
        Longitude.setDomain(Geolocation);
        Latitude.setDomain(Well);
        Longitude.setRange(XSD.xstring);

        DatatypeProperty Name = ontModel.createDatatypeProperty(ns + "Name"); //done
        Name.addDomain(Formation);
        Name.addDomain(Group);
        Name.addDomain(Member);
        Name.addDomain(Period);
        Name.addDomain(StudyField);
        Name.addDomain(Well);
        Name.setRange(XSD.xstring);

        DatatypeProperty TopDepth = ontModel.createDatatypeProperty(ns + "TopDepth");//done
        TopDepth.setDomain(Well);
        TopDepth.setRange(XSD.xdouble);

        // Define Bounds
        DatatypeProperty bounds = ontModel.createDatatypeProperty(ns + "Bounds");
        bounds.setDomain(Formation);
        bounds.setRange(XSD.xstring);

        //ontModel.listClasses().toList().forEach(System.out::println);

        //ontModel.listDatatypeProperties().toList().forEach(System.out::println);
    }


}
