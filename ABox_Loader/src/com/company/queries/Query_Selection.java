package com.company.queries;

public class Query_Selection {
    public static String query_selection(String input_question) {
        String QueryString="";

        if(input_question.contains("age")||input_question.contains("period")){
            //new query--Period
            QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                    "SELECT ?name "+
                    "WHERE { <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> stratig:FormedDuring ?period . " +
                    "?period stratig:Name ?name . }";

        }
        else if(input_question.contains("group")){
            //new query--Group
             QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                    "SELECT ?name "+
                    "WHERE { <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> stratig:PartOf ?group .\n" +
                    "?group stratig:Name ?name . }";

        }
        else if(input_question.contains("well")){
            //new query--well
             QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                     "SELECT ?well "+
                     "WHERE { ?well stratig:Crosses <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> .  }";
                     //"?well stratig:Name <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> .  }";
        }
        else if(input_question.contains("lithology")){
            //new query--lithology
            QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                    "SELECT ?name "+
                    "WHERE { <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> stratig:CharacterizedBy ?lithology .\n"+
                    "?lithology stratig:Name ?name . }";
            //"?well stratig:Name <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> .  }";
        }
        else if(input_question.contains("member")) {
            //new query--member
            QueryString = "PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>" +
                    "SELECT ?member " +
                    "WHERE { ?member stratig:BelongsTo <http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/ekofisk_formation> .  }";
        }






        return QueryString;


    }

}
