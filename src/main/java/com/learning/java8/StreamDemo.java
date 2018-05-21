package com.learning.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class StreamDemo {
    
    private List<CategorySite> list;
    
    @Before
    public void init() {
        CategorySite s1 = new CategorySite();
        s1.setSite(1);
        s1.setName("zbj");
        CategorySite s2 = new CategorySite();
        s2.setSite(2);
        s2.setName("tpw");
        
        list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
    }
    
    @Test
    public void lamda() {
        Runnable r1 = ()->{System.out.println("sss");try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }};
        new Thread(r1).start();
        
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stream() {
        //list.stream().forEach((site)->System.out.println(site.getName()));
        list.stream().filter((site)->site.getSite()==2).forEach((site)->System.out.println(site.getName()));
        System.out.println(list.stream().filter((site)->site.getSite()==2).distinct().count());
    }
    
    @Test
    public void collect() {
        List<CategorySite> l = list.stream().filter((site)->site.getSite()==1).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(l));
    }
    
    @Test
    public void min() {
        CategorySite site = list.stream().min(new SiteComparator()).get();
        System.out.println(JSON.toJSONString(site));
        
        CategorySite site2 = list.stream().max(new SiteComparator()).get();
        System.out.println(JSON.toJSONString(site2));
        
        CategorySite site3 = list.stream().max(Comparator.comparing(num->site.getSite())).get();
        System.out.println(JSON.toJSONString(site3));
    }
    
    
    public static class SiteComparator implements Comparator<CategorySite>{

        @Override
        public int compare(CategorySite o1, CategorySite o2) {
            return o1.getSite() - o2.getSite();
        }
        
    }
    
    
    public static class CategorySite implements Comparable<CategorySite>{
        private String _id;

        /**
         * 站点
         */
        private Integer site;

        /**
         * 站点名称
         */
        private String name;

        /**
         * 站点状态（1正常  2隐藏）。隐藏的站点在boss后台还未选择的地方不展示，但是已经选择的需要展示。
         */
        private String status;

        @Override
        public int compareTo(CategorySite o) {
            // TODO Auto-generated method stub
            return 0;
        }
        
        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public Integer getSite() {
            return site;
        }

        public void setSite(Integer site) {
            this.site = site;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        
    }
}
