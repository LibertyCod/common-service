package com.liberty.share.sign;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Binbin Wang
 * @date 2018/4/3
 */

public class SignAuthenticationFilter extends OncePerRequestFilter {

    private Set<String> excludesPattern;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = httpServletRequest.getRequestURI();

        if (isExclusion(requestURI)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String key = httpServletRequest.getParameter("key");
            String timestamp = httpServletRequest.getParameter("timestamp");
            String sign = httpServletRequest.getParameter("sign");

            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)) {
                httpServletResponse.getWriter().write(
                        JSON.toJSONString(ApiResponse.getErrResponse(BizErrorCode.PERMISSION_DENIED)));
                return;
            }

            Map<String, String[]> requestParamsM = httpServletRequest.getParameterMap();
            Map<String, String> paramsM = new HashMap<>(requestParamsM.size() - 3);

            for (Map.Entry<String, String[]> entry : requestParamsM.entrySet()) {
                if (!"key".equals(entry.getKey()) && !"timestamp".equals(entry.getKey()) && !"sign".equals(entry.getKey())) {
                    paramsM.put(entry.getKey(), entry.getValue()[0]);
                }
            }

            String secret = getSecret(timestamp);
            String corSign = null;
            try {
                corSign = SignHelper.genSig(requestURI, paramsM, secret);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if (!sign.equals(corSign)) {
                httpServletResponse.getWriter().write(
                        JSON.toJSONString(ApiResponse.getErrResponse(BizErrorCode.SIG_KEY_MUST_NOT_BLANK)));
                return;
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }

    private boolean isExclusion(String requestURI) {
        if(this.excludesPattern == null) {
            return false;
        } else {
            String contextPath = getServletContext().getContextPath();
            if(contextPath != null && requestURI.startsWith(contextPath)) {
                requestURI = requestURI.substring(contextPath.length());
                if(!requestURI.startsWith("/")) {
                    requestURI = "/" + requestURI;
                }
            }

            Iterator i = this.excludesPattern.iterator();

            String pattern;
            do {
                if(!i.hasNext()) {
                    return false;
                }
                pattern = (String)i.next();
            } while(!this.pathMatcher.match(pattern, requestURI));

            return true;
        }
    }

    @Override
    protected void initFilterBean() throws ServletException {
        String param = this.getFilterConfig().getInitParameter("exclusions");
        if(!StringUtils.isEmpty(param)) {
            this.excludesPattern = new HashSet(Arrays.asList(param.split("\\s*,\\s*")));
        }
        super.initFilterBean();
    }

    private String getSecret(String timestamp) {
        int timeLenth = String.valueOf(System.currentTimeMillis()).length() - 3;
        return timestamp.substring(0, timeLenth);
    }

}
