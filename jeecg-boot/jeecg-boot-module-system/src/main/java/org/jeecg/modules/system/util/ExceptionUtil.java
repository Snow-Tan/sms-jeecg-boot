package org.jeecg.modules.system.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionUtil
 * @Description:
 * @Author: lijianwei
 * @DATE: 2020/9/28 16:16
 **/
public class ExceptionUtil {

    public static String getErrorStack(Exception e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
        return "";
    }
}
