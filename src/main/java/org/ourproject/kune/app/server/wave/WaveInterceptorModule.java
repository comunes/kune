package org.ourproject.kune.app.server.wave;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.wave.examples.fedone.waveserver.WaveClientRpcImpl;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class WaveInterceptorModule extends AbstractModule {
    public class WaveInterceptor implements MethodInterceptor {
        public WaveInterceptor() {
        }

        public Object invoke(final MethodInvocation invocation) throws Throwable {
            LOG.info("Wave method intercepted: " + invocation.getMethod().toString());
            return invocation.proceed();
        }
    }

    private static final Log LOG = LogFactory.getLog(WaveInterceptorModule.class);

    @Override
    protected void configure() {
        bindInterceptor(Matchers.identicalTo(WaveClientRpcImpl.class), Matchers.any(), new WaveInterceptor());
    }

}
