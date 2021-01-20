package com.company.queries;

public class Query_Selection {
    public static String query_selection(String input_question, String subject, String well_num) {
        String QueryString="";
        if(!well_num.equals("")){
            String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Well/"+subject+"/"+well_num+">";
            System.out.println(formation_uri);
            QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                    "SELECT ?topDepth "+
                    "WHERE { "+formation_uri+" stratig:TopDepth ?topDepth . }";

        }
        else {
            if(input_question.contains("age")||input_question.contains("period")){
                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/"+subject+">";
                QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                        "SELECT ?name "+
                        "WHERE { "+formation_uri+" stratig:FormedDuring ?period . " +
                        "?period stratig:Name ?name . }";

            }
            else if(input_question.contains("group")){
                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/"+subject+">";
                QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                        "SELECT ?name "+
                        "WHERE { "+formation_uri+" stratig:PartOf ?group .\n" +
                        "?group stratig:Name ?name . }";

            }
            else if(input_question.contains("/") && input_question.contains("well")){
                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/"+subject+">";
                QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                        "SELECT ?top "+
                        "WHERE { "+formation_uri+" stratig:PartOf stratig:TopDepth ?top . }";

            }
            else if(input_question.contains("well")){

                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/"+subject+">";
                QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                        "SELECT ?wellnum "+
                        "WHERE { ?well stratig:Crosses "+formation_uri+" . "+
                        "?well stratig:Name ?wellnum . }";
            }
            else if(input_question.contains("lithology")){

                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Lithology/"+subject+">";
                QueryString ="PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>"+
                        "SELECT ?name "+
                        "WHERE { "+formation_uri+" stratig:Name ?name . }";

            }
            else if(input_question.contains("member")) {
                //ex: fruholmen (has member)
                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/"+subject+">";
                QueryString = "PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>" +
                        "SELECT ?name " +
                        "WHERE { ?member stratig:BelongsTo "+formation_uri+" ."+
                        "?member stratig:Name ?name . }";
            }
            else if(input_question.contains("where")||input_question.contains("location")) {

                String formation_uri="<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#Formation/"+subject+">";
                QueryString = "PREFIX stratig:<http://www.semanticweb.org/user/ontologies/2020/11/Stratigraphy_in_North_Sea#>" +
                        "SELECT ?boundary " +
                        "WHERE { "+formation_uri+" stratig:Bounds ?boundary . }";
            }

        }


        return QueryString;


    }

}
