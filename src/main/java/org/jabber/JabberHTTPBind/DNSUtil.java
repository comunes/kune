/**
 * $RCSfile: DNSUtil.java,v $
 * $Revision: 1.1 $
 * $Date: 2005/12/08 12:01:05 $
 *
 * Copyright (C) 2004-2005 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the Apache License
 */

package org.jabber.JabberHTTPBind;

import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import java.util.Hashtable;

/**
 * Utilty class to perform DNS lookups for XMPP services.
 *
 * @author Matt Tucker
 */
public class DNSUtil
{
    private static DirContext context;
    
    static
    {
        try
        {
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            context = new InitialDirContext(env);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the host name and port that the specified XMPP server can be
     * reached at for communication. A DNS lookup for a SRV
     * record in the form "_xmpp-server._tcp.example.com" is attempted, according
     * to section 14.4 of RFC 3920. If that lookup fails, a lookup in the older form
     * of "_jabber._tcp.example.com" is attempted since servers that implement an
     * older version of the protocol may be listed using that notation. If that
     * lookup fails as well, it's assumed that the XMPP server lives at the
     * host resolved by a DNS lookup at the specified domain on the specified
     * default port.<p>
     *
     * As an example, a lookup for "example.com" may return "im.example.com:5269".
     *
     * @param domain the domain.
     * @return a HostAddress, which encompasses the hostname and port that the XMPP
     *      server can be reached at for the specified domain.
     */
    public static HostAddress resolveXMPPServerDomain(String domain, int defaultport)
    {
        if (context == null)
        {
            return new HostAddress(domain, defaultport);
        }
        String host = domain;
        int port = defaultport;
        
        try
        {
            Attributes dnsLookup = context.getAttributes("_xmpp-client._tcp." + domain);
            String srvRecord = (String)dnsLookup.get("SRV").get();
            String [] srvRecordEntries = srvRecord.split(" ");
            port = Integer.parseInt(srvRecordEntries[srvRecordEntries.length-2]);
            host = srvRecordEntries[srvRecordEntries.length-1];
        }
        catch (Exception e)
        {
            // Attempt lookup with older "jabber" name.
            try
            {
                Attributes dnsLookup = context.getAttributes("_jabber-client._tcp." + domain);
                String srvRecord = (String)dnsLookup.get("SRV").get();
                String [] srvRecordEntries = srvRecord.split(" ");
                port = Integer.parseInt(srvRecordEntries[srvRecordEntries.length-2]);
                host = srvRecordEntries[srvRecordEntries.length-1];
            }
            catch (Exception e2)
            { }
        }
        // Host entries in DNS should end with a ".".
        if (host.endsWith("."))
        {
            host = host.substring(0, host.length()-1);
        }
        return new HostAddress(host, port);
    }
    
    /**
     * Encapsulates a hostname and port.
     */
    public static class HostAddress
    {
        
        private String host;
        private int port;
        
        private HostAddress(String host, int port)
        {
            this.host = host;
            this.port = port;
        }
        
        /**
         * Returns the hostname.
         *
         * @return the hostname.
         */
        public String getHost()
        {
            return host;
        }
        
        /**
         * Returns the port.
         *
         * @return the port.
         */
        public int getPort()
        {
            return port;
        }
        
        public String toString()
        {
            return host + ":" + port;
        }
    }
}










