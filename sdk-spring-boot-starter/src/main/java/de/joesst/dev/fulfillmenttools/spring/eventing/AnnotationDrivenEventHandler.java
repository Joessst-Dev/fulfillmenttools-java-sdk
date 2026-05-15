package de.joesst.dev.fulfillmenttools.spring.eventing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default {@link FulfillmenttoolsEventHandler} that routes events to methods annotated with
 * {@link FulfillmenttoolsEventListener}.
 *
 * <p>Scans all Spring beans after singletons are instantiated, building an internal dispatch map
 * keyed by event type. Two calling conventions are supported per handler method:
 * <ul>
 *   <li><b>Payload only</b> — SDK auto-acks on success, auto-nacks on exception.</li>
 *   <li><b>Payload + {@link FulfillmenttoolsEvent}</b> — caller controls ack/nack explicitly.</li>
 * </ul>
 * Events with no registered handler are silently auto-acked.
 */
class AnnotationDrivenEventHandler
        implements FulfillmenttoolsEventHandler, ApplicationContextAware, SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(AnnotationDrivenEventHandler.class);

    private ApplicationContext applicationContext;
    private final Map<String, List<HandlerMethod>> handlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean;
            try {
                bean = applicationContext.getBean(beanName);
            } catch (Exception e) {
                continue;
            }
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            for (Method method : targetClass.getMethods()) {
                FulfillmenttoolsEventListener annotation =
                        AnnotationUtils.findAnnotation(method, FulfillmenttoolsEventListener.class);
                if (annotation == null) {
                    continue;
                }
                HandlerMethod handlerMethod = new HandlerMethod(bean, method);
                for (String eventType : annotation.value()) {
                    handlerMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handlerMethod);
                }
            }
        }
        if (!handlerMap.isEmpty()) {
            int total = handlerMap.values().stream().mapToInt(List::size).sum();
            log.info("Registered {} @FulfillmenttoolsEventListener method(s) across {} event type(s)",
                    total, handlerMap.size());
        }
    }

    @Override
    public void onEvent(FulfillmenttoolsEvent<?> event) {
        List<HandlerMethod> handlers = handlerMap.get(event.eventType());
        if (handlers == null || handlers.isEmpty()) {
            log.warn("No @FulfillmenttoolsEventListener registered for event type '{}', auto-acking", event.eventType());
            event.ack();
            return;
        }
        for (HandlerMethod handler : handlers) {
            handler.invoke(event);
        }
    }

    private static final class HandlerMethod {

        private final Object bean;
        private final Method method;
        private final boolean hasEventParam;

        HandlerMethod(Object bean, Method method) {
            this.bean = bean;
            this.method = method;
            this.hasEventParam = detectEventParam(method);
        }

        private static boolean detectEventParam(Method method) {
            for (Parameter param : method.getParameters()) {
                if (FulfillmenttoolsEvent.class.isAssignableFrom(param.getType())) {
                    return true;
                }
            }
            return false;
        }

        void invoke(FulfillmenttoolsEvent<?> event) {
            Object[] args = resolveArgs(event);
            try {
                method.invoke(bean, args);
                if (!hasEventParam) {
                    event.ack();
                }
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause() != null ? e.getCause() : e;
                if (!hasEventParam) {
                    log.error("@FulfillmenttoolsEventListener method '{}' threw, nacking",
                            method.getName(), cause);
                    event.nack();
                } else {
                    log.error("@FulfillmenttoolsEventListener method '{}' threw",
                            method.getName(), cause);
                }
            } catch (IllegalAccessException e) {
                log.error("Cannot invoke @FulfillmenttoolsEventListener method '{}'",
                        method.getName(), e);
                if (!hasEventParam) {
                    event.nack();
                }
            }
        }

        private Object[] resolveArgs(FulfillmenttoolsEvent<?> event) {
            Parameter[] params = method.getParameters();
            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                if (FulfillmenttoolsEvent.class.isAssignableFrom(params[i].getType())) {
                    args[i] = event;
                } else {
                    args[i] = event.payload();
                }
            }
            return args;
        }
    }
}
