package com.company;

import com.company.utils.CSV;
import com.company.utils.Utils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import com.company.queries.Query_Selection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


        //DefineTBox.run();
        //DefineABox.run();
        //Question
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a question");

        String question = myObj.nextLine();  // Read user input
        System.out.println("Your question is: " + question);  // Output user input
        question=question.toLowerCase(); //turn string ot lowercase, easy to compare
        //Check the list of formation/group/member name



        String formation_basic = "/Users/alice/Desktop/ABox_Loader/src/Test_Abox.csv";
        List<String[]> lines = CSV.read(formation_basic, ",/t,");
        List<String> subject_list=new ArrayList<>();

        for (String[] line : lines) {
            String formation_name = line[3];
            formation_name=Utils.cleanURI(formation_name);
            subject_list.add(formation_name);
        }
        System.out.println(subject_list);



        if(question.contains("stratigraphy")){
            System.out.println("Stratigraphy is a geology study involved the study of the rock layer(strata). It includes three main subfields, lithostratigraphy, biostratigraphy and chronostratigraphy.");
        }
        else{


            String Query_String=Query_Selection.query_selection(question);

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





        }







        }
    }


