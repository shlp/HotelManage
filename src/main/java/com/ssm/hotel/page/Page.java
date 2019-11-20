package com.ssm.hotel.page;

import org.springframework.stereotype.Component;

/**
 * 分页基本信息
 * @author llq
 *
 */
@Component
public class Page {
	private int page = 1;//当前页码

	private int rows;//每页显示数量

	private int offset;//对应数据库中的偏移量

	private int totalPage;//总共页数

	private int totalRows;//总共记录数

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getOffset() {
		this.offset = (page - 1) * rows;
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotalPage(){return totalPage;}
	public void setTotalPage(int totalRows){this.totalPage=totalRows/rows;}
	public int getTotalRows(){return totalRows;}
	public void setTotalRows(int totalRows){this.totalRows=totalRows;}
}
