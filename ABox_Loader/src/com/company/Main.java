package com.company;

import com.company.queries.Question_Parsing;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


//        DefineTBox.run();
//        DefineABox.run();

        //Asking Question
        String question="";
        String go_on="";

        while(!go_on.equals("n")){
            System.out.println("Enter a question");
            Scanner myObj = new Scanner(System.in);
            question = myObj.nextLine();
            question=question.toLowerCase(); //turn string ot lowercase, easy to compare
            Question_Parsing.question_parsing(question);
            System.out.println("continue? (y/n)");
            Scanner Continue = new Scanner(System.in);
            go_on= Continue.nextLine();

        }

          // Read user input




        }
    }


