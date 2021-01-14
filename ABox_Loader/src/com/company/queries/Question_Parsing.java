package com.company.queries;

import com.company.utils.CSV;
import com.company.utils.Utils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Question_Parsing {

    public static String question_parsing(String input_question) throws IOException {
        //Check the list of formation/group/member name
        String formation_basic = "src/Test_Abox.csv";
        String well_info = "src/well-location.csv";
        List<String[]> lines = CSV.read(formation_basic, ",/t,");
        List<String[]> wells = CSV.read(well_info, ",");

        String result="";

        //no need query to answer this question
        if(input_question.contains("stratigraphy")){
            result = "Stratigraphy is a geology study involved the study of the rock layer(strata). It includes three main subfields, lithostratigraphy, biostratigraphy and chronostratigraphy.";
        }
        else{
            String well_num="";
            String subject="";

            for (String[] well: wells ){
                String num=well[0];
                if(input_question.contains(num)){
                    well_num=num;
                    break;
                }
            }

            for (String[] line : lines) {
                String formation_name = line[3];
                formation_name= Utils.cleanURI(formation_name);
                if(input_question.contains(formation_name.split("_")[0])){
                    subject=formation_name;
                    break;
                }

            }
            String Query_String="";
            try{
                Query_String=Query_Selection.query_selection(input_question,subject, well_num);
            } catch (Exception e) {
                System.out.println("We can't understand your question.");
            }

            if(!Query_String.equals("")){
                //Initialize the connection for querying the graph
                InputStream in = new FileInputStream(new File("Abox_output.rdf"));
                // Create an empty in‑memory model and populate it from the graph
                Model model = ModelFactory.createMemModelMaker().createModel("model");
                model.read(in,null); // null base URI, since model URIs are absolute
                in.close();

                org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

                //execute the query
                Query query = QueryFactory.create(Query_String);

                // Execute the query and obtain results
                QueryExecution qe = QueryExecutionFactory.create(query, model);
                ResultSet results = qe.execSelect();

                // Output query results
                ResultSetFormatter.out(System.out, results, query);

                qe.close(); // Important ‑ free up resources used running the query

            }else{
                System.out.println("We can't understand your question");
            }



        }
        return result;


    }
}
