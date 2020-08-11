package com.hdms.antivirus.port.clamd;

import com.hdms.antivirus.config.ClamdConfig;
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
    ClamdConfig config = new ClamdConfig ( "localhost", 3310,500);
    ClamdVerifier clamdVerifier = new ClamdVerifier(config);
    assertTrue(clamdVerifier.ping());
  }
}
