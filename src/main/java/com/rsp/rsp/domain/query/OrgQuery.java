package com.rsp.rsp.domain.query;

/**
 * 机构查询条件
 * @author sjb
 */
public class OrgQuery {
    private String name;
    private String sSearch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }
}
