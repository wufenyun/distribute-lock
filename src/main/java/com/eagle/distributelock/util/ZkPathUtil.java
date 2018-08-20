package com.eagle.distributelock.util;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public final class ZkPathUtil {

    private final static String SLASH = "/";

    public static TreeMap<String,String> listSortedNodeSequenceNum(List<String> nodeNames, String prefix) {
        AssertUtil.notNull(nodeNames);
        TreeMap<String,String> sortedNodeMap = new TreeMap<>();
        nodeNames.forEach(nodeName -> {
            sortedNodeMap.put(getNodeSequenceNum(nodeName,prefix),nodeName);
        });
        return sortedNodeMap;
    }

    public static String getNodeSequenceNum(String nodeName,String prefix) {
        AssertUtil.notBlank(nodeName);
        return nodeName.substring(prefix.length());
    }

    public static String getNodeNameByPath(String path) {
        AssertUtil.notBlank(path);
        String[] split = path.split(SLASH);
        return split[split.length-1];
    }

    public static String getPathSequenceNum(String path,String prefix) {
        AssertUtil.notBlank(path);
        return getNodeSequenceNum(getNodeNameByPath(path),prefix);
    }
}
