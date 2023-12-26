 package mousavi.hashem.imageeffectwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import mousavi.hashem.imageeffectwithcompose.ui.theme.ImageEffectWithComposeTheme

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageEffectWithComposeTheme {
                MainScreen()
            }
        }
    }
}
