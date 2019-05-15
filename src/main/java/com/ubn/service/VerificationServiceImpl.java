package com.ubn.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ubn.HomeController;
import com.ubn.model.Payload;
import com.ubn.model.PeaceMassResponse;

@Service(value = "VerificationService")
public class VerificationServiceImpl implements VerificationService {

	String CON_URL = getPropertiesValue("peacemass.url");

	static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@SuppressWarnings("unused")
	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(100000000);
		factory.setConnectTimeout(100000000);
		return factory;
	}

	public void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };
			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (

		NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PeaceMassResponse verified(String code) {
		// Default response
		PeaceMassResponse peace = new PeaceMassResponse("99", false, "Operation Failed!", new ArrayList<>());
		CON_URL = CON_URL + code;
		LOGGER.info("******GET URL******" + CON_URL);
		try {
			HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

			// HttpEntity<String>: To get result as String.
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			RestTemplate restTemplate = new RestTemplate();

			// Send request with GET method, and Headers.
			ResponseEntity<String> responseFromServer = restTemplate.exchange(CON_URL.trim(), HttpMethod.GET, entity,
					String.class);

			String result = responseFromServer.getBody().toString();

			if (result != null) {
				LOGGER.info("peacemass direct response  >> " + result.toString());
				if (result.startsWith("{")) {
					JSONObject jsonResult = new JSONObject(result);
					if (jsonResult.has("payload")) {
						List<Payload> p = new ArrayList<>();
						JSONArray jsonArray = jsonResult.getJSONArray("payload");
						if (jsonArray != null & jsonArray.length() > 0) {
							for (int i = 0; i < jsonArray.length(); i++) {
								Payload m = new Payload();
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								JSONArray seat = jsonObject.optJSONArray("seat_positions");
								int[] seats = new int[seat.length()];
								for (int j = 0; j < seat.length(); j++) {
									seats[j] = seat.getInt(j);
								}
								m.setSeat_positions(seats);
								m.setPayment_status(jsonObject.optString("payment_status"));
								m.setTransaction_code(jsonObject.optString("transaction_code"));
								m.setPhone(jsonObject.optString("phone"));
								m.setDescription(jsonObject.optString("description"));
								m.setBooking_type(jsonObject.optString("booking_type"));
								m.setCustomer_id(jsonObject.optString("booking_type"));
								m.setPmt_schedule_id(jsonObject.optString("pmt_schedule_id"));
								m.setSeat_quantity(jsonObject.optInt("seat_quantity"));
								m.setFare(jsonObject.optInt("fare"));
								m.setPayment_method(jsonObject.optString("payment_method"));
								m.setPayment_gateway(jsonObject.optString("payment_gateway"));
								m.setCreated_at(jsonObject.optString("created_at"));
								m.setUpdated_at(jsonObject.optString("updated_at"));
								m.setId(jsonObject.optString("id"));

								p.add(m);
							}
						}
						peace.setPayload(p);
						peace.setResponseCode(jsonArray != null & jsonArray.length() > 0 ? "00" : "99");
						peace.setSuccess(
								jsonArray != null & jsonArray.length() > 0 ? jsonResult.optBoolean("success") : false);
						peace.setMessage(jsonArray != null & jsonArray.length() > 0 ? jsonResult.optString("message")
								: "Operation Failed!");
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error encountered >> " + e.getMessage());
		}
		return peace;
	}

	public static String getPropertiesValue(String key) {
		Properties prop = new Properties();
		InputStream input = null;
		String retValue = "";
		// String config_path = System.getenv("WSCONFIG_HOME") + File.separator +
		// "wsconfig.properties";
		String WSCONFIG_HOME = "C:/deploy";

		if (!new File(WSCONFIG_HOME).isDirectory()) {
			WSCONFIG_HOME = "/u01/app/oracle/config/properties";
		}
		String config_path = WSCONFIG_HOME + File.separator + "peacemass.properties";
		System.out.println("Config found on=====" + config_path);
		try {
			input = new FileInputStream(config_path);
			prop.load(input);
			retValue = prop.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
					prop.clear();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return retValue;
	}

//	public static void main(String[] args) {
//		VerificationServiceImpl s = new VerificationServiceImpl();
//		PeaceMassResponse p = s.verified("ABKQ1234FG89K");
//		JSONObject j = new JSONObject(p);
//		System.out.println(j);
//	}
}
