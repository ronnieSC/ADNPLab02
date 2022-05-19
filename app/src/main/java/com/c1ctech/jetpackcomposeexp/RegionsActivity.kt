package com.c1ctech.jetpackcomposeexp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.c1ctech.jetpackcomposeexp.ui.theme.JetpackComposeExpTheme
import kotlinx.android.parcel.Parcelize
import androidx.compose.foundation.Image as Image

val region_name_costa = "Costa"
val region_name_sierra = "Sierra"
val region_name_selva = "Selva"

val region_description_costa = "La costa peruana no solo es " +
        "conocida por su bonita orografía y sus bellas " +
        "ciudades, sino que también forma parte de la cultura" +
        " gastronómica del país."

val region_description_sierra = "En esta zona de nuestro país " +
        "se consumen platos típicos de Perú, pero también " +
        "cuenta con un buen número de platos únicos que hacen" +
        " de esta región un lugar exquisito para realizar una" +
        " ruta gastronómica."

val region_description_selva = "La gastronomía peruana ha " +
        "sabido mezclar muy bien lo exótico de los productos " +
        "de esta región con el ingenio de los mejores cocineros, " +
        "dando lugar a una mezcla de sabores inigualable."

//Aquí vendrá la lista
val regiones_lista: List<Region> = listOf(
    Region(R.drawable.costa, region_name_costa, region_description_costa),
    Region(R.drawable.sierra, region_name_sierra, region_description_sierra),
    Region(R.drawable.selva, region_name_selva, region_description_selva),
)

class RegionsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExpTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    //Material Design top app bar displays information and actions relating to the current screen.
                    TopAppBar(title = {
                        Text("Platos Típicos")
                    })
                    RegionList(regiones_lista, this@RegionsActivity)
                }
            }
        }
    }
}

@Parcelize
data class Region(
    @DrawableRes val imageResource: Int,
    val region_name: String,
    val region_description: String
) : Parcelable

@Composable
fun RegionList(regions: List<Region>, context: Context) {
    //The vertically scrolling list that only composes and lays out the currently visible items.
    LazyColumn() {
        items(regions) { region ->
            RegionListItem(region, context)
        }
    }
}

@Composable
fun RegionListItem(region: Region, context: Context) {
    //used to make a CardView.
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                //open DetailActivity on item click
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("regionData", region)
                startActivity(context, intent, null)

            }
    ) {
        //places its children in a horizontal sequence.
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            RegionImage(region)
            //places its children in a vertical sequence.
            Column(
                modifier = Modifier
                    .weight(6f, true)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
            ) {
                //used to display text
                Text(
                    text = region.region_name,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Red
                )
                Text(
                    text = "${region.region_description}",
                    style = MaterialTheme.typography.subtitle2,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                )

            }
        }
    }
}


@Composable
fun RegionImage(region: Region?) {
    //used to display an image.
    Image(
        painter = painterResource(region!!.imageResource),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
    )
}

//Preview can be applied to @Composable methods with no parameters
//to show them in the Android Studio preview.
@Preview
@Composable
fun regionListPreview() {
    JetpackComposeExpTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text("Platos Típicos")
            })
            RegionList(
                regiones_lista,
                LocalContext.current
            )
        }
    }
}


