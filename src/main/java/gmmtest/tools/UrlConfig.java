package gmmtest.tools;

import java.util.ResourceBundle;

/**
 * 连接properties
 * @author zhangli06.gw
 */
public class UrlConfig {

    protected static final ResourceBundle rd = ResourceBundle.getBundle("PageUrl");

    public static final String GMM_URL = rd.getString("gmmUrl");
    public static final String LOGIN_URL = rd.getString("loginUrl");
    public static final String GOSELL_URL = rd.getString("goSellUrl");

}