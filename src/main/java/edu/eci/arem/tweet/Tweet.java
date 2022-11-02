/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package edu.eci.arem.tweet;
import Spark.spark.port;
import Spark.spark.get;
/**
 *
 * @author cristian.castellanos
 */
public class Tweet {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        get("/hello")
    }
}
