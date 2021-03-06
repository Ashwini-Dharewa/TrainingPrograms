package com.microservice.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@RestController
public class DbClient 
{
    /*@Autowired
    private DiscoveryClient discoveryClient;
    
    @RequestMapping("/home")
    public String getUser()
    {
        List<ServiceInstance> instances=discoveryClient.getInstances("DB-Producer");
        ServiceInstance serviceInstance=(ServiceInstance) instances.get(0);
        String baseUrl=serviceInstance.getUri().toString()+"/";
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response=null;
        try
        {
            response=restTemplate.exchange(baseUrl,HttpMethod.GET, getHeaders(), String.class);                                                
        }
        catch (Exception ex)
        {
        	System.out.println(response.getBody());            
        }
        
        return  response.getBody();
    }
    public HttpEntity<?> getHeaders()
    {
        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);              
    }*/
    
	@Autowired
	private LoadBalancerClient loadBalancer;
	@RequestMapping(value="/get",method=RequestMethod.GET)
    public String getEmployee()
	{
		ServiceInstance si=loadBalancer.choose("DB-Producer");
		System.out.println(si.getUri());
		String baseUrl=si.getUri().toString()+"/";
		
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<String>response=null;
		try
		{
			response=restTemplate.exchange(baseUrl,HttpMethod.GET,getHeaders(),String.class);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return response.getBody();
	}
	private static HttpEntity<?>getHeaders() throws IOException
	{
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept",MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}

