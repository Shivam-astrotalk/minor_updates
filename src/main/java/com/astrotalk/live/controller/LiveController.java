package com.astrotalk.live.controller;

import com.astrotalk.live.model.*;
import com.astrotalk.live.service.LiveService;
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

public class LiveController {

    @Autowired
    LiveService liveService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CONSULTANT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity fillIntakeForm(@RequestBody LiveEvent liveEvent, HttpServletRequest request) {
        liveService.create(liveEvent);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/update/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CONSULTANT')")
    public ResponseEntity approve(@RequestParam long eventId, @RequestParam Status status, HttpServletRequest request) {
        liveService.updateStatus(eventId, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/update/link")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CONSULTANT')")
    public ResponseEntity approve(@RequestParam long eventId, @RequestParam String link,@RequestParam boolean goLive, HttpServletRequest request) {
        liveService.updateLink(eventId, link,goLive);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/user/subscribe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> subscribe(@RequestParam long eventId, @RequestParam String username,
                                            @RequestParam long userId, HttpServletRequest request) {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        try {
            liveService.joinEvent(userId, eventId, username);
            return new ResponseEntity(HttpStatus.OK);
        } catch (LiveException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/activity/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity activity(@RequestParam long eventId, @RequestParam long fromId, @RequestParam long userId, HttpServletRequest request) {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        try {
            List<LiveEventActivity> activityList = liveService.getActivity(fromId, eventId, userId);
            return new ResponseEntity(HttpStatus.OK);
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
            return new ResponseEntity(HttpStatus.OK);
        } catch (LiveException e) {
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
            return new ResponseEntity(HttpStatus.OK);
        } catch (LiveException e) {
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
            return new ResponseEntity(HttpStatus.OK);
        } catch (LiveException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/block")
    @PreAuthorize("hasRole('ROLE_CONSULTANT')")
    public ResponseEntity createMessage(@RequestParam long eventId, @RequestParam long userId,
                                        HttpServletRequest request) {
        liveService.blockUser(userId, eventId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/leave")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity leave(@RequestParam long eventId, @RequestParam long userId,
                                        HttpServletRequest request) {
        if (userId != Long.parseLong(request.getHeader("id")))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        liveService.leaveEvent(userId, eventId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/current/subscribers")
    public ResponseEntity subscriberList(@RequestParam long eventId,
                                HttpServletRequest request) {

        List<LiveEventSubscriber> subscribers  = liveService.getCurrentSubscribers(eventId);
        return new ResponseEntity(subscribers,HttpStatus.OK);
    }

    @GetMapping("/redirect/go/live")
    public ResponseEntity goLive(@RequestParam String authCode, @RequestParam String state) throws IOException, GeneralSecurityException {
        String [] tokens = URLDecoder.decode(state).split(",");
        long eventId = Long.parseLong(tokens[0]);
        LiveEvent liveEvent = liveService.goLive(eventId,authCode);
        return new ResponseEntity(liveEvent,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getList(@RequestParam Status status, @RequestParam long fromId, @RequestParam int pageSize,
                                  @RequestParam(required = false, defaultValue = "-1") long consultantId){
        List<LiveEvent> liveEvents = liveService.getAllByStatus(status,fromId,consultantId,pageSize);
        return new ResponseEntity(liveEvents,HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam  long eventId){
        LiveEvent liveEvent = liveService.getById(eventId);
        return new ResponseEntity(liveEvent,HttpStatus.OK);
    }

}
