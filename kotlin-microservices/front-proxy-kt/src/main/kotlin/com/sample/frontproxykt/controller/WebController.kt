package com.sample.frontproxykt.controller

import com.sample.frontproxykt.model.GenericDTO
import com.sample.frontproxykt.service.MessageHandler
import com.sample.frontproxykt.utils.JSONConverter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.log.LogMessage
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult

@RestController
@RequestMapping("/user")
class WebController(private var messageHandler: MessageHandler , private var jsonConverter: JSONConverter) {

    private val log:Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/greet", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun greet(): ResponseEntity<String> {
        return ResponseEntity.ok("hello")
    }

    @PostMapping(value = ["/registration/{apis}"], consumes =[ MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun processRequest(@RequestBody payload: GenericDTO, @PathVariable("apis") apis: String): DeferredResult<Map<String,Any?>> {

        var deferredResult : DeferredResult<Map<String,Any?>> = DeferredResult(500);
        log.info(jsonConverter.toMap(payload).toString())
        messageHandler.sendMessage( Integer.parseInt(apis),jsonConverter.toMap(payload) , deferredResult)
        return deferredResult;
    }
}