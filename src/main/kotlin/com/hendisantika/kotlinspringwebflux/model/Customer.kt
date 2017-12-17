package com.hendisantika.kotlinspringwebflux.model

/**
 * Created by IntelliJ IDEA.
 * Project : kotlin-spring-webflux
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/12/17
 * Time: 03.58
 * To change this template use File | Settings | File Templates.
 */
data class Customer(
        var id: Long = -1,
        var firstName: String? = null,
        var lastName: String? = null,
        var age: Int? = null,
        var address: Address = Address())