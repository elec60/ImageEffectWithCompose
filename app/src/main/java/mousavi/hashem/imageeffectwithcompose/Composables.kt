package mousavi.hashem.imageeffectwithcompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import mousavi.hashem.imageeffectwithcompose.model.Cell

@Composable
fun MainScreen(
    columnCount: Int = 41,
    rowCount: Int = 100
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
                    Cell(x, y)
                }
            }
        }

        val imageBitmap = ImageBitmap.imageResource(id = R.drawable.myself)
        var changeOffset by remember {
            mutableStateOf(Offset.Zero)
        }


        Canvas(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        changeOffset += dragAmount
                    }
                }
                .fillMaxSize()
        ) {

            cells.forEachIndexed { index, columns ->
                columns.forEach { cell ->
                    drawImage(
                        image = imageBitmap,
                        srcOffset = IntOffset(
                            x = (cell.x + changeOffset.x).toInt(),
                            y = (cell.y + changeOffset.y).toInt()
                        ),
                        srcSize = IntSize(width = cellWith, height = cellHeight),
                        dstOffset = IntOffset(
                            x = (cell.x + changeOffset.x).toInt(),
                            y = (cell.y + changeOffset.y).toInt()
                        ),
                        dstSize = IntSize(width = cellWith, height = cellHeight),
                    )
//                    drawRect(
//                        color = Color.Red,
//                        topLeft = Offset(x = cell.x.toFloat(), y = cell.y.toFloat()),
//                        size = Size(width = cellWith.toFloat(), height = cellHeight.toFloat()),
//                        style = Stroke(width = 1f)
//                    )
                }
            }
        }
    }
}