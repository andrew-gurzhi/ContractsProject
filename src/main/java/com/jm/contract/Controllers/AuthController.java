package com.jm.contract.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController  {
	@RequestMapping(value ="/",method = RequestMethod.GET)
	public String login() {
		return "index";
	}

	@RequestMapping(value ="/success",method = RequestMethod.GET)
	public String contractpage() {
		return "success";
	}

}

