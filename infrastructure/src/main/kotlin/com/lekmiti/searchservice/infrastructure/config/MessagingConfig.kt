package com.lekmiti.searchservice.infrastructure.config

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.SubscribableChannel

@Configuration
@EnableBinding(ExternalBindings::class)
class ConsumersConfig

interface ExternalBindings {

    @Input("candidate-created")
    fun candidateCreatedChannel(): SubscribableChannel

    @Input("candidate-updated")
    fun candidateUpdatedChannel(): SubscribableChannel

    @Input("candidate-data-deleted")
    fun candidateDataDeletedChannel(): SubscribableChannel

    @Input("company-as-client-init")
    fun companyAsClientInitChannel(): SubscribableChannel


}