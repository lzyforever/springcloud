package com.jack.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 合并请求HystrixCommand
 */
public class MyEighthHystrixCommand extends HystrixCollapser<List<String>, String, String> {

    private final String name;

    public MyEighthHystrixCommand(String name) {
        this.name = name;
    }

    @Override
    public String getRequestArgument() {
        return name;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {
        return new BatchCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, String>> requests) {
        int count = 0;
        for (CollapsedRequest<String, String> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>> {

        private final Collection<CollapsedRequest<String, String>> requests;

        private BatchCommand(Collection<CollapsedRequest<String, String>> requests) {
            super(HystrixCommand.Setter.withGroupKey(
                    HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("HELL_KEY"))
            );
            this.requests = requests;
        }

        protected List<String> run() throws Exception {
            System.out.println("真正执行请求....");
            List<String> response = new ArrayList<String>();
            for (CollapsedRequest<String, String> request : requests) {
                response.add("返回的结果：" + request.getArgument());
            }
            return response;
        }
    }
}
