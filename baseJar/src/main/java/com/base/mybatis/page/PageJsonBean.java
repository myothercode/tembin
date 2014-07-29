package com.base.mybatis.page;

import java.util.List;

/**
 * PageJsonBean 用于ajax返回json列表数据构造
 * @author yanghongquan
 *
 */
public class PageJsonBean {

	//当前页面数
	private int pageNum;
	//每页显示数
	private int pageCount;
	//总数
	private int total;
	//结果集
	private List<?> list;

	public PageJsonBean() {
		pageNum = 1;
		pageCount = 10;
	}

	public PageJsonBean(Page page, List<?> list) {
		this.list = list;
		this.pageNum = page.getCurrentPage();
		this.pageCount = page.getPageSize();
		this.total = (int) page.getTotalCount();
	}

	public Page toPage() {
		return toPage(false);
	}

	public Page toPage(boolean forceTotal) {
		Page page = new Page();
		page.setPageSize(pageCount);
		page.setCurrentPage(pageNum);
		if(forceTotal && total != 0)
			page.setTotalCount(total);
		return page;
	}
	
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	/** 获取总页数 */
	public int getTotalPage() {
		if(pageCount == 0) {
			return 0;
		}
		int temp = total / pageCount;
		return total % pageCount == 0 ? temp + 1 : temp;
	}
}
