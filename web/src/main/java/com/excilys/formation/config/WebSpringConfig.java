package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.excilys.formation.controller, com.excilys.formation.config")
public class WebSpringConfig {
}