package com.hendisantika.kotlinspringwebflux.controller

import com.hendisantika.kotlinspringwebflux.model.Address
import com.hendisantika.kotlinspringwebflux.model.Customer
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

/**
 * Created by IntelliJ IDEA.
 * Project : kotlin-spring-webflux
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/12/17
 * Time: 04.00
 * To change this template use File | Settings | File Templates.
 */

@RestController
@RequestMapping(value="/api/customer")
class RestControllerAPIs {
    val log = LoggerFactory.getLogger(RestControllerAPIs::class.java)

    // Define customer storage
    val custStores = mutableMapOf<Long, Customer>()

    @PostConstruct
    fun initial(){
        custStores.put(1, Customer(1, "Hendi", "Santika", 30, Address("Kolonel Masturi", "212")))
        custStores.put(2, Customer(2, "Uzumaki", "Naruto", 25, Address("Konohagakure", "412")))
    }

    @GetMapping
    fun getAll(): Flux<Customer> {
        return Flux.fromIterable(ArrayList(custStores.values))
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Long): Mono<Customer> {
        return Mono.justOrEmpty(custStores.get(id))
    }

    @PostMapping("/post")
    fun postCustomer(@RequestBody customer: Customer): Mono<ResponseEntity<String>>{
        // do post
        custStores.put(customer.id, customer)

        log.info("########### POST:" + customer)

        return Mono.just(ResponseEntity("Post Successfully!", HttpStatus.CREATED))
    }

    @PutMapping("/put/{id}")
    fun putCustomer(@PathVariable id: Long, @RequestBody customer: Customer): Mono<ResponseEntity<Customer>>{
        // reset customer.Id
        customer.id = id

        if(custStores.get(id) != null){
            custStores.replace(id, customer)
        }else{
            customer.id = id
            custStores.put(id, customer)
        }

        log.info("########### PUT:" + customer)
        return Mono.just(ResponseEntity(customer, HttpStatus.CREATED))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Long): Mono<ResponseEntity<String>> {
        val cust = custStores.remove(id)
        if(cust != null){
            log.info("########### DELETE:" + cust)
        }else{
            log.info("########### Don't exist any customer with id = ${id}")
        }

        return Mono.just(ResponseEntity("Delete Succesfully!", HttpStatus.ACCEPTED))
    }
}