package org.example.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static Logger logger= LoggerFactory.getLogger(LoginInterceptor.class);


    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user_id=request.getParameter("user_id");
        String token=request.getParameter("token");
        String str="user_id"+user_id;
        String redis_token = (String) redisTemplate.opsForValue().get(str);
        if (token==null || user_id==null || token.equals("")||
                user_id.equals("") || redis_token==null|| !redis_token.equals(token)){
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            JSONObject res = new JSONObject();
            res.put("success", false);
            res.put("message", "对不起请登录");
            pw.write(res.toString());
            pw.flush();
            pw.close();
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
