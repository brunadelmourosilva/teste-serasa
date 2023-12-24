package br.com.brunadelmouro.apiserasa.config;

import br.com.brunadelmouro.apiserasa.consumers.ViaCepConsumer;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignConfig {

  @Value("${srvc.viacep.url}")
  private String viaCepUrl;

  @Bean
  public ViaCepConsumer viaCepConsumerClient() {
    return Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .target(ViaCepConsumer.class, viaCepUrl);
  }
}
