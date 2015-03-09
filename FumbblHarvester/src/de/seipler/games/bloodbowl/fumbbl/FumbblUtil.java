package de.seipler.games.bloodbowl.fumbbl;

import de.seipler.games.bloodbowl.util.HttpUtil;

/**
 * 
 * @author Georg Seipler
 */
public class FumbblUtil {
  
  public static String buildFumbblUrl(String pUrl) {
    StringBuffer result = new StringBuffer(IFumbblData.FUMBBL_URL);
    if (!pUrl.startsWith("/")) {
      result.append("/");
    }
    result.append(HttpUtil.decodeHtml(pUrl));
    return result.toString();
  }

}
