package com.model2.mvc.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * xml에 선언적으로 aspect를 적용한다.
 */

public class LogAspectJ {

	public LogAspectJ() {
		System.out.println("Common ::"+ this.getClass());
	}
	
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
			
		System.out.println(" ");
		System.out.println("[Around before] targetObject method"+
													joinPoint.getTarget().getClass().getName() +"."+
													joinPoint.getSignature().getName());
		
		if(joinPoint.getArgs().length !=0){
			System.out.println("[Around before] Argument to method : "+ joinPoint.getArgs()[0]);
		}
		
		Object obj = joinPoint.proceed();

		System.out.println("[Around after] targetObject return value  : "+obj);
		System.out.println("");
		
		return obj;
	}
}