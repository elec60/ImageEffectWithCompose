package mousavi.hashem.imageeffectwithcompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import mousavi.hashem.imageeffectwithcompose.model.Cell
import kotlin.math.hypot

@Composable
fun MainScreen(
    columnCount: Int = 100,
    rowCount: Int = 300,
    distanceAroundTap: Float = 100f
) {
    BoxWithConstraints {
        val density = LocalDensity.current
        val parentWidth = with(density) { this@BoxWithConstraints.maxWidth.roundToPx() }
        val parentHeight = with(density) { this@BoxWithConstraints.maxHeight.roundToPx() }
        val cellWith = parentWidth / columnCount + 1
        val cellHeight = parentHeight / rowCount
        val cells = remember {
            List(columnCount) { col ->
                val x = col * cellWith
                List(rowCount) { row ->
                    val y = row * cellHeight
                    Cell(
                        x = x,
                        y = y
                    )
                }
            }
        }

        val imageBitmap = ImageBitmap.imageResource(id = R.drawable.myself)
        var tappedPosition by remember {
            mutableStateOf(Offset.Zero)
        }
        var drage by remember {
            mutableStateOf(Offset.Zero)
        }

        Canvas(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            tappedPosition = it
                        },
                        onDragEnd = {
                            tappedPosition = Offset.Zero
                        }
                    ) { _, dragAmount ->
                        drage = dragAmount
                        tappedPosition += dragAmount
                        println(dragAmount)
                    }
                }
                .fillMaxSize()
        ) {
            cells.flatten().forEach { cell ->
                if (tappedPosition != Offset.Zero) {
                    val dx = cell.x - tappedPosition.x
                    val dy = cell.y - tappedPosition.y
                    val distance = hypot(dx, dy)
                    if (distance < distanceAroundTap) {
                        val ratio = distance / distanceAroundTap
                        cell.scaleX += if (drage.x > 0f) -ratio else ratio
                        cell.scaleY += if (drage.y > 0f) -ratio else ratio
                    }
                }
                drawImage(
                    image = imageBitmap,
                    srcOffset = IntOffset(
                        x = (cell.x + cell.scaleX).toInt(),
                        y = (cell.y + cell.scaleY).toInt()
                    ),
                    srcSize = IntSize(width = cellWith, height = cellHeight),
                    dstOffset = IntOffset(
                        x = (cell.x),
                        y = (cell.y)
                    ),
                    dstSize = IntSize(width = cellWith, height = cellHeight),
                )
            }
        }
    }
}