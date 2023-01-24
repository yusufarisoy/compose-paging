package com.yusufarisoy.composepagingapp.domain

import com.yusufarisoy.composepagingapp.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSourceWithQuery
import com.yusufarisoy.composepagingapp.util.NetworkError

class SearchPagingSource(
    private val useCase: FetchCharactersUseCase
) : ComposePagingSourceWithQuery<SearchPagingSource.Params, CharacterUiModel>() {

    override suspend fun load(params: Params): ComposePagingSource.Result<CharacterUiModel> {
        val response = useCase.run(FetchCharactersUseCase.Params(params.page, params.query))

        if (params.page == 0 && response.characters.isEmpty()) {
            return ComposePagingSource.Result.Error(NetworkError.EmptyFeedError("No results found"))
        }

        return ComposePagingSource.Result.Page(
            data = response.characters,
            hasNextPage = response.hasNextPage
        )
    }

    data class Params(
        override val page: Int,
        override val query: String
    ) : ComposePagingSourceWithQuery.Params(page, query)
}
