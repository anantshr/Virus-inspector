package com.hdms.antivirus.port.clamd;

import com.hdms.antivirus.infrastructure.clamd.ClamdVerifier;
import com.hdms.antivirus.infrastructure.clamd.config.ClamdConfig;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertTrue;

/**
 * These tests assume clamd is running and responding in the virtual machine. 
 */
public class PingTest {


  @Test
  public void testPingPong() throws UnknownHostException, IOException  {
    ClamdConfig config = new ClamdConfig ( "localhost", 3310,500,"200000KB","200000KB");
    ClamdVerifier clamdVerifier = new ClamdVerifier(config);
    assertTrue(clamdVerifier.ping());
  }
}
