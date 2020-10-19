package org.mediasoup.droid.lib;

import java.util.Locale;

public class UrlFactory {

  //private static final String HOSTNAME = "v3demo.mediasoup.org";
  private static final String HOSTNAME = "172.19.0.168";
  private static final int PORT = 4443;
  //private static final int PORT = 3000;

  public static String getInvitationLink(String roomId, boolean forceH264, boolean forceVP9) {
    //String url = String.format(Locale.US, "https://%s:%d/?roomId=%s", HOSTNAME, PORT, roomId);
    String url = String.format(Locale.US, "https://%s/?roomId=%s", HOSTNAME, roomId);
    if (forceH264) {
      url += "&forceH264=true";
    } else if (forceVP9) {
      url += "&forceVP9=true";
    }
    return url;
  }

  public static String getProtooUrl(
      String roomId, String peerId, boolean forceH264, boolean forceVP9) {
    String url =
        String.format(
            Locale.US, "wss://%s:%d/?roomId=%s&peerId=%s", HOSTNAME, PORT, roomId, peerId);
    if (forceH264) {
      url += "&forceH264=true";
    } else if (forceVP9) {
      url += "&forceVP9=true";
    }
    return url;
  }
}