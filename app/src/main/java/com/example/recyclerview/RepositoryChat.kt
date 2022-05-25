package com.example.recyclerview

import com.github.javafaker.Faker

class RepositoryChat {
    private val randomUser: Faker = Faker.instance()

    fun generateChat(movies: ArrayList<User>, old: ArrayList<User>): ArrayList<User> {
        val start = old.size
        val end = start + 9
        IMAGES.shuffle()
        if (end <= 40) {
            (start..end).mapTo(movies) {
                User(
                    id = it.toLong(),
                    name = randomUser.name().name(),
                    company = randomUser.company().name(),
                    photo = IMAGES[it % IMAGES.size],

                    countMessage = (15..30).random()
                )
            }
        }
        return movies
    }

    fun initChat(movies: MutableList<User>): MutableList<User> {

        IMAGES.shuffle()
        (0..10).mapTo(movies) {
            User(
                id = it.toLong(),
                name = randomUser.name().name(),
                company = randomUser.company().name(),
                photo = IMAGES[it % IMAGES.size],
                countMessage = (15..30).random()
            )
        }
        return movies
    }


    companion object {
        private val IMAGES = mutableListOf(
            "https://1avatara.ru/pic/cartoons/mult009.jpg",
            "https://1avatara.ru/pic/cartoons/mult022.jpg",
            "https://1avatara.ru/pic/cartoons/mult032.jpg",
            "https://1avatara.ru/pic/cartoons/mult042.jpg",
            "https://1avatara.ru/pic/cartoons/mult052.jpg",
            "https://1avatara.ru/pic/cartoons/mult055.jpg",
            "https://1avatara.ru/pic/cartoons/mult059.jpg",
            "https://1avatara.ru/pic/cartoons/mult063.jpg",
            "https://1avatara.ru/pic/cartoons/mult067.jpg",
            "https://1avatara.ru/pic/cartoons/mult069.jpg",
            "https://1avatara.ru/pic/cartoons/mult070.jpg",
            "https://1avatara.ru/pic/cartoons/mult073.jpg",
            "https://1avatara.ru/pic/cartoons/mult077.jpg",
            "https://1avatara.ru/pic/cartoons/mult078.jpg",
            "https://1avatara.ru/pic/cartoons/mult080.jpg",
            "https://1avatara.ru/pic/cartoons/mult081.jpg",
            "https://1avatara.ru/pic/cartoons/mult083.jpg",
            "https://1avatara.ru/pic/cartoons/mult084.jpg"
        )
    }
}