package com.example.pillnotes.domain.stars

class ModelStar {

    companion object {
        var MOTION_SPEED = 0.0006f
        var START_Z = -0.7f
    }

    private var points: ArrayList<PointStar> = ArrayList()
    fun update(elapsedTime: Long) {
        points.add(PointStar(random(-1f, 1f), random(-1f, 1f), START_Z, 0f))
        for (point in points) {
            point.z += elapsedTime * MOTION_SPEED
            point.r = point.r + 3
        }
        val iterator: MutableIterator<PointStar> = points.iterator()
        while (iterator.hasNext()) {
            val point: PointStar = iterator.next()
            if (point.z >= 0) {
                iterator.remove()
            }
        }
    }

    private fun random(from: Float, to: Float): Float {
        return (from + (to - from) * Math.random()).toFloat()
    }

    fun getPoints(): ArrayList<PointStar> {
        return points
    }
}