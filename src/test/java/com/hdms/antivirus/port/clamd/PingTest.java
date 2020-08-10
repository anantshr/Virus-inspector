package com.hdms.antivirus.port.clamd;

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
    ClamdVerifier clamdVerifier = new ClamdVerifier("localhost", 3310,100);
    assertTrue(clamdVerifier.ping());
  }
}
