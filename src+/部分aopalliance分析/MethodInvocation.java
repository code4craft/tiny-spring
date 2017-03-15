package org.aopalliance.intercept; 
  
import java.lang.reflect.Method; 
  
/** 
 * Description of an invocation to a method, given to an interceptor 
 * upon method-call. 
 * 
 * <p>A method invocation is a joinpoint and can be intercepted by a method 
 * interceptor. 
 * 
 * @see MethodInterceptor */
public interface MethodInvocation extends Invocation 
{ 
  
    /** 
     * Gets the method being called. 
     * 
     * <p>This method is a frienly implementation of the {@link 
     * Joinpoint#getStaticPart()} method (same result). 
     * 
     * @return the method being called. 
     */
    Method getMethod(); 
  
} 