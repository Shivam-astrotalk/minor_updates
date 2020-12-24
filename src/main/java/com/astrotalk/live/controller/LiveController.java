package com.astrotalk.live.controller;

import com.astrotalk.live.JSONUtils;
import com.astrotalk.live.model.*;
import com.astrotalk.live.service.LiveService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@RequestMapping("/live/event")
@Slf4j
public class LiveController {

    @Autowired
    LiveService liveService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CONSULTANT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity fillIntakeForm(@RequestBody LiveEvent liveEvent, HttpServletRequest request) throws JSONException {
        liveService.create(liveEvent);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity update(@RequestBody LiveEvent liveEvent, HttpServletRequest request) throws JSONException {
        liveService.update(liveEvent);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }
    @PostMapping("/update/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CONSULTANT')")
    public ResponseEntity approve(@RequestParam long eventId, @RequestParam Status status, HttpServletRequest request) throws JSONException {
        liveService.updateStatus(eventId, status);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @PostMapping("/update/link")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CONSULTANT')")
    public ResponseEntity approve(@RequestParam long eventId, @RequestParam String link,@RequestParam boolean goLive, HttpServletRequest request) throws JSONException {
        liveService.updateLink(eventId, link,goLive);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @PostMapping("/user/subscribe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> subscribe(@RequestParam long eventId, @RequestParam String username,
                                            @RequestParam long userId, HttpServletRequest request) throws JSONException {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(JSONUtils.getFailJson(),HttpStatus.UNAUTHORIZED);
        try {
            String token = liveService.joinEvent(userId, eventId, username);
            log.info("Sending token : " + token);
            return new ResponseEntity(JSONUtils.getSuccessJson("token",token),HttpStatus.OK);
        } catch (LiveException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/activity/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity activity(@RequestParam long eventId, @RequestParam long fromId, @RequestParam long userId, HttpServletRequest request) throws JSONException {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(JSONUtils.getFailJson(),HttpStatus.UNAUTHORIZED);
        try {
            List<LiveEventActivity> activityList = liveService.getActivity(fromId, eventId, userId);
            return new ResponseEntity(activityList,HttpStatus.OK);
        } catch (LiveException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/activity/consultant")
    @PreAuthorize("hasRole('ROLE_CONSULTANT')")
    public ResponseEntity activity(@RequestParam long eventId, @RequestParam long fromId, HttpServletRequest request) {
        try {
            List<LiveEventActivity> activityList = liveService.getActivity(fromId, eventId, -1);
            return new ResponseEntity(JSONUtils.getSuccessJson(activityList),HttpStatus.OK);
        } catch (LiveException | JSONException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/activity/message/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity createMessage(@RequestParam long eventId, @RequestParam long userId, @RequestParam String userName,
                                        @RequestParam String message, HttpServletRequest request) {
        try {
            liveService.addMessageActivity(userId, eventId, userName, message);
            return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
        } catch (LiveException | JSONException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/activity/message/consultant")
    @PreAuthorize("hasRole('ROLE_CONSULTANT')")
    public ResponseEntity createMessage(@RequestParam long eventId, @RequestParam String userName,
                                        @RequestParam String message, HttpServletRequest request) {
        try {
            liveService.addMessageActivity(-1, eventId, userName, message);
            return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
        } catch (LiveException | JSONException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/block")
    @PreAuthorize("hasRole('ROLE_CONSULTANT')")
    public ResponseEntity createMessage(@RequestParam long eventId, @RequestParam long userId,
                                        HttpServletRequest request) throws JSONException {
        liveService.blockUser(userId, eventId);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @PostMapping("/go/live")
    @PreAuthorize("hasRole('ROLE_CONSULTANT')")
    public ResponseEntity goLive(@RequestParam long eventId,
                                        HttpServletRequest request) throws JSONException, IOException, GeneralSecurityException {
        String token = liveService.goLive(eventId);
        return new ResponseEntity(JSONUtils.getSuccessJson("token",token),HttpStatus.OK);
    }

    @PostMapping("/end")
    @PreAuthorize("hasRole('ROLE_CONSULTANT')")
    public ResponseEntity end(@RequestParam long eventId,
                                 HttpServletRequest request) throws JSONException, IOException, GeneralSecurityException {
        liveService.endEvent(eventId);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @PostMapping("/leave")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity leave(@RequestParam long eventId, @RequestParam long userId,
                                        HttpServletRequest request) throws JSONException {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        liveService.leaveEvent(userId, eventId);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @GetMapping("/current/subscribers")
    public ResponseEntity subscriberList(@RequestParam long eventId,
                                HttpServletRequest request) {

        List<LiveEventSubscriber> subscribers  = liveService.getCurrentSubscribers(eventId);
        return new ResponseEntity(subscribers,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getList(@RequestParam Status status, @RequestParam long fromId, @RequestParam int pageSize,
                                  @RequestParam(required = false, defaultValue = "-1") long consultantId){
        List<LiveEvent> liveEvents = liveService.getAllByStatus(status,fromId,consultantId,pageSize);
        return new ResponseEntity(liveEvents,HttpStatus.OK);
    }

    @GetMapping("/astrologer/list")
    public ResponseEntity getList(@RequestParam long consultantId){
        List<LiveEvent> liveEvents = liveService.getAllByAstrologer(consultantId);
        return new ResponseEntity(liveEvents,HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity getEventForUser(@RequestParam long userId){
        List<LiveEvent> liveEvents = liveService.getAllForUser(userId);
        return new ResponseEntity(liveEvents,HttpStatus.OK);
    }

    @GetMapping("/user/history")
    public ResponseEntity getHistory(@RequestParam long userId){
        List<LiveEvent> liveEvents = liveService.getUserHistory(userId);
        return new ResponseEntity(liveEvents,HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam  long eventId){
        LiveEvent liveEvent = liveService.getById(eventId);
        return new ResponseEntity(liveEvent,HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity getById(){
        List<LiveEventProduct> products = liveService.getAllProducts();
        return new ResponseEntity(products,HttpStatus.OK);
    }


    @PostMapping("/products/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity addProduct(@RequestBody LiveEventProduct product) throws JSONException {
        liveService.saveOrUpdate(product);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }

    @PostMapping("/product/buy")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity buyProduct(@RequestParam long userId, @RequestParam long eventId,@RequestParam long productId,
                                     HttpServletRequest request) throws JSONException, LiveException {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(JSONUtils.getFailJson(),HttpStatus.UNAUTHORIZED);
        liveService.buyProduct(userId,eventId,productId);
        return new ResponseEntity(JSONUtils.getSuccessJson(),HttpStatus.OK);
    }
}
