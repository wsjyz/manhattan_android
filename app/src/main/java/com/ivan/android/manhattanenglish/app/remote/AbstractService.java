package com.ivan.android.manhattanenglish.app.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-3-14
 * Time: AM9:59
 */
public class AbstractService {

    public final static Type STRING_MAP = new TypeReference<Map<String, String>>() {
    }.getType();

    private static RestTemplate restTemplate = new RestTemplate(true);

    static {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    protected <T> T getForObject(Class<T> clazz, String url, Object... params) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
        return convertToObject(clazz, responseEntity);
    }

    protected <T> T getForObject(Type type, String url, Object... params) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
        return convertToObject(type, responseEntity);
    }

    protected <T> List<T> getForList(Class<T> clazz, String url, Object... params) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
        return convertToList(clazz, responseEntity);
    }

    private void checkResponse(ResponseEntity<String> entity) {
        if (entity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(entity.getStatusCode().getReasonPhrase());
        }
        HttpHeaders headers = entity.getHeaders();
        String exception = headers.getFirst("midhException");
        if (exception != null && !"".equals(exception)) {
            try {
                exception = URLDecoder.decode(exception, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(exception);
        }
    }

    private <T> T convertToObject(Class<T> clazz, ResponseEntity<String> entity) {
        checkResponse(entity);
        String body = entity.getBody();
        return JSON.parseObject(body, clazz);
    }

    private <T> T convertToObject(Type type, ResponseEntity<String> entity) {
        checkResponse(entity);
        String body = entity.getBody();
        return JSON.parseObject(body, type);
    }

    private <T> List<T> convertToList(Class<T> clazz, ResponseEntity<String> entity) {
        checkResponse(entity);
        String body = entity.getBody();
        return JSON.parseArray(body, clazz);
    }

    protected void post(String url, Map<String, String> uriVariables) {
        ResponseEntity<String> responseEntity = getStringResponseEntity(url, uriVariables);
        checkResponse(responseEntity);
    }

    protected <T> T postForObject(Class<T> clazz, String url, Map<String, String> uriVariables) {
        ResponseEntity<String> responseEntity = getStringResponseEntity(url, uriVariables);
        return convertToObject(clazz, responseEntity);
    }

    protected <T> T postForObject(Type type, String url, Map<String, String> uriVariables) {
        ResponseEntity<String> responseEntity = getStringResponseEntity(url, uriVariables);
        return convertToObject(type, responseEntity);
    }

    protected Map<String, String> postForStringMap(String url, Map<String, String> uriVariables) {
        ResponseEntity<String> responseEntity = getStringResponseEntity(url, uriVariables);
        return convertToObject(STRING_MAP, responseEntity);
    }


    protected <T> List<T> postForList(Class<T> clazz, String url, Map<String, String> uriVariables) {
        ResponseEntity<String> responseEntity = getStringResponseEntity(url, uriVariables);
        return convertToList(clazz, responseEntity);
    }


    private ResponseEntity<String> getStringResponseEntity(String url, Map<String, String> uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Connection", "Close");//avoid EOFException
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        if (MapUtils.isNotEmpty(uriVariables)) {
            for (String key : uriVariables.keySet()) {
                body.set(key, uriVariables.get(key));
            }
        }
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        return restTemplate.postForEntity(url, request, String.class);
    }



}
