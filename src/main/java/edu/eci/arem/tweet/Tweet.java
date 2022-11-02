<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package edu.eci.arem.tweet;

import static spark.Spark.get;

=======


package edu.eci.arem.tweet;

>>>>>>> 18241f643a5742d6bbe7af01f5ebb7a415a60fde
/**
 *
 * @author cristian.castellanos
 */
public class Tweet {

    public static void main(String[] args) {
        System.out.println("Hello World!");
<<<<<<< HEAD
        get("/hello", (req, res) -> "Hello World!");
    }
    
    public static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }else{
            return 4567;
        }
=======
      
>>>>>>> 18241f643a5742d6bbe7af01f5ebb7a415a60fde
    }
}
