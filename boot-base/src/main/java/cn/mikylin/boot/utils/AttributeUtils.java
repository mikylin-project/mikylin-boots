package cn.mikylin.boot.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * http servlet attribute utils.
 *
 * @author mikylin
 * @date 20200506
 */
public final class AttributeUtils {

    public static ServletRequestAttributes getAttribute() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if(ra == null)
            return null;
        return (ServletRequestAttributes) ra;
    }

    public static HttpServletRequest getRequst() {
        return getAttribute().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getAttribute().getResponse();
    }

    public static HttpSession getSession() {
        return getRequst().getSession();
    }

    public static void setSessionAttribute(String key,Object value) {
        getAttribute().setAttribute(key,value,RequestAttributes.SCOPE_SESSION);
    }

    public static Object getSessionAttribute(String key) {
        return getAttribute().getAttribute(key,RequestAttributes.SCOPE_SESSION);
    }

    public static String getHeader(String key) {
        return getRequst().getHeader(key);
    }

    public static void destorySession() {
        getSession().invalidate();
    }
}
