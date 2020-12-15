package com.astrotalk.live.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)`
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()
//				.antMatchers("/login/user").permitAll()
//				.antMatchers("/login/signup/user/email").permitAll().antMatchers("/login/signup/user/google")
//				.permitAll().antMatchers("/login/consultant").permitAll().antMatchers("/login/signup/consultant/google")
//				.permitAll().antMatchers("/login/admin").permitAll().antMatchers("/login/admin/google").permitAll()

                .antMatchers("/login/**").permitAll().antMatchers("/bulk-emailer/**").permitAll()
                .antMatchers("/user/web-view/login").permitAll().antMatchers("/user/api/v2/forget-password").permitAll()

                .antMatchers("/has-gst").permitAll().antMatchers("/get/conversion/rate").permitAll()
                .antMatchers("/recharge/amount/get").permitAll().antMatchers("/user/get/new/with/without/order")
                .permitAll()

                .antMatchers("/events/fill-intake-form").permitAll().antMatchers("/get/urls").permitAll()

                .antMatchers("/chat/message/share-c").permitAll().antMatchers("/api/v1/ask/question/get/to/share")
                .permitAll().antMatchers("/r/share").permitAll()

                .antMatchers("/ios/update/app/version").permitAll()

                .antMatchers("/order/share/visitorCount/update").permitAll()
                .antMatchers("/order/share/get").permitAll()

                .antMatchers("/notification/get/by/user").permitAll()
                .antMatchers("/user/forgot/password").permitAll()
                .antMatchers("/user/reset/password").permitAll()

                .antMatchers("/api/v1/add/update/seo/url").permitAll()
                .antMatchers("/api/v1/get/all/seo/url").permitAll()
                .antMatchers("/api/v1/get/seo/keys/by/url").permitAll()

                .antMatchers("/api/v1/horoscope/user/upload-files").permitAll()

                .antMatchers("/upload-files/v2").permitAll()
                .antMatchers("/delete-files").permitAll()

                .antMatchers("/consultant/type/get").permitAll()

                .antMatchers("/user/update-password").permitAll().antMatchers("/user/forgot-password").permitAll()
                .antMatchers("/user/forgot-password/verify-token").permitAll()

                .antMatchers("/user/send-otp/email").permitAll()

                .antMatchers("/isTokenExpired").permitAll().antMatchers("/horoscope/**").permitAll()

                .antMatchers("/url/create").permitAll()
                .antMatchers("/ios/isToShowInApp").permitAll()
                .antMatchers("/consultant/reset/promotional/order/size").permitAll()

                .antMatchers("/blog/article/save").permitAll().antMatchers("/blog/article/get").permitAll()

                .antMatchers("/call-chat/queue/show/waitlist").permitAll()

                .antMatchers("/payment-log/check-for-payments").permitAll()
                .antMatchers("/payment-log/check-for-payments-paypal").permitAll()
                .antMatchers("/plivo/make-astrologers-login").permitAll()

                .antMatchers("/admin/send-sms/**").permitAll()

                .antMatchers("/galary/**").permitAll()
                .antMatchers("/media/**").permitAll()

                .antMatchers("/offer/promotional/setAll/promotionalLocalFlagOff").permitAll()

                .antMatchers("/blog/article/update").permitAll()

                .antMatchers("/referral/send-otp").permitAll()

                .antMatchers("/consultant/set/total/order/size").permitAll()

                .antMatchers("/notification/get/for/user").permitAll()

                .antMatchers("/product-type/**").permitAll()

                .antMatchers("/consultant/getRepeatRate").permitAll()

                .antMatchers("/offer/promotional/isToShowPromotionalOffer/withoutLogin").permitAll()

                .antMatchers("/calling/get-struck-calls").permitAll()

                .antMatchers("/user/primary/verify-mobile/save").permitAll()

                .antMatchers("/home-screen").permitAll().antMatchers("/home-screen/with/api/keys").permitAll()

                .antMatchers("/consultant/upload-tax-files").permitAll()

                .antMatchers("/chat/order/call-alert/update/status").permitAll()

                .antMatchers("/admin/notification/send-cron-job").permitAll()

                .antMatchers("/test/**").permitAll().antMatchers("/knowlarity/**").permitAll()
                .antMatchers("/healthCheck/**").permitAll().antMatchers("/receipts/**").permitAll()

                .antMatchers("/pooja/type/v1/add-update").permitAll()

                .antMatchers("/video/**").permitAll()
                .antMatchers("/job-career/**").permitAll()

                .antMatchers("/isToVerifyMobile/firebase").permitAll()

                .antMatchers("/utils/**").permitAll().antMatchers("/exotel/**").permitAll().antMatchers("/plivo/**")
                .permitAll().antMatchers("/finance/**").permitAll()

                .antMatchers("/chat/api/v1/get-welcome-msg").permitAll()
                .antMatchers("/coupon/get/coupon/offer").permitAll()
                .antMatchers("/coupon/gpay/voucher").permitAll()

                .antMatchers("/get/customer/support").permitAll().antMatchers("/api/v1/get/version")
                .permitAll()

                .antMatchers("/report_type").permitAll().antMatchers("/pooja/type/v1/get").permitAll()
                .antMatchers("/cart/addon/get").permitAll().antMatchers("/cart/product/get").permitAll()
                .antMatchers("/cart/product/get/byId").permitAll().antMatchers("/consultant/get-list/sorting")
                .permitAll().antMatchers("/consultant/get/minimum/prices").permitAll()
                .antMatchers("/consultant/get/type").permitAll().antMatchers("/consultant/get/for/users/soultalk")
                .permitAll().antMatchers("/consultant/get/users/for/astroq").permitAll()
                .antMatchers("/consultant/get/consultant/for/users/v2").permitAll()
                .antMatchers("/consultant/get/by/slug").permitAll().antMatchers("/rating-review/getByConsulantId")
                .permitAll().antMatchers("/consultant/registration/create").permitAll()
                .antMatchers("/consultant/registration/update").permitAll().antMatchers("/consultant/get/for/homePage")
                .permitAll().antMatchers("/consultant/setAvgRatingAndTotalRating").permitAll()

                .antMatchers("/consultant/setRPAndRR").permitAll()

                .antMatchers("/consultant/get/slug").permitAll().antMatchers("/freeAPI/consultant/**").permitAll()

                .antMatchers("/consultant/get-list").permitAll().antMatchers("/consultant/get/category").permitAll()
                .antMatchers("/consultant/get/languague").permitAll().antMatchers("/consultant/get/skill").permitAll()

                .antMatchers("/consultant/setMinimumPriceForHomePage").permitAll()

                .antMatchers("/api/v1/get/question/by/Id").permitAll().antMatchers("/api/v1/get/all/question")
                .permitAll()

                .antMatchers("/rating-review/getByConsulantId/for/user").permitAll()

                .antMatchers("/rating-review/get/forProfile").permitAll()
                .antMatchers("/rating-review/get/forProfile/v2").permitAll()

                .antMatchers("/user/get/app/version").permitAll()

                .antMatchers("/language/clear/cache").permitAll()

                .antMatchers("/admin/send-sms/**").permitAll()

                .antMatchers("/crm-consultant-analytics/set-consultant-data").permitAll()

                .antMatchers("/user/api/v1/get_notification").permitAll()
                .antMatchers("/isToShowHoroscope").permitAll()

                .antMatchers("/cricket/**").permitAll().antMatchers("/socket-chat/**").permitAll()
                .antMatchers("/admin-consultant-chat/send/admin/message/socket").permitAll()
                .antMatchers("/admin/notification/send-chat-message/toAll").permitAll()
                .antMatchers("/corporate/partners/employee/register").permitAll()
                .antMatchers("/corporate/partners/employee/login").permitAll()
                .antMatchers("/corporate/partners/employee/login/fb/kit").permitAll()
                .antMatchers("/corporate/partners/employee/upload/register/multiple/").permitAll()
                .antMatchers("/corporate/partners/register").permitAll()
                .antMatchers("/corporate/partners/get-all/approved").permitAll()
                .antMatchers("/corporate/partners/get/by/slug").permitAll().antMatchers("/user/start/frequency/mapping")
                .permitAll().antMatchers("/user/start/frequency/mapping/today").permitAll()
                .antMatchers("/chat/order/get/details/for/kundli").permitAll()
                .antMatchers("/user/start/max/frequency/mapping").permitAll()
//				.antMatchers("/astrohub/query/question/correct").permitAll()
                .antMatchers("/user/update/default/notif/time").permitAll()
                .antMatchers("/user/correct/frequency/mapping/old").permitAll()

                .antMatchers("/api/keys/**").permitAll()
                .antMatchers("/affiliate/register").permitAll()
                .antMatchers("/affiliate/check/coupon/code").permitAll()
                .antMatchers("/rating-review/getByConsulantId/v2").permitAll()

                .antMatchers("/banner/**").permitAll()
                .antMatchers("/consultant-gallery/get/for/user").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();

        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    }



    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

}
