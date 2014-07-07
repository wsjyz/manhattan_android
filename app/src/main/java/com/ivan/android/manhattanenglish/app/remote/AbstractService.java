package com.ivan.android.manhattanenglish.app.remote;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import org.apache.commons.collections.MapUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public final static String HOST = "http://203.195.131.34:8080/mhd";
//    public final static String HOST = "http://192.168.0.104:8090/mhd";


    public final static Type STRING_MAP = new TypeReference<Map<String, String>>() {
    }.getType();

    protected static RestTemplate restTemplate = new RestTemplate(true);

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
        String exception = headers.getFirst("ErrorMsg");
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
        if (clazz.equals(String.class)) {
            body = "\"" + body + "\"";
        }
        return JSON.parseObject(body, clazz);
    }

    private <T> T convertToObject(Type type, ResponseEntity<String> entity) {
        checkResponse(entity);
        String body = entity.getBody();
        if (type.equals(String.class)) {
            body = "\"" + body + "\"";
        }
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
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        if (!TextUtils.isEmpty(UserCache.getUserId())) {
            body.set("userId", UserCache.getUserId());
        }
        if (MapUtils.isNotEmpty(uriVariables)) {
            for (String key : uriVariables.keySet()) {
                body.set(key, uriVariables.get(key));
            }
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        Log.i("RestTemplate", "request url: " + url
                + "\n request: " + request
                + "\n userId: " + UserCache.getUserId());
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        Log.i("RestTemplate", "response:" + response.getBody()
                + "\n responseStatus :" + response.getStatusCode());
        return response;
    }

    protected String multiPartPost(String url, Resource fileResource, Map<String, String> variables) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Connection", "Close");//avoid EOFException
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        if (fileResource != null) {
            body.set("file", fileResource);
        }
        if (!TextUtils.isEmpty(UserCache.getUserId())) {
            body.set("userId", UserCache.getUserId());
        }
        if (MapUtils.isNotEmpty(variables)) {
            for (String key : variables.keySet()) {
                body.set(key, variables.get(key));
            }
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(body, headers);
        Log.i("RestTemplate", "request url: " + url
                + "\n request: " + request
                + "\n file: " + (fileResource == null ? "not specified." : fileResource.getFilename()));
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        Log.i("RestTemplate", "statusCode:" + response.getStatusCode()
                + "\n response: " + response.getBody());

        return response.getBody();
    }

    protected String getUrl(String action) {
        return HOST + action;
    }

}
