/**
 * Package: com.eagle.distributelock
 * Description: 
 */
package com.eagle.distributelock;

/**
 * 基于ZooKeeper分布式锁:
 * 在zookeeper指定节点(lockname)下创建临时顺序节点competitor
 * 获取lockname下所有子节点children
 * 对子节点按节点自增序号从小到大排序
 * 判断本节点是不是第一个子节点，若是，则获取锁；若不是，则监听比该节点小的那个节点的删除事件
 * 若监听事件生效，则回到第二步重新进行判断，直到获取到锁
 * 
 * Date: 2018年1月10日 下午3:10:57
 * @author wufenyun 
 */
public interface Lock {
    
    void lock();
    
    void lock(long timeoutSeconds);
    
    void unlock();
}
