package com.learning.datastructure;

public class LruCache implements Cache {
    
    private volatile long maxSize;
    private volatile long size;
    
    @Override
    public int put(Object key, Object object) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public Object get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void clean(Object key) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void cleanAll() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public long getSize() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public long getMaxSize() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public final static class Entry {
        private String key;
        private Object value;
        private long updateTime;
        
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public Object getValue() {
            return value;
        }
        public void setValue(Object value) {
            this.value = value;
        }
        public long getUpdateTime() {
            return updateTime;
        }
        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
        
    }
}
