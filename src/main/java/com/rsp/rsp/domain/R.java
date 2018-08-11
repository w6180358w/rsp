package com.rsp.rsp.domain;

import java.util.List;

public class R {
    /**数据*/
    private Object[] content;
    /**总记录数1*/
    private int iTotalRecords;
    /** 总记录数2 */
    private int iTotalDisplayRecords;
    /** 不知道啥用 */
    private Integer draw;

    private String error;
    
    private List<?> data;

    public R(Object[] content, int iTotalRecords, int iTotalDisplayRecords, Integer draw, String error) {
        this.content = content;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.draw = draw;
        this.error = error;
    }

    public R(List<?> data, int iTotalRecords, int iTotalDisplayRecords, Integer draw, String error) {
        this.data = data;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.draw = draw;
        this.error = error;
    }
    
    public R(List<?> data) {
        this.data = data;
    }
    
    public Object[] getContent() {
        return content;
    }

    public void setContent(Object[] content) {
        this.content = content;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
