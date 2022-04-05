package com.example.tmdb_app

object Genre {
    private val genreMap = mapOf<Int, String>(
        28 to "動作",
        12 to "冒險",
        16 to "動畫",
        35 to "喜劇",
        80 to "犯罪",
        99 to "紀錄",
        18 to "劇情",
        10751 to "家庭",
        14 to "奇幻",
        36 to "歷史",
        27 to "恐怖",
        10402 to "音樂",
        9648 to "懸疑",
        10749 to "愛情",
        878 to "科幻",
        10770 to "TV Movie",
        53 to "驚悚",
        10752 to "戰爭",
        37 to "西部"
    )

    fun getGenre(ids: List<Int>?) : String {
        ids?.let {
            val genreStrs = mutableListOf<String>()
            ids.forEach {
                genreStrs.add(getGenre(it))
            }
            return genreStrs.joinToString(separator = ", ")
        } ?: return  ""
    }

    private fun getGenre(id: Int?) : String {
        genreMap[id].let {
            return it ?: ""
        }
    }
}