package com.jack.cloud;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoliceController {

	@RequestMapping(value = "/call/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Police call(@PathVariable Integer id) {
		
		Police police = new Police();
		police.setId(id);
		police.setName("jack");
		return police;
	}
	
	// 是否可以访问DB
	public static boolean canVisitDB = true;
	
	@GetMapping("/db/{can}")
	@ResponseBody
	public String setDB(@PathVariable boolean can) {
		canVisitDB = can;
		return canVisitDB + "";
	}
	
}
