package com.example.jvmint.instr;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.App;
import spark.Spark;

public class InstrIT {

   private static final String url = "http://localhost:4567/";

   @BeforeClass
   public static void beforeClass() throws InterruptedException {
      // run server
      App.main(null);
      Thread.sleep(1000); // wait for the server to start
   }

   @AfterClass
   public static void afterClass() {
      // stop server
      Spark.stop();
   }

   @Test
   public void agentTest1() throws MalformedURLException, IOException {
      InputStream response = new URL(url + "add/2/3").openStream();
      try (Scanner sc = new Scanner(response)) {
         assertEquals("5", sc.nextLine().trim());
      }
   }

   @Test
   public void agentTest2() throws MalformedURLException, IOException {
      InputStream response = new URL(url + "add/3/3").openStream();
      try (Scanner sc = new Scanner(response)) {
         assertEquals("6", sc.nextLine().trim());
      }
   }

   @Test
   public void agentTest3() throws MalformedURLException, IOException {
      InputStream response = new URL(url + "add/4/3").openStream();
      try (Scanner sc = new Scanner(response)) {
         assertEquals("7", sc.nextLine().trim());
      }
   }
}
