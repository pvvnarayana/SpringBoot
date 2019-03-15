package com.tm;

import brave.sampler.Sampler;
import com.tm.MicroService2Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroService2Application {
	public static void main(String[] args) {
		SpringApplication.run(MicroService2Application.class, args);
	}

	@Value("${message}")
	private String message;

	@GetMapping({ "ms2" })
	public String getMessage() {
		RestTemplate restTemplate = new RestTemplate();
		String serviceResponse = (String) restTemplate.getForObject("http://domain-b-service:8124/domainB",
				String.class, new Object[0]);
		return this.message + "<br/>" + serviceResponse;
	}

	@Bean
	public Sampler alwaysSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
