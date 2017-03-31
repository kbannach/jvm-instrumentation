package com.example.jvmint.instr;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.junit.Test;

public class InstrTest {

   private static final String url = "http://localhost:4567/";

   @Test
   public void agentTest() throws MalformedURLException, IOException {
      InputStream response = new URL(url + "add/2/3").openStream();
      try (Scanner sc = new Scanner(response)) {
         assertEquals("5", sc.nextLine().trim());
      }
   }
}
