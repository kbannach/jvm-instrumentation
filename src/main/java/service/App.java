package service;

import static spark.Spark.get;
import instr.Measure;

public class App {

   public static void main(String[] args) {
      get("/hello", (req, res) -> "Hello World");
      get("/add/:first/:second", (req, res) -> "" + calculateSum(req.params("first"), req.params("second")));
   }

   @Measure
   private static int calculateSum(String first, String second) {
      try {
         Thread.sleep(parseParamToInt(first) * 1000);
      } catch (InterruptedException e) {
         // empty
      }
      return parseParamToInt(first) + parseParamToInt(second);
   }

   private static int parseParamToInt(String params) {
      return Integer.valueOf(params);
   }
}
