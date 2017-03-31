package service;

import static spark.Spark.get;

public class App {

   public static void main(String[] args) {
      get("/hello", (req, res) -> "Hello World");
      get("/add/:first/:second", (req, res) -> "" + calculateSum(parseParamToInt(req.params("first")) + parseParamToInt(req.params("second")))//
      );
      // TODO is number check
   }

   @Measure
   private static String calculateSum(int i) {
      // TODO Auto-generated method stub
      return null;
   }

   private static int parseParamToInt(String params) {
      return Integer.valueOf(params);
   }
}
