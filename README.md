# ComposePaging
ComposePaging library for [Jetpack Compose](https://developer.android.com/jetpack/compose).

## About
ComposePaging is a paging library for Compose. ComposePaging makes it possible to use dynamic Composables as **`skippable`** for **better performance** like it should be.

## Metrics
Metrics is the way of measuring the performance of composables. Ideally a composable method should be `restartable` and `skipabble` for the compiler to work with best performance.
<p></p>

Besides being lazy, `ComposePaging` Feed items are easy to maintain and it's easy to make them `restartable` and `skipabble` while being updatable for better performance for your Android application.

## Attributes
| Attribute | Description |
|----- | --|
| `prefetchDistance` | Defines the item distance to request the next page before reaching the bottom |
| `initialPage` | Defines the initial page for the first request |

## Demo
Composables items in the pages are updated without having any performance issues.
| ComposePager | ComposePagerWithQuery |
| ------ | ------ |
| <video src="https://user-images.githubusercontent.com/53172424/214320381-a6d616cb-0f7e-4f14-beb1-debb10c786b2.mp4" /> | <video src="https://user-images.githubusercontent.com/53172424/214320641-001bdc75-a7ea-408f-8475-585109065ff9.mp4" /> |

## Usage
#### PagingSource
ComposePagingSource is used for requesting pages. [Example for more info](https://github.com/yusufarisoy/compose-paging/blob/main/app/src/main/java/com/yusufarisoy/composepaging/domain/HomePagingSource.kt).
```
class PagingSource(
    private val useCase: UseCase,
    private val onPageLoaded: (itemCount: Int) -> Unit
) : ComposePagingSource<UiModel>() {

    override suspend fun load(params: Params): Result<UiModel> {
        val page = params.page
        val response = useCase.run(UseCase.Params(page = page))

        if (page == 0 && response.items.isEmpty()) {
            return Result.Error(NetworkError.EmptyFeedError("No results found"))
        }

        onPageLoaded(response.count)

        return Result.Page(data = response.items, hasNextPage = response.hasNextPage)
    }
}
```

#### PagingData and Pager in ViewModel
ComposePagingData is used for storing the page data, it stores `SnapshotStateList` for better performance and maintainability. [Look for more info](https://github.com/yusufarisoy/compose-paging/blob/main/composepaging/src/main/java/com/yusufarisoy/composepaging/pagingdata/ComposePagingData.kt).
<p> ComposePager is used for managing the page data and states. <a href="https://github.com/yusufarisoy/compose-paging/blob/main/composepaging/src/main/java/com/yusufarisoy/composepaging/ComposePager.kt"> Look for more info. </a> </p>

```
val feed: ComposePagingData<UiModel> = ComposePagingData(prefetchDistance = 2)
private val pager: ComposePager<UiModel> = ComposePager(
    pagingSource = PagingSource(
        useCase = useCase,
        onPageLoaded = ::onPageLoaded
    ),
    initialPage = 1
)
```
