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
    int RS = 0;
    int count=0;
    int numOfObservations = 3; //f 
    String[] observations = new String[] {"3","1","3"}; //f
    
    int numOfEmmissionsType = 3; //f
    String[] emmissionName = new String[] {"1","2","3"}; //f
    
    int numOfStates = 4; //f
    String[] statesName = new String[] {"Start","End","Hot","Cold"}; //f
    
    
//    double[] emissionProMatForHot = new double[] {.2,.4,.4 };
//    double[] emissionProMatForCold = new double[] {.5,.4,.1 };
//    //OR
    
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
    
    double[][] probabiltyMatrix = new double[][]{
  //S    E     H    C
  { 0 ,  0  ,  0 ,  0},
  { 0 ,  0  ,  0 ,  0}, 
  { 0 ,  0  ,  0 ,  0}, 
};
    //or 
    
   Double[][][]  recordHolder3D = new Double[numOfObservations][numOfStates][numOfStates];
   
  void forwardAlgo(String input){
    
    initialize3DArray();
    for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
        System.out.println("transitionNo = "+(transitionNo+1));
        
        for(int previousState = 0; previousState < numOfStates; previousState++){
            System.out.println("From "+ statesName[previousState]);
            
            for(int nextState = 0; nextState < numOfStates; nextState++){
   
                if(transitionNo==0&&input!=""){
                    int Startindex = Arrays.asList(statesName).indexOf(input);
                    RS = Startindex;       
                    if(Startindex == previousState){
                           recordHolder3D[transitionNo][previousState][nextState] = 
                           transitionProMat[previousState][nextState] * emissionProMat[nextState][Arrays.asList(emmissionName).indexOf(observations[transitionNo])];
                    }
                }
                else if(transitionNo==0&&input==""){
                           recordHolder3D[transitionNo][previousState][nextState] = 
                           transitionProMat[previousState][nextState] * emissionProMat[nextState][Arrays.asList(emmissionName).indexOf(observations[transitionNo])];  
                }        
                
                else if(transitionNo>0) {
                    recordHolder3D[transitionNo][previousState][nextState] = 
                    (transitionProMat[previousState][nextState] * emissionProMat[nextState][Arrays.asList(emmissionName).indexOf(observations[transitionNo])])
                    *(probabiltyMatrix[transitionNo-1][previousState]);
                }
       
                System.out.println(""+recordHolder3D[transitionNo][previousState][nextState]);
            }
    //   break;        
        }
        for(int i =0;i<numOfStates; i++){
            double sum1 = 0;
            for(int j =0;j<numOfStates; j++){
                sum1 = sum1 + recordHolder3D[transitionNo][j][i];
            }
            probabiltyMatrix[transitionNo][i] = sum1;
            System.out.println("PM = "+probabiltyMatrix[transitionNo][i]);
        }
       //break;
    }
      //System.out.println("count = "+count);
      count = 0;
      
      
  }
  double value=0;
  
   double arrays[][] = new double[numOfStates][numOfStates];
  
  void veterbialgo(){
    for(int transitionNo =0 ; transitionNo <numOfObservations; transitionNo++){
        initialize2DArray();
        if(transitionNo==0){
            int previousState=0;
            for(int nextState = 0; nextState < numOfStates; nextState++){       
               value= recordHolder3D[transitionNo][previousState][nextState];
               arrays[previousState][nextState]=value;
            }
        }
        else{
            for(int previousState = 0; previousState < numOfStates; previousState++){
           
                for(int nextState = 0; nextState < numOfStates; nextState++){       
                value= recordHolder3D[transitionNo][nextState][previousState];
                arrays[previousState][nextState]=value;
			     
                }
			
           
            }
        }
        findmax(arrays,transitionNo,numOfStates);
   
    }
	  
  }
  
  
  void findmax(double[][] arr,int transitionNo,int numOfStates){
    int row = 0; 
    int column = 0; 
    double maxValue = arr[0][0]; 

    
        for (int i = 0; i < numOfStates; i++)
        {
            for (int j = 0; j < numOfStates; j++)
            {
                    if (arr[i][j] > maxValue)
                    {
                             row = i;  
                             column = j; 
                             maxValue = arr[i][j];
                    }
            }
        }
        
  backpointer[row][transitionNo]=1;	
  
  }
  
  void veterbialgo1(){
    initialize2DArray();
      for(int transitionNo =0 ; transitionNo <numOfObservations; transitionNo++){
        if(transitionNo==0){
            //int previousState=0;  //generalize here
            for(int nextState = 0; nextState < numOfStates; nextState++){       
               value= recordHolder3D[transitionNo][RS][nextState];
               arrays[RS][nextState]=value;
            }
        }
        else{
            //for(int previousState = RS; previousState < numOfStates; previousState++){
           
                for(int nextState = 0; nextState < numOfStates; nextState++){       
                value= recordHolder3D[transitionNo][RS][nextState];
                arrays[RS][nextState]=value;
			     
                }
			
           
            
        }
        findmax1(arrays,transitionNo,numOfStates);
   
    }
	  
  }
  
  void findmax1(double[][] arr,int transitionNo,int numOfStates){
    //int row = 0; 
    int column = 0; 
    double maxValue = arr[RS][0]; 

    
            for (int j = 0; j < numOfStates; j++)
            {
                    if (arr[RS][j] > maxValue)
                    {
                           //  row = i;  
                             column = j; 
                             maxValue = arr[RS][j];
                    }
            }
        
    
    
    
    
	
  backpointer[column][transitionNo]=1;
  RS = column;
  
  }
  
    
    
    
    
	
   void initialize2DArray(){
      
        
        for(int previousState = 0; previousState < numOfStates; previousState++){
                
            for(int nextState = 0; nextState <numOfStates ; nextState++){       
              
                arrays[previousState][nextState] = 0;
                
            }
        }
   
      
      //System.err.println("count = "+count);
  }
    void printtest(){
	   
      for(int i = 0; i<4; i++){
          for(int j = 0; j<3; j++){
             System.out.print(" "+backpointer[i][j]+"  ");
          }
          System.out.println("");
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
               count++;
            }
        }
        System.out.println("count = "+count);
   
       //break;
      }
        System.out.println("count = "+count);
   
      
  }
  
  void initialize3DArray(){
      for(int transitionNo = 0; transitionNo < numOfObservations; transitionNo++){
        
        for(int previousState = 0; previousState < numOfStates; previousState++){
                
            for(int nextState = 0; nextState < numOfStates; nextState++){       
              
                recordHolder3D[transitionNo][previousState][nextState] = 0.0;
                count++;
            }
        }
   
      }
      //System.err.println("count = "+count);
  }
  
  void showlikelihoodMatrix(){
      System.out.println("");
      for(int i = 0; i<3; i++){
          for(int j = 0; j<4; j++){
             System.out.print(" "+probabiltyMatrix[i][j]+"  ");
          }
          System.out.println("");
      }
  }
  
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
  
    
    
}


