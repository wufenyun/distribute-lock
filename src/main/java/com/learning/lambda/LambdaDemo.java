/**
 * Package: com.demo.lambda
 * Description: 
 */
package com.learning.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年10月18日 下午12:03:29
 * @author wufenyun 
 */
public class LambdaDemo {
    
    @Test
    public void test() {
        List<String> list = Arrays.asList("ddd","aaa","bbb","ccc");
        /*Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });*/
        Collections.sort(list,(o1,o2)->o1.compareTo(o2));
        for(String str:list) {
            System.out.println(str);
        }
    }
    
    interface HelloService {
        void message();
    }
}
