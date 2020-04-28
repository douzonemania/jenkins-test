package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;



@Controller
@RequestMapping("/guestbook")
public class GuestbookController {	
	
	@Autowired
	private GuestBookService guestbookService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model) {
		
		guestbookService.guestBookList(model);
		return "guestbook/list";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String join(GuestBookVo vo){		
		
		guestbookService.guestBookInsert(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no,Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		
		boolean result = guestbookService.guestBookDelete(no, password);		
		if(result==true)
			return "redirect:/guestbook";
		else
			return "/guestbook/delete";
	
	}

	
}