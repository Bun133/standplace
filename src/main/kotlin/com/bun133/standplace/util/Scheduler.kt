package com.bun133.standplace.util

import com.bun133.standplace.Standplace

class Scheduler(var plugin:Standplace){
    fun setTimer(ticks:Long,runnable:Runnable){
        plugin.server.scheduler.runTaskLater(plugin,runnable,ticks)
    }
}