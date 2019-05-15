package com.ubn;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ubn.model.PeaceMassResponse;
import com.ubn.service.VerificationService;

@RestController
public class HomeController {

	static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	VerificationService service;

	@GetMapping("/echo")
	public String echo() {
		return "echo";
	}

	@GetMapping("/verify_transaction_code/{ver_code}")
	public @ResponseBody ResponseEntity<PeaceMassResponse> verifyWithCode(HttpServletRequest request,
			@PathVariable("ver_code") String code, Principal principal) {
		System.out.println("Code is >>>> " + code);
		SaveInfo(request, "/verify_transaction_code");
		PeaceMassResponse p = service.verified(code);
		logAudit(request, code, p, principal.getName());
		return new ResponseEntity<PeaceMassResponse>(p, HttpStatus.OK);
	}

	private void SaveInfo(HttpServletRequest req, String methodName) {
		LOGGER.info("********** " + methodName + " in SaveInfo method *********");
		String remoteAddress = "0.0.0.0";
		if (req != null) {
			remoteAddress = req.getHeader("X-FOWARDED-FOR") != null ? req.getHeader("X-FOWARDED-FOR")
					: req.getRemoteAddr();
		}
		LOGGER.info(methodName + " accessed by " + remoteAddress);
	}

	private void logAudit(HttpServletRequest req, String code, Object resp, String uname) {
		String remoteAddress = "0.0.0.0";
		String request = code;
		JSONObject jsonResp = new JSONObject(resp);
		if (req != null) {
			remoteAddress = req.getHeader("X-FOWARDED-FOR") != null ? req.getHeader("X-FOWARDED-FOR")
					: req.getRemoteAddr();
		}
		LOGGER.info("\nRequestString("+uname + "-" + remoteAddress + ")>>> " + request + "\nResponseJson("
				+ uname + "-" + remoteAddress + ")>>> " + jsonResp.toString());
	}
}
