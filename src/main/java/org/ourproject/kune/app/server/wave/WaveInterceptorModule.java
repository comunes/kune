package org.ourproject.kune.app.server.wave;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.wave.examples.fedone.waveserver.WaveClientRpcImpl;
import org.waveprotocol.wave.examples.fedone.waveserver.WaveServerModule;
import org.waveprotocol.wave.examples.fedone.waveserver.WaveClientRpc.ProtocolOpenRequest;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class WaveInterceptorModule extends AbstractModule {
    public class WaveInterceptor implements MethodInterceptor {
        public WaveInterceptor() {
        }

        public Object invoke(final MethodInvocation invocation) throws Throwable {
            if (invocation.getMethod().getName().equals("open")) {
                final ProtocolOpenRequest openRequest = (ProtocolOpenRequest) (invocation.getArguments()[1]);
                LOG.info("Wave open method intercepted of: " + openRequest.getParticipantId());
            }
            if (invocation.getMethod().getName().equals("create")) {
                LOG.info("Wave create method intercepted");
            }
            return invocation.proceed();
        }
    }

    private static final Log LOG = LogFactory.getLog(WaveInterceptorModule.class);

    @Override
    protected void configure() {
        // WaveClientRpcImpl.class.getMethod("open", RpcController.class,
        // ProtocolOpenRequest.class, RpcCallback<ProtocolWaveletUpdate>.class)
        // new WaveClientRpcImpl(null).open(null, null, null)
        final WaveInterceptor waveInterceptor = new WaveInterceptor();
        bindInterceptor(Matchers.identicalTo(WaveClientRpcImpl.class), Matchers.any(), waveInterceptor);
        bindInterceptor(Matchers.identicalTo(WaveServerModule.class), Matchers.any(), waveInterceptor);
    }
}
