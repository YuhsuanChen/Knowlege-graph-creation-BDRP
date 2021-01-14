package com.company.define;

import com.company.utils.CSV;
import com.company.utils.Utils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.define.DefineTBox.ontModel;


public class DefineABox {
    final static String ns = "http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#";

    OntClass geolocationClass = ontModel.getOntClass(ns + "Geolocation");
    ObjectProperty LocatedIn_prop = ontModel.getObjectProperty(ns + "LocatedIn");

    public static void run() {

        try {
            import_Part1(); //Formation, Group, Lithology, Period
            import_Part2();
            //OutputStream out = new FileOutputStream("output-test.ttl");
            //RDFDataMgr.write(out, ontModel, Lang.TURTLE);
            OutputStream out = new FileOutputStream("Abox_output.rdf"); //output the ABox
            RDFDataMgr.write(out, ontModel, Lang.RDFXML); //output the ABox
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void import_Part1() throws IOException {
        String formation_basic = "src/Test_Abox.csv";
        OntClass lithologyClass = ontModel.getOntClass(ns + "Lithology");
        OntClass periodClass = ontModel.getOntClass(ns + "Period");
        OntClass groupClass = ontModel.getOntClass(ns + "Group");
        OntClass memberClass = ontModel.getOntClass(ns + "Member");
        OntClass formationClass = ontModel.getOntClass(ns + "Formation");
        OntClass DefinitionClass = ontModel.getOntClass(ns + "Definition");
        OntClass StudyFieldClass = ontModel.getOntClass(ns + "StudyField");
        DatatypeProperty Name_prop = ontModel.getDatatypeProperty(ns + "Name");
        ObjectProperty CharacterizedBy_prop = ontModel.getObjectProperty(ns + "CharacterizedBy");
        ObjectProperty FormedDuring_prop = ontModel.getObjectProperty(ns + "FormedDuring");
        ObjectProperty PartOf_prop = ontModel.getObjectProperty(ns + "PartOf");
        ObjectProperty BelongsTo_prop = ontModel.getObjectProperty(ns + "BelongsTo");
        ObjectProperty DefinedAs_prop = ontModel.getObjectProperty(ns + "DefinedAs");
        List<String[]> lines = CSV.read(formation_basic, ",/t,");

        for (String[] line : lines) {

            String formation_name = line[3];
            String group_content = line[2];
            String lithology_content = line[4];
            String period_content = line[5];
            Utils.addFormation(Utils.cleanURI(formation_name));

            // Inserting the formation, its group and related info
            if (formation_name.contains("formation") || formation_name.contains("Formation")) {
                Individual formation = formationClass.createIndividual(ns + "Formation/" + Utils.cleanURI(formation_name));
                Literal formation_Name_string = ontModel.createTypedLiteral(formation_name, XSDDatatype.XSDstring);
                ontModel.add(formation, Name_prop, formation_Name_string);
                if (group_content.contains("group")) {
                    Individual group = groupClass.createIndividual(ns + "Group/" + Utils.cleanURI(formation_name));
                    Literal group_content_string = ontModel.createTypedLiteral(group_content, XSDDatatype.XSDstring);
                    ontModel.add(group, Name_prop, group_content_string);
                    ontModel.add(formation, PartOf_prop, group);
                }
                //Inserting the lithology
                Individual lithology = lithologyClass.createIndividual(ns + "Lithology/" + Utils.cleanURI(formation_name));
                Literal lithology_content_string = ontModel.createTypedLiteral(lithology_content, XSDDatatype.XSDstring);
                ontModel.add(lithology, Name_prop, lithology_content_string);

                //Inserting the period
                Individual period = periodClass.createIndividual(ns + "Period/" + Utils.cleanURI(formation_name));
                Literal period_content_string = ontModel.createTypedLiteral(period_content, XSDDatatype.XSDstring);
                ontModel.add(period, Name_prop, period_content_string);

                ontModel.add(formation, CharacterizedBy_prop, lithology);
                ontModel.add(formation, FormedDuring_prop, period);

            } else if (formation_name.contains("group") || formation_name.contains("Group")) {
                Individual group = groupClass.createIndividual(ns + "Group/" + Utils.cleanURI(formation_name));
                Literal group_content_string = ontModel.createTypedLiteral(group_content, XSDDatatype.XSDstring);
                ontModel.add(group, Name_prop, group_content_string);
                //Inserting the lithology
                Individual lithology = lithologyClass.createIndividual(ns + "Lithology/" + Utils.cleanURI(formation_name));
                Literal lithology_content_string = ontModel.createTypedLiteral(lithology_content, XSDDatatype.XSDstring);
                ontModel.add(lithology, Name_prop, lithology_content_string);

                //Inserting the period
                Individual period = periodClass.createIndividual(ns + "Period/" + Utils.cleanURI(formation_name));
                Literal period_content_string = ontModel.createTypedLiteral(period_content, XSDDatatype.XSDstring);
                ontModel.add(period, Name_prop, period_content_string);

                ontModel.add(group, CharacterizedBy_prop, lithology);
                ontModel.add(group, FormedDuring_prop, period);

            } else if(formation_name.contains("member")||formation_name.contains("Member")){
                Individual member = memberClass.createIndividual(ns + "Member/" + Utils.cleanURI(formation_name));
                Literal member_content_string = ontModel.createTypedLiteral(formation_name, XSDDatatype.XSDstring);
                ontModel.add(member, Name_prop, member_content_string);
                if(group_content.contains("formation")){
                    Individual formation = formationClass.createIndividual(ns + "Formation/" + Utils.cleanURI(group_content));
                    Literal formation_Name_string = ontModel.createTypedLiteral(group_content, XSDDatatype.XSDstring);
                    ontModel.add(formation, Name_prop, formation_Name_string);
                    ontModel.add(member, BelongsTo_prop, formation);
                }

            }


            //Inserting the studyfield
            Individual studyfield = StudyFieldClass.createIndividual(ns + "StudyField/" + Utils.cleanURI(formation_name));
            Literal study_field_string = ontModel.createTypedLiteral("Stratigraphy", XSDDatatype.XSDstring);
            ontModel.add(studyfield, Name_prop, study_field_string);


            //Inserting the definition
            Individual definition = DefinitionClass.createIndividual(ns + "Definition/" + Utils.cleanURI(formation_name));
            Literal definition_string = ontModel.createTypedLiteral("Stratigraphy is a geology study involved the study of the rock layer(strata). It includes three main subfields, lithostratigraphy, biostratigraphy and chronostratigraphy.", XSDDatatype.XSDstring);
            ontModel.add(definition, Name_prop, definition_string);
            ontModel.add(studyfield, DefinedAs_prop, definition);


        }
        System.out.println("Part1 done");
    }

    public static String getCoordinate(String one, String two, boolean max) {
        if (one.equals(two)) {
            return one;
        }
        List<String> delimier = Arrays.asList("°", "'", "''");
        String oneMutation = one;
        String twoMutation = two;
        for (String l : delimier) {
            String[] oneSplit = oneMutation.split(l);
            String[] twoSplit = twoMutation.split(l);
            if (Double.parseDouble(oneSplit[0].trim()) > Double.parseDouble(twoSplit[0].trim())) {
                return max ? one : two;
            }
            if (Double.parseDouble(oneSplit[0].trim()) < Double.parseDouble(twoSplit[0].trim())) {
                return max ? two : one;
            }
            oneMutation = oneSplit[1];
            twoMutation = twoSplit[1];
        }
        return null;
    }

    //Wells information
    private static void import_Part2() throws IOException {
        String part2 = "src/wells_location_formation.csv";
        OntClass WellClass = ontModel.getOntClass(ns + "Well");
        ObjectProperty Crosses = ontModel.getObjectProperty(ns + "Crosses");
        DatatypeProperty Name_prop = ontModel.getDatatypeProperty(ns + "Name");
        DatatypeProperty TopDepth_prop = ontModel.getDatatypeProperty(ns + "TopDepth");
        DatatypeProperty BottomDepth_prop = ontModel.getDatatypeProperty(ns + "BottomDepth");
        DatatypeProperty Longitude_prop = ontModel.getDatatypeProperty(ns + "Longitude");
        DatatypeProperty Latitude_prop = ontModel.getDatatypeProperty(ns + "Latitude");
        DatatypeProperty formationBound = ontModel.getDatatypeProperty(ns + "Bounds");
//        Formation name: <W: value, E: value, N: value, S: value>
        Map<String, Map<String, String>> boundedBox = new HashMap<>();
        List<String[]> lines = CSV.read(part2, ",");

        for (String[] line : lines) {
            String well_number = line[0];
            String top = line[1];
            String bottom = line[2];
            String formation_name = line[3];
            String latitude = line[4];
            String longitude = line[5];


            // Inserting the well
            String cleanedFormationName = Utils.cleanURI(formation_name);
            Individual formation = ontModel.getIndividual(ns + "Formation/" + cleanedFormationName);
            Individual well = WellClass.createIndividual(ns + "Well/" + Utils.cleanURI(formation_name + "/" + well_number));
            Literal well_string = ontModel.createTypedLiteral(well_number, XSDDatatype.XSDstring);
            Literal latitude_string = ontModel.createTypedLiteral(latitude, XSDDatatype.XSDstring);
            Literal longitude_string = ontModel.createTypedLiteral(longitude, XSDDatatype.XSDstring);

            if (formation != null) {
                ontModel.add(well, Crosses, formation);
                ontModel.add(well, Name_prop, well_string);
                ontModel.add(well, Longitude_prop, longitude_string);
                ontModel.add(well, Latitude_prop, latitude_string);
                Map<String, String> existingValue = boundedBox.getOrDefault(cleanedFormationName, new HashMap<>());
                existingValue.put("N", getCoordinate(existingValue.getOrDefault("N", latitude), latitude, true));
                existingValue.put("S", getCoordinate(existingValue.getOrDefault("S", latitude), latitude, false));
                existingValue.put("E", getCoordinate(existingValue.getOrDefault("E", longitude), longitude, true));
                existingValue.put("W", getCoordinate(existingValue.getOrDefault("W", longitude), longitude, false));
                boundedBox.put(cleanedFormationName, existingValue);
            }

            //add top and bottom value
            Literal top_value = ontModel.createTypedLiteral(top, XSDDatatype.XSDdouble);
            ontModel.add(well, TopDepth_prop, top_value);
            Literal bottom_value = ontModel.createTypedLiteral(bottom, XSDDatatype.XSDdouble);
            ontModel.add(well, BottomDepth_prop, bottom_value);


        }
        boundedBox.forEach((key, value) -> {
            Individual formation = ontModel.getIndividual(ns + "Formation/" + key);
            String boundedValue = "";
            boundedValue = boundedValue + "Formation is bounded in NS by " + value.get("N") + " to " + value.get("S") + ".";
            boundedValue = boundedValue + "Formation is bounded in EW by " + value.get("E") + " to " + value.get("W") + ".";
            Literal boundedValueLiteral = ontModel.createTypedLiteral(boundedValue, XSDDatatype.XSDstring);
            ontModel.add(formation, formationBound, boundedValueLiteral);

        });
        System.out.println("Part2 done");

    }
}
