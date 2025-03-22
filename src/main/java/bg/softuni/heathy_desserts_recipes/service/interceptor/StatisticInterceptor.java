package bg.softuni.heathy_desserts_recipes.service.interceptor;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StatisticInterceptor implements HandlerInterceptor {
    private static final String BASE_PATH = ".\\src\\main\\resources\\visit-statistic.txt";


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {

        String ipAddress = getIpAddressFromRequest(request);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String date = formatter.format(LocalDateTime.now());

        String message = String.format("User with IP: %s watch all recipes on %s.%n", ipAddress, date);


        try {
            File file = new File(BASE_PATH);

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(message);
            bw.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String getIpAddressFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddr = (ip == null) ? request.getRemoteAddr() : ip;
        if (ipAddr!=null && !ipAddr.equals("")) {
            return ipAddr;
        }
        return null;
    }


}