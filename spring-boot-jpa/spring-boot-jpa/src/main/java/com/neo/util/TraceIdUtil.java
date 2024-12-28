package com.neo.util;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

@Slf4j
public class TraceIdUtil {
    public static final String TRACE_ID = "TraceId";

    /**
     * 生成TraceId
     * @return
     */
    public static String generateTraceId(){
        String traceId = IdUtil.fastSimpleUUID().toUpperCase();
        MDC.put(TRACE_ID, traceId);
        return traceId;
    }
    /**
     * 生成TraceId
     * @return
     */
    public static String generateTraceId(String traceId){
        if (StringUtils.isBlank(traceId)){
           traceId = generateTraceId();
        }
        MDC.put(TRACE_ID, traceId);
        return traceId;
    }
    /**
     * 获取TraceId
     * @return
     */
    public static String getTraceId(){
        String traceId = MDC.get(TRACE_ID);
        return traceId;
    }
    /**
     * 移除TraceId
     * @return
     */
    public static void removeTraceId(){
        MDC.remove(TRACE_ID);
    }
}
