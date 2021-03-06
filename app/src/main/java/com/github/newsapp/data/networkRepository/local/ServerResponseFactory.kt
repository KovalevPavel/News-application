package com.github.newsapp.data.networkRepository.local

import com.github.newsapp.data.entities.ImageToLoad
import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.data.entities.ServerResponseItem
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.domain.usecases.network.NewsTypes
import kotlin.random.Random

//Фабрика, отвечающая за генерацию фейковых новостей
class ServerResponseFactory {
    companion object {
        private const val DEFAULT_PREVIEW =
            "https://img.jakpost.net/c/2019/05/20/2019_05_20_72607_1558317268._large.jpg"

        private val IMAGE_LIST = listOf(
            "https://img.jakpost.net/c/2019/05/20/2019_05_20_72607_1558317268._large.jpg",
            "https://i20.kanobu.ru/r/237baa06e8539dbff29787a4442ad158/1040x700/u.kanobu.ru/editor/images/71/05241395-2f9f-42db-8a7f-5b592c3ecd3f.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSZSHRV9i71Iu52G09I1EmiLWC4ARcM8Gliw&usqp=CAU"
        )
    }

    private var itemsCount = 0
    fun generateServerResponse(): ServerResponseItem {
        generateItemsCount()
        val newsList = List(itemsCount) { currentIndex ->
            generateNewsItem(currentIndex + 1L)
        }
        return ServerResponseItem(
            newsList,
            itemsCount
        )
    }

    private fun generateItemsCount() {
        itemsCount = Random.nextInt(50, 150)
    }

    private fun generateNewsItem(newsIndex: Long): NewsItem {
        return NewsItem(
            id = newsIndex,
            title = generateNewsTitle(newsIndex),
            description = generateDescription(),
            type = generateNewsType(),
            previewImage = generatePreviewImage(),
            publishedAt = generatePublishTime(),
            isEnabled = generateAvailability()
        )
    }

    fun generateNewsItemExtended(newsID: Long): RecItemExtended {
        val newsType = generateNewsType()
        return RecItemExtended(
            id = newsID,
            title = generateNewsTitle(newsID),
            description = generateDescription(),
            type = newsType,
            publishedAt = generatePublishTime(),
            images = generateListImages(),
            shareText = generateShareText(newsID, newsType)
        )
    }

    private fun generateNewsTitle(number: Long) = "Заголовок новости $number"

    private fun generateDescription() =
        "Сегодня Киану Ривз был найден в своей квартире в добром здравии, порадуемся за него!"

    private fun generateNewsType(): NewsTypes {
        val availableTypes = NewsTypes.values()
        val randomIndex = Random.nextInt(0, availableTypes.size)
        return availableTypes[randomIndex]
    }

    private fun generatePreviewImage() = if (Random.nextBoolean()) DEFAULT_PREVIEW else null

    private fun generateAvailability() = Random.nextBoolean()

    private fun generatePublishTime() = Random.nextLong(0, System.currentTimeMillis())

    private fun generateListImages(): List<ImageToLoad>? {
        val listSize = Random.nextInt(0, IMAGE_LIST.size+1)
        if (listSize == 0) return null
        val namesList = List(listSize) { "image$it" }
        val imagesList = IMAGE_LIST.filterIndexed { index, _ -> index < listSize }
        val tempList = mutableListOf<ImageToLoad>()
        (0 until listSize).forEach {
            tempList.add(
                ImageToLoad(
                    name = namesList[it],
                    url = imagesList[it]
                )
            )
        }
        return tempList
    }

    private fun generateShareText(newsID: Long, newsType: NewsTypes): String? {
        val shareType = when (newsType) {
            NewsTypes.NEWS -> "новость"
            NewsTypes.ALERT -> "уведомление"
        }
        val isNull = Random.nextBoolean()
        return if (isNull) null else "Оцени $shareType! ${generateNewsTitle(newsID)}"
    }
}