package instr;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import service.Measure;

public class ExecutionTimeTransformer implements ClassFileTransformer {

   @Override
   public byte[] transform(ClassLoader loader, String className, Class< ? > classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
      try {
         if (className == null || className.startsWith("javassist") || className.startsWith("java/lang"))
            return null; // didn't change anything
         String dotClassName = className.replace('/', '.');
         ClassPool cp = ClassPool.getDefault();
         CtClass ctClazz = cp.get(dotClassName);
         for (CtMethod m : ctClazz.getDeclaredMethods()) {
            if (m.getAnnotation(Measure.class) != null) {
               m.addLocalVariable("elapsedTime", CtClass.longType);
               m.insertBefore("elapsedTime = System.currentTimeMillis();");
               m.insertAfter(String.format(
                     " { elapsedTime = System.currentTimeMillis() - elapsedTime; System.out.println(\"[MEASUREMENT] Method %s from class %s execution time = \" + elapsedTime + \"ms (actual arguments: $1, $2)\"); } ",
                     m.getName(),
                     ctClazz.getName()));
            }
         }
         return ctClazz.toBytecode();
      } catch (NotFoundException e) {
         // ignore
      } catch (Throwable e) {
         e.printStackTrace(System.out);
      }
      return null; // didn't change anything
   }
}
