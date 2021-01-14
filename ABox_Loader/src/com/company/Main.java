package com.company;

import com.company.queries.Question_Parsing;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


//        DefineTBox.run();
//        DefineABox.run();

        //Asking Question
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a question");
        String question = myObj.nextLine();  // Read user input

        question=question.toLowerCase(); //turn string ot lowercase, easy to compare
        Question_Parsing.question_parsing(question);


        }
    }


