package com.example.networktest.network

// APIレスポンス
data class HeartRailsExpressProperty<T> (
    val response: T)

//エリアリスト
data class AreaResponse (
    val area: List<String>)

//都道府県リスト
data class PrefecturesResponse (
    val prefecture: List<String>)

//路線リスト
data class LinesResponse (
    val line: List<String>)

//駅リスト
data class StationsResponse (
    val station: List<StationInfo>
)

//駅情報
data class StationInfo (
    val name: String,
    val prefecture: String = "",
    val line: String = "",
    val x:Double = 0.0,
    val y:Double = 0.0
) {
    override fun toString(): String {
        return name
    }
    val text: String get() = "$name($prefecture, $line) at ($x, $y)"
}