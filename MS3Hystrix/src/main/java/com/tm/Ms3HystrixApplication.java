package com.tm;

import brave.sampler.Sampler;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@RestController
public class Ms3HystrixApplication {
	@Value("${message}")
	private String message;
	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Ms3HystrixApplication.class, args);
	}

	@Bean
	public Sampler alwaysSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@GetMapping({ "ms3" })
	@HystrixCommand(fallbackMethod = "getMessage2", defaultFallback = "errorMessage")
	public String getMessage1() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
		HttpEntity entity = new HttpEntity(headers);
		String serviceResponse = (String) this.restTemplate
				.exchange("http://domain-a-service/domainA", HttpMethod.GET, entity, String.class, new Object[0])
				.getBody();
		return String.valueOf(this.message) + "<br/>" + serviceResponse;
	}

	@HystrixCommand(defaultFallback = "errorMessage")
	public String getMessage2() {
		String serviceResponse = (String) this.restTemplate.getForObject("http://domain-b-service/domainB",
				String.class, new Object[0]);
		return String.valueOf(this.message) + "<br/>" + serviceResponse;
	}

	public String errorMessage() {
		return "<h1 color='red'> Service is temporarily unavailable</h1>";
	}
}
