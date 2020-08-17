package com.can_apps.bad_char_data_source

interface BadCharDataSource {

    fun getAll(): List<BadCharDto>

    fun getById(id: BadCharIdDto): BadCharDto
}