package com.yusufarisoy.composepaging.domain

import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepaging.util.NetworkError

class HomePagingSource(
    private val useCase: FetchCharactersUseCase,
    private val onPageLoaded: (itemCount: Int) -> Unit
) : ComposePagingSource<CharacterUiModel>() {

    override suspend fun load(params: Params): Result<CharacterUiModel> {
        val page = params.page
        val response = useCase.run(FetchCharactersUseCase.Params(page = page))

        if (page == 0 && response.characters.isEmpty()) {
            return Result.Error(NetworkError.EmptyFeedError("No results found"))
        }

        onPageLoaded(response.count)

        return Result.Page(data = response.characters, hasNextPage = response.hasNextPage)
    }
}
