package tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class MyTransformer implements IAnnotationTransformer {
	@Override
	public void transform(ITestAnnotation arg0, @SuppressWarnings("rawtypes") Class arg1, @SuppressWarnings("rawtypes") Constructor arg2, Method arg3) {
		String temp = arg3.getName();
		for (String s : Runner.tcl.keySet()) {
			if(temp.equals(s)) {
				
				if(Runner.tcl.get(s).equalsIgnoreCase("No")){
					arg0.setEnabled(false);
				}	
			}
		}
	}
}
