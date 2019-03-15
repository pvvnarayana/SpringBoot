package com.tm;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroService1Application {
	@Value("${message}")
	private String message;

	public static void main(String[] args) {
		SpringApplication.run(MicroService1Application.class, args);
	}

	@Autowired
	RestTemplate restTemplate;

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@GetMapping({ "ms1" })
	public String getMessage() {
		String serviceResponse = (String) this.restTemplate.getForObject("http://domain-a-service/domainA",
				String.class, new Object[0]);
		return String.valueOf(this.message) + "<br/>" + serviceResponse;
	}

	@Bean
	public Sampler alwaysSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
