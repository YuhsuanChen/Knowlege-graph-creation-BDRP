package com.company.define;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.ontology.Ontology;

public class Formation_OntModel {
    private OntModel model;

    private Formation_OntModel() {
        this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        String baseURI = "http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea";
        Ontology onto = this.model.createOntology(baseURI);

    }


    private static final Formation_OntModel instance = new Formation_OntModel();

    public static OntModel getInstance() {
        return instance.model;
    }
}