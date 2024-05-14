package com.zzg.controller;


import com.zzg.config.QuartzManager;
import com.zzg.utils.SpringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/task")
public class QuartzController {
	
	@GetMapping("/list")
	public String listJob(){
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		return quartzManager.listJobs();
	}
	
	@GetMapping("/init")
	public String initJob(@RequestParam String jobKey) {
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		return quartzManager.initJob(jobKey);
	}
	
	@GetMapping("/pause")
	public String pauseJob(@RequestParam String jobKey) {
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		return quartzManager.pauseJob(jobKey);
	}

	@GetMapping("/resume")
	public void resumeJob(@RequestParam String jobKey) {
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		quartzManager.resumeJob(jobKey);
	}

	@GetMapping("/run")
	public void runJob(@RequestParam String jobKey) {
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		quartzManager.runJob(jobKey);
	}

	@GetMapping("/ir")
	public void initAndRunJob(@RequestParam String jobKey) {
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		quartzManager.initJob(jobKey);
		quartzManager.runJob(jobKey);
	}

    @GetMapping("/initGroup")
    public void initGroup(@RequestParam String jobGroup) {
        QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
        quartzManager.initJobsByGroup(jobGroup);
    }

	@GetMapping("/runGroup")
	public void runGroup(@RequestParam String jobGroup) {
		QuartzManager quartzManager = SpringUtils.getBean(QuartzManager.class);
		quartzManager.runJobsByGroup(jobGroup);
	}
	
}
