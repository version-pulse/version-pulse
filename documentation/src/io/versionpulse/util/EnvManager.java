package io.versionpulse.util;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Set;

/**
 * 환경 변수 관리 유틸리티 클래스.
 */
public class EnvManager {
    private Properties properties;
    
    /**
     * 기본 생성자.
     * 지정된 경로의 env.properties 파일을 로드하여 Properties 객체에 저장한다.
     */
    public EnvManager() {
        String propertiesPath = "src/io/versionpulse/resource/env.properties";
        this.properties = new Properties();

        try {
            properties.load(new FileInputStream(propertiesPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 주어진 키(key)에 해당하는 값을 반환한다.
     * 
     * @param key 검색할 키
     * @return 해당 키의 값, 키가 없으면 null 반환
     */
    public String getValueByKey(String key) {
        return this.properties.getProperty(key);
    }

    /**
     * 현재 설정된 모든 키 목록을 반환한다.
     *
     * @return 키 목록(Set<String>)
     */
    public Set<String> getKeyList() {
        return this.properties.stringPropertyNames();
    }

    /**
     * 특정 키의 값을 변경한다.
     * 
     * @param key   변경할 키
     * @param value 새로운 값
     * @return 변경 성공 시 true, 실패 시 false
     */
    public boolean editPropertyValueByKey(String key, String value) {
        return this.properties.replace(key, value) != null;
    }

    /**
     * 특정 키를 삭제한다.
     * 
     * @param key 삭제할 키
     * @return 삭제 성공 시 true, 실패 시 false
     */
    public boolean removePropertyByKey(String key) {
        return this.properties.remove(key) != null;
    }

    /**
     * 변경된 properties 데이터를 새로운 파일로 저장한다.
     *
     * @param path 저장할 파일 경로
     */
    public void savePropertiesFile(String path) {
        try {
            this.properties.store(new FileOutputStream(path + "_new"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 특정 키가 존재하는지 확인한다.
     *
     * @param key 확인할 키
     * @return 존재하면 true, 없으면 false
     */
    public boolean isKeyExist(String key) {
        return this.properties.containsKey(key);
    }

    /**
     * 새로운 키-값 쌍을 추가한다.
     * 
     * @param key   추가할 키
     * @param value 추가할 값
     * @return 추가 성공 시 true, 실패 시 false (이미 존재하는 경우 추가되지 않음)
     */
    public boolean addProperty(String key, String value) {
        return this.properties.putIfAbsent(key, value) != null;
    }
}