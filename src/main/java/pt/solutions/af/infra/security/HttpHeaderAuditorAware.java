package pt.solutions.af.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class HttpHeaderAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        var userId = HttpServletRequestUtil.getCurrentRequest().getHeader("X-AF-UserId");
        return Optional.ofNullable(userId);
    }

    public static class HttpServletRequestUtil {

        public static HttpServletRequest getCurrentRequest() {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                return requestAttributes.getRequest();
            }
            throw new IllegalStateException("No current HttpServletRequest found");
        }
    }
}
