package instr;

import java.lang.instrument.Instrumentation;

public class AppAgent {

   public static void premain(String agentArgs, Instrumentation inst) {
      // Transformer registration 
      inst.addTransformer(new ExecutionTimeTransformer());
   }
}
