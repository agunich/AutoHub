package com.alexgunich.config;//package com.alexgunich.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RequestConfigCallback;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchRestClient;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean
//    public RestClient restClient() {
//        // Создаем RestClient для Elasticsearch
//        return RestClient.builder(
//                new HttpHost("localhost", 9200, "http") // Настройте свой хост и порт
//        ).build();
//    }
//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestClient restClient) {
//        // Создаем и возвращаем ElasticsearchRestTemplate
//        return new ElasticsearchRestTemplate(new ElasticsearchRestClient(restClient));
//    }
//}