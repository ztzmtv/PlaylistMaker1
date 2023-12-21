package com.azmetov.playlistmaker.utils

fun interface IObserver {
    fun update(data: Any)
}

interface IObservable {
    val observers: ArrayList<IObserver>

    fun subscribe(observer: IObserver) {
        observers.add(observer)
    }

    fun unsubscribe(observer: IObserver) {
        observers.remove(observer)
    }

    fun unsubscribe() {
        observers.clear()
    }

    fun sendUpdateEvent(data: Any) {
        observers.forEach { it.update(data) }
    }
}