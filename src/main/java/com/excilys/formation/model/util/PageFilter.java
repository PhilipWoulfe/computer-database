package com.excilys.formation.model.util;

import java.util.HashMap;
import java.util.Map;

public class PageFilter {
    private int nbPage;
    private int elementsByPage;
    private int pageNum;
    private Map<String, String> conditions = new HashMap<>();

    public void addCondition(String key, String value) {
        conditions.put(key, value);
    }

    public int getNbPage() {
        return nbPage;
    }

    public void setNbPage(int nbPage) {
        this.nbPage = nbPage;
    }

    public int getElementsByPage() {
        return elementsByPage;
    }

    public void setElementsByPage(int elementsByPage) {
        this.elementsByPage = elementsByPage;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}