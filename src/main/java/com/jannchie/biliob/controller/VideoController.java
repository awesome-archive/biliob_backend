package com.jannchie.biliob.controller;

import com.jannchie.biliob.exception.UserAlreadyFavoriteVideoException;
import com.jannchie.biliob.exception.VideoAlreadyFocusedException;
import com.jannchie.biliob.model.Video;
import com.jannchie.biliob.service.VideoService;
import com.jannchie.biliob.utils.Message;
import com.jannchie.biliob.utils.MySlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jannchie
 */
@RestController
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/{aid}")
    public Video getVideoDetails(
            @PathVariable("aid") Long aid, @RequestParam(defaultValue = "1") Integer type) {
        return videoService.getVideoDetails(aid, type);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/author/{mid}/video/{aid}")
    public MySlice<Video> getAuthorVideo(
            @PathVariable("aid") Long aid,
            @PathVariable("mid") Long mid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer pagesize) {
        return videoService.getAuthorOtherVideo(aid, mid, page, pagesize);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/author/{mid}/video")
    public MySlice<Video> getAuthorTopVideo(
            @PathVariable("mid") Long mid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer pagesize,
            @RequestParam(defaultValue = "0") Integer sort) {
        return videoService.getAuthorTopVideo(mid, page, pagesize, sort);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/video")
    public ResponseEntity<Message> postVideoByAid(@RequestBody @Valid Long aid)
            throws VideoAlreadyFocusedException, UserAlreadyFavoriteVideoException {
        return videoService.postVideoByAid(aid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video")
    public MySlice<Video> getVideo(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "0") Integer days,
            @RequestParam(defaultValue = "-1") Long aid,
            @RequestParam(defaultValue = "") String text,
            @RequestParam(defaultValue = "0") Integer sort) {
        return videoService.getVideo(aid, text, page, pageSize, sort, days);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/ads")
    public Video getMyVideo() {
        return videoService.getMyVideo();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/online")
    public ResponseEntity listOnlineVideo() {
        return videoService.listOnlineVideo();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/online/top")
    public Map getTopOnlineVideo() {
        return videoService.getTopOnlineVideo();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/number")
    public Map<String, Long> getNumberOfVideo() {
        Map<String, Long> result = new HashMap<>(1);
        result.put("videoNumber", videoService.getNumberOfVideo());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/rank-table")
    public Map getVideoRankTable() {
        return videoService.getRankTable();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/video/popular-keyword")
    public List getPopularKeyword() {
        return videoService.getPopularKeyword();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/video/prefer-keyword")
    public Map getPreferKeyword(@RequestBody Map<String, Integer> data) {
        return videoService.getPreferKeyword(data);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/video/recommend")
    public List getRecommendVideo(
            @RequestBody Map<String, Integer> data,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
        return videoService.getRecommendVideoByTag(data, page, pagesize);
    }
}
