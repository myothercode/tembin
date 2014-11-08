/** Created by flym at 13-3-12 */
package com.base.mybatis.page;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;

/**
 * 分页组件类
 *
 * @author flym
 */
public class Page extends RowBounds implements Serializable {
	public static final Page NO_PAGE = new Page(NO_ROW_OFFSET, NO_ROW_LIMIT) {
		@Override
		public void setPageSize(int pageSize) {
			throw new UnsupportedOperationException("不支持此操作");
		}

		@Override
		public void setCurrentPage(int currentPage) {
			throw new UnsupportedOperationException("不支持此操作");
		}

		@Override
		public void setTotalCount(long totalCount) {
			throw new UnsupportedOperationException("不支持此操作");
		}
	};

	public static final Page UNIQUE_PAGE = new Page(1, 1) {{
		setTotalCount(1);
	}};

	/** 当前界面 */
	private int currentPage = 1;
	/** 每页显示的记录数 */
	private int pageSize;
	/** 总记录数 */
	private long totalCount;

	public Page() {
	}

	public Page(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if(currentPage <= 1)
			return;
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize <= 0)
			return;
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/** 得到查询记录的起始点,即前一页记录点的最后一条 */
	public int getStartIndex() {
		return (currentPage - 1) * pageSize;
	}

	/** 总页数 */
	public long getTotalPage() {
		long i = totalCount / (long) pageSize;
		long j = totalCount % (long) pageSize;
		return i + (j == 0 ? 0 : 1);
	}

	/** 是否有上一页 */
	public boolean isHasPreviousPage() {
		return currentPage > 1;
	}

	/** 是否有下一页 */
	public boolean isHasNextPage() {
		return currentPage < getTotalPage();
	}

	/** 重写父类的相应方法,以提供自实现取得起始点 */
	@Override
	public int getOffset() {
        return getStartIndex();
	}

	/** 重写父类的相应方法,以提供自实现限制 */
	@Override
	public int getLimit() {
		return getPageSize();
	}

    /**获取一个单页状态下的Page*/
    public static Page newAOnePage(){
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(10000);
        page.setTotalCount(1);
        return page;
    }
}
