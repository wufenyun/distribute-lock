package com.learning.netty.codec;

public class Request {

    private long rid;
    private int length;
    private String data;

    /**
     * @return the rid
     */
    public long getRid() {
        return rid;
    }

    @Override
    public String toString() {
        return "Request [rid=" + rid + ", length=" + length + ", data=" + data + "]";
    }

    /**
     * @param rid
     *            the rid to set
     */
    public void setRid(long rid) {
        this.rid = rid;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

}
