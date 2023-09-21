package com.sample.frontproxykt.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.springframework.web.context.request.async.DeferredResult
import java.util.*

@Service
class MessageHandler(private val rabbitTemplate: RabbitTemplate, template: RabbitTemplate) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    val constId = "correlationId";
    var mutableMap = mutableMapOf<String, Any?>();

    fun sendMessage(apis: Int, payLoad: Map<String, Any?>, deferredResult: DeferredResult<Map<String, Any?>>) {
        var correlationId = correlationId();
        mutableMap.put(correlationId, deferredResult)

        val temp = payLoad.toMutableMap();
        temp.put(constId,correlationId)

        var queue_number = apis * 15 + 1;
        val routingKey = "user.engine.$queue_number"


       rabbitTemplate.convertAndSend(routingKey, temp)

    }


    private fun correlationId(): String {
        return UUID.randomUUID().toString();
    }

    @RabbitListener(queues = ["frontProxy.final.response","user.engine.1"])
    fun onResponse( response : Map<String, Any?>) : Unit{
        log.info("receiver : $response")
        var id: Any? = response.get(constId)
        // System.out.println("rece"+correlationId);
        if(mutableMap.containsKey(id)){
            val deferredResult = mutableMap.get(id) as DeferredResult<Any?>
            deferredResult.setResult(response);
            mutableMap.remove(id);
        }

    }
}