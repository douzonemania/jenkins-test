package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookRepository guestbookRepository;
	
	public void guestBookList(Model model) {
		List<GuestBookVo> list = guestbookRepository.findAll();
		model.addAttribute("list", list);		
	}

	public void guestBookInsert(GuestBookVo vo) {
		guestbookRepository.insert(vo);		
	}

	public boolean guestBookDelete(Long no, String password) {
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);
		boolean result = guestbookRepository.delete(vo);
		return result;		
	}


	
	
}