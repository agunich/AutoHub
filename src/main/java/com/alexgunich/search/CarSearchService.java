//package com.alexgunich.search;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CarSearchService {
//
//    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
//
//    @Autowired
//    public CarSearchService(ElasticsearchRestTemplate elasticsearchRestTemplate) {
//        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
//    }
//
//    /**
//     * Выполняет поиск автомобилей с фильтрацией.
//     *
//     * @param brand    марка автомобиля (необязательно).
//     * @param model    модель автомобиля (необязательно).
//     * @param minYear  минимальный год выпуска (необязательно).
//     * @param maxYear  максимальный год выпуска (необязательно).
//     * @param minPrice минимальная цена (необязательно).
//     * @param maxPrice максимальная цена (необязательно).
//     * @param minMileage минимальный пробег (необязательно).
//     * @param maxMileage максимальный пробег (необязательно).
//     * @return список найденных автомобилей.
//     */
//    public List<CarElasticsearch> searchCars(String brand, String model, Integer minYear, Integer maxYear, Double minPrice, Double maxPrice, Double minMileage, Double maxMileage) {
//        // Создание динамического запроса с фильтрами
//        Query query = Query.query(new org.elasticsearch.index.query.BoolQueryBuilder()
//                .must(brand != null && !brand.isEmpty() ? org.elasticsearch.index.query.QueryBuilders.matchQuery("brand", brand) : null)
//                .must(model != null && !model.isEmpty() ? org.elasticsearch.index.query.QueryBuilders.matchQuery("model", model) : null)
//                .filter(minYear != null || maxYear != null ? org.elasticsearch.index.query.QueryBuilders.rangeQuery("year")
//                        .gte(minYear != null ? minYear : Integer.MIN_VALUE)
//                        .lte(maxYear != null ? maxYear : Integer.MAX_VALUE) : null)
//                .filter(minPrice != null || maxPrice != null ? org.elasticsearch.index.query.QueryBuilders.rangeQuery("price")
//                        .gte(minPrice != null ? minPrice : Double.MIN_VALUE)
//                        .lte(maxPrice != null ? maxPrice : Double.MAX_VALUE) : null)
//                .filter(minMileage != null || maxMileage != null ? org.elasticsearch.index.query.QueryBuilders.rangeQuery("mileage")
//                        .gte(minMileage != null ? minMileage : Double.MIN_VALUE)
//                        .lte(maxMileage != null ? maxMileage : Double.MAX_VALUE) : null)
//        );
//
//        // Выполнение поиска
//        return elasticsearchRestTemplate.search(query, CarElasticsearch.class)
//                .stream()
//                .map(hit -> hit.getContent())
//                .collect(Collectors.toList());
//    }
//}