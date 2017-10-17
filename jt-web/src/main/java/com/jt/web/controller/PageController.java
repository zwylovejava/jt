package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class PageController {
	@RequestMapping("/index")
	public String goIndex(){
		return "index";
	}
}
