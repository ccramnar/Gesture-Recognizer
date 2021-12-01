package net.codebot.drawing;

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CanvasView: View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    val points = ArrayList<Point>()

    init {
    }

    override fun setOnTouchListener(l: OnTouchListener) {
        super.setOnTouchListener(l)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("DEBUG", "DOWN " + event.x + "," + event.y)
                points.add(Point(event.x.toInt(), event.y.toInt()))
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("DEBUG", "MOVE " + event.x + "," + event.y)
                points.add(Point(event.x.toInt(), event.y.toInt()))
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                Log.d("DEBUG", "UP " + event.x + "," + event.y)
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (point in points) {
            canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), 10.0.toFloat(), Paint(Color.CYAN))
        }
    }

    override fun clearAnimation() {
        super.clearAnimation()
        points.clear()
    }
}