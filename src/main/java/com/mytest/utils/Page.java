package com.mytest.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对像
 * 
 * @author zwt
 *
 */
@SuppressWarnings("serial")
public class Page implements Serializable {

    public static Page build(String pageNo, String pageSize) {
        try {
            Page page = new Page(Integer.parseInt(pageNo),
                    Integer.parseInt(pageSize));
            if (page.getBeginIndex() < 0)
                return null;

            return page;
        } catch (Exception e) {
            return null;
        }
    }

    private int pageNo = 1; // 当前页数
    private int pageSize = 20; // 每页条数
    private long total; // 总条数
    private int pagecount; // 总页数

    private int showLine = 5;

    private int showPageHead;

    private int showPageEnd;
    
    private List<?> list;

    public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 获取当前页数
    public int getPageNo() {
        return pageNo;
    }

    // 获取每页条数
    public int getPageSize() {
        return pageSize;
    }

    // 总条数
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        updatePageCount();
    }

    // 总页数
    public int getPagecount() {
        return pagecount;
    }

    // 获取本页起始索引
    public int getBeginIndex() {
        return (pageNo - 1) * pageSize;
    }

    private void updatePageCount() {
        pagecount = (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public String toString() {
        return "[PAGE=" + this.pageNo + ",PAGESIZE=" + this.pageSize + "]";
    }

    public static void main(String[] args) {
        Page p = new Page(5, 9);
        p.setTotal(47);
    }

    /** 显示的页面开头数 **/
    public int getShowPageHead() {
        if (this.getPageNo() <= this.getShowLine()) {
            if (getPageNo() - this.getShowLine() / 2 >= 1) {
                int head = getPageNo() - this.getShowLine() / 2;
                if (head + this.getShowLine() > this.getPagecount()) {
                    return this.getPagecount() - this.getShowLine() >= 1 ? this
                            .getPagecount() - this.getShowLine() : 1;
                } else
                    return head;
            } else {
                return 1;
            }
        } else {
            if ((this.getPageNo() + this.getShowLine() / 2) >= this
                    .getPagecount()) {
                int head = (this.getPageNo() - this.getShowLine()) >= 1 ? (this
                        .getPageNo() - this.getShowLine()) : this.getPageNo();
                if (head + getShowLine() < this.getPagecount()) {
                    return this.getPagecount() - getShowLine();
                } else {
                    return head;
                }
            }
            if (this.getPageNo() - (this.getShowLine() / 2) < this
                    .getShowLine()) {
                return this.getPageNo() - this.getShowLine() / 2;
            } else {

                int head = this.getPageNo() - this.getShowLine() / 2;
                if (this.getPagecount() - head < getShowLine()) {
                    return this.getPagecount() - getShowLine();
                } else {
                    return head;
                }

            }

        }
    }

    public void setShowPageHead(int showPageHead) {
        this.showPageHead = showPageHead;
    }

    /** 显示的页面结尾数 **/
    public int getShowPageEnd() {
        int head = this.getShowPageHead();
        int end = head + this.getShowLine();
        if (end >= this.getPagecount()) {
            return this.getPagecount();
        } else {
            return end;
        }
    }

    public void setShowPageEnd(int showPageEnd) {
        this.showPageEnd = showPageEnd;
    }

    public int getShowLine() {
        return showLine;
    }

    public void setShowLine(int showLine) {
        this.showLine = showLine;
    }

}