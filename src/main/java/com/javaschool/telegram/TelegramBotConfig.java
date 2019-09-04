package com.javaschool.telegram;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class TelegramBotConfig {

    private static String PROXY_HOST = "" /* proxy host */;
    private static Integer PROXY_PORT = 3128 /* proxy port */;

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        HttpHost httpHost = new HttpHost(PROXY_HOST, PROXY_PORT);
        RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(false).build();
        botOptions.setRequestConfig(requestConfig);
        botOptions.setHttpProxy(httpHost);
        return botOptions;
    }

    @Bean
    public TelegramBot exampleBot() {
        return new TelegramBot("", "", defaultBotOptions());
    }
}
