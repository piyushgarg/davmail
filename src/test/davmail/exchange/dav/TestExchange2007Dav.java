/*
 * DavMail POP/IMAP/SMTP/CalDav/LDAP Exchange Gateway
 * Copyright (C) 2010  Mickael Guessant
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package davmail.exchange.dav;

import davmail.AbstractExchange2007TestCase;
import davmail.Settings;
import davmail.exchange.ExchangeSessionFactory;
import davmail.exchange.auth.ExchangeFormAuthenticator;
import davmail.exchange.auth.HC4ExchangeFormAuthenticator;
import org.apache.log4j.Level;

import java.io.IOException;

/**
 * Test cases for on premise Exchange 2007 in DAV mode.
 * Get connection info from test.properties
 */
public class TestExchange2007Dav extends AbstractExchange2007TestCase {

    public void testSimpleUsernameOWAFormAuthenticator() throws IOException {
        String url = "https://" + server + "/owa";
        ExchangeFormAuthenticator authenticator = new ExchangeFormAuthenticator();
        authenticator.setUrl(url);
        authenticator.setUsername(username);
        authenticator.setPassword(password);
        authenticator.authenticate();
        assertEquals("https://" + server + "/owa/", authenticator.getExchangeUri().toString());
        // create session
        DavExchangeSession session = new DavExchangeSession(authenticator.getHttpClient(),
                authenticator.getExchangeUri(), authenticator.getUsername());
        assertEquals(username, session.getAlias());
        assertEquals(email, session.getEmail());
        assertEquals("/exchange/" + email + "/", session.getFolderPath(""));
    }

    public void testDomainUsernameOWAFormAuthenticator() throws IOException {
        String url = "https://" + server + "/owa";
        ExchangeFormAuthenticator authenticator = new ExchangeFormAuthenticator();
        authenticator.setUrl(url);
        authenticator.setUsername(domain + "\\" + username);
        authenticator.setPassword(password);
        authenticator.authenticate();
        // create session
        DavExchangeSession session = new DavExchangeSession(authenticator.getHttpClient(),
                authenticator.getExchangeUri(), authenticator.getUsername());
        assertEquals(username, session.getAlias());
        assertEquals(email, session.getEmail());
        assertEquals("/exchange/" + email + "/", session.getFolderPath(""));
    }

    public void testSimpleUsernameExchangeFormAuthenticator() throws IOException {
        String url = "https://" + server + "/exchange";
        ExchangeFormAuthenticator authenticator = new ExchangeFormAuthenticator();
        authenticator.setUrl(url);
        authenticator.setUsername(username);
        authenticator.setPassword(password);
        authenticator.authenticate();
        // create session
        DavExchangeSession session = new DavExchangeSession(authenticator.getHttpClient(),
                authenticator.getExchangeUri(), authenticator.getUsername());
        assertEquals(username, session.getAlias());
        assertEquals(email, session.getEmail());
        assertEquals("/exchange/" + email + "/", session.getFolderPath(""));
    }

    public void testDomainUsernameExchangeFormAuthenticator() throws IOException {
        String url = "https://" + server + "/exchange";
        ExchangeFormAuthenticator authenticator = new ExchangeFormAuthenticator();
        authenticator.setUrl(url);
        authenticator.setUsername(domain + "\\" + username);
        authenticator.setPassword(password);
        authenticator.authenticate();
        // create session
        DavExchangeSession session = new DavExchangeSession(authenticator.getHttpClient(),
                authenticator.getExchangeUri(), authenticator.getUsername());
        assertEquals(username, session.getAlias());
        assertEquals(email, session.getEmail());
        assertEquals("/exchange/" + email + "/", session.getFolderPath(""));
    }

    /**
     * Check old preauth authentication process.
     * Format is preauthusername"username
     *
     * @throws IOException on error
     */
    public void testPreauthUsernameExchangeFormAuthenticator() throws IOException {
        String url = "https://" + server + "/exchange";
        ExchangeFormAuthenticator authenticator = new ExchangeFormAuthenticator();
        authenticator.setUrl(url);
        authenticator.setUsername(username + "\"" + domain + "\\" + username);
        authenticator.setPassword(password);
        authenticator.authenticate();
        // create session
        DavExchangeSession session = new DavExchangeSession(authenticator.getHttpClient(),
                authenticator.getExchangeUri(), authenticator.getUsername());
        assertEquals(username, session.getAlias());
        assertEquals(email, session.getEmail());
        assertEquals("/exchange/" + email + "/", session.getFolderPath(""));
    }

    public void testCreateSession() throws IOException {
        Settings.setProperty("davmail.url", "https://" + server + "/owa");
        Settings.setProperty("davmail.defaultDomain", domain);
        ExchangeSessionFactory.getInstance(username, password);
    }

    public void testHC4OWAFormAuthenticator() throws IOException {
        String url = "https://" + server + "/owa";
        HC4ExchangeFormAuthenticator authenticator = new HC4ExchangeFormAuthenticator();
        authenticator.setUrl(url);
        authenticator.setUsername(username);
        authenticator.setPassword(password);

        //Settings.setLoggingLevel("org.apache.http.wire", Level.DEBUG);
        //Settings.setLoggingLevel("org.apache.http", Level.DEBUG);

        authenticator.authenticate();
        assertEquals("https://" + server + "/owa/", authenticator.getExchangeUri().toString());

        //Settings.setLoggingLevel("httpclient.wire", Level.DEBUG);
        // create session
        DavExchangeSession session = new DavExchangeSession(authenticator.getHttpClient(),
                authenticator.getExchangeUri(), authenticator.getUsername());
        assertEquals(username, session.getAlias());
        assertEquals(email, session.getEmail());
        assertEquals("/exchange/" + email + "/", session.getFolderPath(""));
    }
}
