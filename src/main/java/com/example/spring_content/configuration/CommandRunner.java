package com.example.spring_content.configuration;

import com.example.spring_content.entities.SopDocument;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandRunner
{
    @Bean
    CommandLineRunner log()
    {
        return args -> {
            System.out.println("sop-document=" + (new SopDocument()).toString());
        };
    }
}
