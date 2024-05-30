package org.hepi.hepi_sv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hepi.hepi_sv.errorHandler.ErrorHandler;
import org.hepi.hepi_sv.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class ProductService implements RequestService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    DatabaseService databaseService = (DatabaseService) ApplicationContextProvider.getBean("databaseService");

    String type;
    HashMap<String, String> request;

    public ProductService(String type, HashMap<String, String> request) {
        this.type = type;
        this.request = request;
    }

    @Override
    public String execute() {

        List list;
        switch (type)
        {
            case "event":
                list = databaseService.selectEventProduct();
                break;
            case "mine":
                list = databaseService.selectMyProduct(request.get("id"));
                break;
            case "cart":
                list = databaseService.selectCartProduct(request.get("id"));
                break;
            default:
                list = databaseService.selectEventProduct();
                break;
        }

        String productJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            productJson = objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            logger.error("[ERROR] | {}", e);
            throw new ErrorHandler("오류가 발생했습니다");
        }

        logger.info("[상품] '{}' 상품리스트 제공 완료 | {}", type, request.get("id"));
        return productJson;
    }
}
