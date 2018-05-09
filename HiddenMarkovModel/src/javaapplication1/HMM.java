/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author azher
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;

/**
 *
 * @author Fighter
 */

public class HMM {
    int BP = 0;
    int numOfObservations = 3; //f 
    String[] observations = new String[] {"3","1","3"}; //f
    
    int numOfEmmissionsType = 3; //f
    String[] emmissionName = new String[] {"1","2","3"}; //f
    
    int numOfStates = 4; //f
    String[] statesName = new String[] {"Start","End","Hot","Cold"}; //f
    
    
    //f
    double[][] emissionProMat = new double[][]{
  { 0 ,  0 ,  0  },  //S
  { 0 ,  0 ,  0  },  //E
  {.2 , .4 , .4  },  //H
  {.5 , .4 , .1  }   //C
};
    
    //f
    double[][] transitionProMat = new double[][]{
  { 0 ,  0  , 0.8 , 0.2 }, //S
  { 0 ,  1  ,  0  ,  0  }, //E
  { 0 , 0.1 , 0.6 , 0.3 }, //H
  { 0 , 0.1 , 0.4 , 0.5 }  //C
};
 
    int[][] backpointer = new int[][]{
  //3    1     3  
  { 0 ,  0  ,  0 },//S
  { 0 ,  0  ,  0 },//E
  { 0 ,  0  ,  0 },//H
  { 0 ,  0  ,  0 },//C
};
    
    double[][] stateProbabiltyMatrix = new double[][]{
  //S    E     H    C
  { 0 ,  0  ,  0 ,  0},
  { 0 ,  0  ,  0 ,  0}, 
  { 0 ,  0  ,  0 ,  0}, 
};
    //or 
    
   Double[][][]  recordHolder3D = new Double[numOfObservations][numOfStates][numOfStates];
   
  void forwardAlgo(String input){
    
    initializeRecoldHoler3D();
    for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
  //      System.out.println("transitionNo = "+(transitionNo+1));
        
        for(int currentState = 0; currentState < numOfStates; currentState++){
    //        System.out.println("From "+ statesName[currentState]);
            
            for(int nextState = 0; nextState < numOfStates; nextState++){
   
                if(transitionNo==0&&input!=""){
                    int Startindex = Arrays.asList(statesName).indexOf(input);
                    BP = Startindex;       
                    if(Startindex == currentState){
                           recordHolder3D[transitionNo][currentState][nextState] = 
                           transitionProMat[currentState][nextState] * emissionProMat[nextState][Arrays.asList(emmissionName).indexOf(observations[transitionNo])];
                    }
                }
                else if(transitionNo==0&&input==""){
                           recordHolder3D[transitionNo][currentState][nextState] = 
                           transitionProMat[currentState][nextState] * emissionProMat[nextState][Arrays.asList(emmissionName).indexOf(observations[transitionNo])];  
                }        
                
                else if(transitionNo>0) {
                    recordHolder3D[transitionNo][currentState][nextState] = 
                    (transitionProMat[currentState][nextState] * emissionProMat[nextState][Arrays.asList(emmissionName).indexOf(observations[transitionNo])])
                    *(stateProbabiltyMatrix[transitionNo-1][currentState]);
                }
       
      //          System.out.println(""+recordHolder3D[transitionNo][currentState][nextState]);
            }
        }
        for(int i =0;i<numOfStates; i++){
            double sum1 = 0;
            for(int j =0;j<numOfStates; j++){
                sum1 = sum1 + recordHolder3D[transitionNo][j][i];
            }
            stateProbabiltyMatrix[transitionNo][i] = sum1;
        //    System.out.println("PM = "+stateProbabiltyMatrix[transitionNo][i]);
        }
    }
      
  }
  
	
  
  
  void showStateProbabiltyMatrix(){
      System.out.println("\nSTATE PROBABILITY MATRIX");
      for(int i = 0; i<numOfStates; i++){
          for(int j = 0; j<numOfObservations; j++){
             System.out.print(" "+stateProbabiltyMatrix[j][i]+"  ");
          }
          System.out.println("");
      }
  }
  
  
   
  
  void showRecordHoler3D(){
      
      System.out.println("\nRECORD HOLDER3D");
      for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
        System.out.println("TransitionNo = "+(transitionNo+1));
        
        for(int currentState = 0; currentState < numOfStates; currentState++){
            //System.out.println("From "+ statesName[previousState]+"\n");
                
            for(int nextState = 0; nextState < numOfStates; nextState++){       
               System.out.print(""+recordHolder3D[transitionNo][nextState][currentState]+"  ");
            }
            System.out.println("");
        }
          System.out.println("");
      }
      
  }
  
  
  
  void initializeRecoldHoler3D(){
      for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
        
        for(int previousState = 0; previousState < numOfStates; previousState++){
                
            for(int nextState = 0; nextState < numOfStates; nextState++){       
                recordHolder3D[transitionNo][previousState][nextState] = 0.0;
            }
        }
   
      }
  }
  
  
  
  
  
  
  
  
  //EXTRA NOT USED BELOW
  
  void propabilityOfEachTransition(){
      for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
        
        for(int previousState = 0; previousState < numOfStates; previousState++){
                
            for(int nextState = 0; nextState < numOfStates; nextState++){       
                System.out.println("");
                System.out.print("  "+recordHolder3D[transitionNo][previousState][nextState]+"  ");
                
            }
            System.out.println("");
        }
   
      }
  }
  
  
  
  void showOutputOfForwardAlgo(){
      
    //  initialize3DArray();
      for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
        System.out.println("transitionNo = "+(transitionNo+1));
          
        for(int previousState = 0; previousState < numOfStates; previousState++){
            System.out.println("From "+ statesName[previousState]);
                
            for(int nextState = 0; nextState < numOfStates; nextState++){       
               System.out.println(""+recordHolder3D[transitionNo][previousState][nextState]);
            }
        }
      }
      
  }
  
    
    
}


