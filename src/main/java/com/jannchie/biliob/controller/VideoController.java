package com.jannchie.biliob.controller;

import com.jannchie.biliob.model.Video;
import com.jannchie.biliob.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试控制器
 *
 * @author jannchie
 */
@RestController
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/video/{aid}")
    public Video getVideoDetails(@PathVariable("aid") Long aid) {
        logger.info("[GET]VideoAid = " + aid);
        return videoService.getVideoDetails(aid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/author/{mid}/video/{aid}")
    public Slice<Video> getAuthorVideo(@PathVariable("aid") Long aid,
                                       @PathVariable("mid") Long mid,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "5") Integer pagesize) {
        logger.info("[GET]VideoAid = " + aid + ",AuthorMid = " + mid);
        return videoService.getAuthorVideo(aid, mid, page, pagesize);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/video")
    public Video postAuthorByMid(@RequestParam("aid") Long aid) {
        logger.info("[POST]VideoAid = " + aid);
        return videoService.postVideoByAid(aid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/video")
    public Page<Video> getVideo(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "20") Integer pageSize,
                                @RequestParam(defaultValue = "-1") Long aid,
                                @RequestParam(defaultValue = "") String text) {
        return videoService.getVideo(aid, text, page, pageSize);
    }
}