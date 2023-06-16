package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankProfileDomain

internal class RankRepository(
    private val rankApi: RankApi, // API was removed =/
    private val dtoMapper: RankDtoMapper
) : RankContract.Repository {

    override suspend fun getProfiles(): Set<RankProfileDomain> =
//        dtoMapper.toDomain(rankApi.getAll().profiles)
        dtoMapper.toDomain(FAKE_DATA.profiles)

    companion object {
        val FAKE_DATA = RankDto(
            profiles = setOf(
                RankProfileDto(
                    username = RankUsernameDto(value = "Rick"),
                    weeklyXP = RankXpDto(value = 999),
                    isCurrentUser = RankCurrentUserDto(value = false)
                ),
                RankProfileDto(
                    username = RankUsernameDto(value = "Morty"),
                    weeklyXP = RankXpDto(value = 42069),
                    isCurrentUser = RankCurrentUserDto(value = true)
                ),
                RankProfileDto(
                    username = RankUsernameDto(value = "Beth"),
                    weeklyXP = RankXpDto(value = 12345),
                    isCurrentUser = RankCurrentUserDto(value = true)
                ),
                RankProfileDto(
                    username = RankUsernameDto(value = "Summer"),
                    weeklyXP = RankXpDto(value = 10000),
                    isCurrentUser = RankCurrentUserDto(value = true)
                ),
                RankProfileDto(
                    username = RankUsernameDto(value = "Squanchy"),
                    weeklyXP = RankXpDto(value = 0),
                    isCurrentUser = RankCurrentUserDto(value = false)
                ),
            )
        )
    }
}
