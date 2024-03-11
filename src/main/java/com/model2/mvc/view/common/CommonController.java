package com.model2.mvc.view.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class CommonController {

	@Value("#{commonValues['pageUnit']}")
	protected int PAGE_UNIT;
	
	@Value("#{commonValues['pageSize']}")
	protected int PAGE_SIZE;
}
