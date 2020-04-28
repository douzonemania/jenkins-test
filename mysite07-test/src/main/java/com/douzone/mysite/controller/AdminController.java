package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = adminService.List();
		model.addAttribute("siteVo", vo);
		
		return "admin/main";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}	
	@RequestMapping(value = "main/update", method = RequestMethod.POST)
	public String modify(
			@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "welcomeMessage", required =true, defaultValue = "") String welcomeMessage,
			@RequestParam(value = "file1") MultipartFile multipartFile,
			@RequestParam(value = "description", required = true, defaultValue = "") String description,
			Model model) {
		SiteVo vo = new SiteVo();
		vo.setWelcomeMessage(welcomeMessage);
		vo.setTitle(title);
		vo.setDescription(description);
		String url = fileUploadService.restore(multipartFile);
		vo.setProfile(url);
		adminService.update(vo);
		model.addAttribute("siteVo",vo);
		return "admin/main";
	}
	
//	@RequestMapping(value="main/update", method = RequestMethod.POST)
//	public String update(
//			@PathVariable("siteVo.title") String title,
//			@PathVariable("siteVo.welcomeMessage") String welcomeMessage,
//			@PathVariable("siteVo.description") String description,
//			Model model, SiteVo vo){
//		vo.setTitle(title);
//		vo.setWelcomeMessage(welcomeMessage);
//		vo.setDescription(description);
//		System.out.println(vo.getTitle());
//		System.out.println(vo.getWelcomeMessage());
//		System.out.println(vo.getDescription());
//		System.out.println(vo+"zz");
//		
//		adminService.update(vo);
//		
//		return "admin/main";
//	}
}
