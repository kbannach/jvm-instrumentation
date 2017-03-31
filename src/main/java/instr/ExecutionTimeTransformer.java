package instr;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import service.Measure;

public class ExecutionTimeTransformer implements ClassFileTransformer {

   @Override
   public byte[] transform(ClassLoader loader, String className, Class< ? > classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
      byte[] result = bytes;
      if (classBeingRedefined.getAnnotation(Measure.class) != null) {
         try {
            String dotClassName = className.replace('/', '.');
            ClassPool cp = ClassPool.getDefault();
            CtClass ctClazz = cp.get(dotClassName);
            CtMethod method1 = ctClazz.getDeclaredMethod("doSomething");
            method1.addLocalVariable("elapsedTime", CtClass.longType);
            method1.insertBefore("elapsedTime = System.currentTimeMillis();");
            method1.insertAfter(String.format(
                  " { elapsedTime = System.currentTimeMillis() - elapsedTime; System.out.println(\" Method %s from class %s execution time = \" + elapsedTime + \" (actual arguments: $1, $2)\"); } ",
                  method1.getName(),
                  ctClazz.getName()));
            result = ctClazz.toBytecode();
         } catch (Throwable e) {
            e.printStackTrace();
         }
      }
      return result;
   }
}
