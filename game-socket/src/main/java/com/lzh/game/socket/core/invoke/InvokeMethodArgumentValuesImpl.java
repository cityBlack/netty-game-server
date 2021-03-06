package com.lzh.game.socket.core.invoke;

import com.lzh.game.common.bean.HandlerMethod;
import com.lzh.game.common.serialization.ProtoBufUtils;
import com.lzh.game.socket.core.Request;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;

import java.util.Objects;

public class InvokeMethodArgumentValuesImpl implements InvokeMethodArgumentValues<Request>
        , ApplicationContextAware {

    private InnerParamDataBindHandler innerParamDataBindHandler;

    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private Object dataToObject(byte[] data, Class<?> target) {
        return ProtoBufUtils.deSerialize(data, target);
    }

    private Object[] getMethodArgumentValues(Request<byte[]> request, HandlerMethod handlerMethod) throws Exception {

        MethodParameter[] parameters = handlerMethod.getMethodParameters();
        Object[] args = new Object[parameters.length];

        boolean targetChange = false;

        for(int i = 0; i < parameters.length; ++i) {

            MethodParameter parameter = parameters[i];

            parameter.initParameterNameDiscovery(getParameterNameDiscoverer());

            if (innerParamDataBindHandler.isInnerParam(parameter)) {

                args[i] = innerParamDataBindHandler.conventData(request, parameter);

            } else if (!targetChange) {
                args[i] = dataToObject(request.data(), parameter.getParameterType());

                targetChange = true;

            } else if (args[i] == null) {
                throw new IllegalStateException("Could not resolve convent parameter at index "
                        + parameter.getParameterIndex() +
                        " in " + parameter.getExecutable().toGenericString() + ": "
                        + this.getArgumentResolutionErrorMessage("No suitable resolver for", i, handlerMethod));
            }
        }

        return args;
    }

    @Override
    public Object[] transfer(Request value, HandlerMethod handlerMethod) throws Exception {
        return this.getMethodArgumentValues(value, handlerMethod);
    }

    public ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    protected String getArgumentResolutionErrorMessage(String text, int index, HandlerMethod handlerMethod) {
        Class<?> paramType = handlerMethod.getMethodParameters()[index].getParameterType();
        return text + " argument " + index + " of type '" + paramType.getName() + "'";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Object o = applicationContext.getBean(InnerParamDataBindHandler.class);
        if (Objects.isNull(o)) {
            this.innerParamDataBindHandler = new DefaultInnerParam();
        } else {
            this.innerParamDataBindHandler = (InnerParamDataBindHandler) o;
        }
    }

    class DefaultInnerParam implements InnerParamDataBindHandler {

        @Override
        public Object conventData(Request request, MethodParameter parameter) {
            return null;
        }

        @Override
        public boolean isInnerParam(MethodParameter parameter) {
            return false;
        }
    }
}
