/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Scanner;

/**
 *
 * @author azher
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
    /**
     * @param args the command line arguments
     */
        // TODO code application logic here
        HMM hmm = new HMM();
        
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a start state: ");
        String n;
        if((n=reader.next()).equalsIgnoreCase("No")){
           hmm.forwardAlgo(""); 
           
        }
        else{
            hmm.forwardAlgo(n);
        
        }
        hmm.showlikelihoodMatrix();
        
         hmm.veterbialgo1();
        hmm.printtest();
       
        
        
        //hmm.showOutputOfForwardAlgo();
    }
    
}

    
    
