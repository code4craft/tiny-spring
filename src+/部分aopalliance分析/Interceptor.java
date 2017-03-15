package org.aopalliance.intercept; 
  
import org.aopalliance.aop.Advice; 
  
/** 
 * This interface represents a generic interceptor. 
 * 
 * <p>A generic interceptor can intercept runtime events that occur 
 * within a base program. Those events are materialized by (reified 
 * in) joinpoints. Runtime joinpoints can be invocations, field 
 * access, exceptions...  
 * 
 * <p>This interface is not used directly. Use the the sub-interfaces 
 * to intercept specific events. For instance, the following class 
 * implements some specific interceptors in order to implement a 
 * debugger: 
 * 
 * <pre class=code> 
 * class DebuggingInterceptor implements MethodInterceptor,  
 *     ConstructorInterceptor, FieldInterceptor { 
 * 
 *   Object invoke(MethodInvocation i) throws Throwable { 
 *     debug(i.getMethod(), i.getThis(), i.getArgs()); 
 *     return i.proceed(); 
 *   } 
 * 
 *   Object construct(ConstructorInvocation i) throws Throwable { 
 *     debug(i.getConstructor(), i.getThis(), i.getArgs()); 
 *     return i.proceed(); 
 *   } 
 *  
 *   Object get(FieldAccess fa) throws Throwable { 
 *     debug(fa.getField(), fa.getThis(), null); 
 *     return fa.proceed(); 
 *   } 
 * 
 *   Object set(FieldAccess fa) throws Throwable { 
 *     debug(fa.getField(), fa.getThis(), fa.getValueToSet()); 
 *     return fa.proceed(); 
 *   } 
 * 
 *   void debug(AccessibleObject ao, Object this, Object value) { 
 *     ... 
 *   } 
 * } 
 * </pre> 
 * 
 * @see Joinpoint */
  
public interface Interceptor extends Advice { 
} 