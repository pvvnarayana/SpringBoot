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
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableHystrixDashboard
@RibbonClient(name = "domain-a-service")
@RestController
public class Ms4RibbonApplication {
	@Value("${message}")
	private String message;

	public static void main(String[] args) {
		SpringApplication.run(Ms4RibbonApplication.class, args);
	}

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public Sampler alwaysSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@GetMapping({ "ms4" })
	@HystrixCommand(defaultFallback = "errorMessage")
	public String getMessage1() {
		String serviceResponse = (String) this.restTemplate.getForObject("http://domain-a-service/domainA",
				String.class, new Object[0]);
		return String.valueOf(this.message) + "<br/>" + serviceResponse;
	}

	public String errorMessage() {
		return "<h1 style='text-color:red'> Service is temporarily unavailable</h1>";
	}
}
