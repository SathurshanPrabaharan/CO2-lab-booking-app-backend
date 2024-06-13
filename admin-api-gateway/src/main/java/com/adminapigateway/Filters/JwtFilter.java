//package com.adminapigateway.Filters;
//
//@RequiredArgsConstructor
//@Slf4j
//public class JwtFilter extends OncePerRequestFilter {
//
//
//    private final TokenSetting setting;
//
//    private String resolveToken(HttpServletRequest request)
//    {
//        String bearerTkn = request.getHeader("Authorization");
//
//        if(hasText(bearerTkn) && bearerTkn.startsWith("Bearer ")) return bearerTkn.substring(7);
//        return null;
//
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String jwt = resolveToken(request);
//        String uri = request.getRequestURI();
//
//        log.info("요청 URL : {}",uri);
//
//        if(hasText(jwt))
//        {
//            System.out.println("토큰 존재");
//        }
//        else
//        {
//            System.out.println("토큰 존재 X");
//        }
//        filterChain.doFilter(request,response);
//    }
//}
