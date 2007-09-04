package org.ourproject.kune.platf.server.rpc;

/**
 * marker interface (should not contain any method!)
 * 
 * all the RPC objects should:
 * <ul>
 * <li>implement a RemoteService interface in the server side</li>
 * <li>convert from domain objects to dto objects</li>
 * <li>has userHash protected methods</li>
 * <li>contains little (or nothing) bussines logic (should delegate to other
 * services/managers)</li>
 * </ul>
 * 
 */
public interface RPC {

}
