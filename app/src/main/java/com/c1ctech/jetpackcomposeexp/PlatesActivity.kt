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

val platos_costa_lista: List<Plate> = listOf(
    Plate(R.drawable.costa_ceviche, "Ceviche", "En muchas variedades"),
    Plate(R.drawable.costa_chupe_de_pescado, "Chupe de pescado", "Ideal para el frío"),
)

val platos_sierra_lista: List<Plate> = listOf(
    Plate(R.drawable.sierra_rocoto_relleno, "Rocoto relleno", "Arequipa en su esplendor"),
    Plate(R.drawable.sierra_pachamanca, "Pachamanca","Lo que todo turista debe probar"),
    Plate(R.drawable.sierra_cuy_chactado, "Cuy chactado","Arequipa y Cusco"),
    Plate(R.drawable.sierra_pastel_de_papa, "Pastel de papa","Esta delicia no te puede faltar"),
)

val platos_selva_lista: List<Plate> = listOf(
    Plate(R.drawable.selva_paiche, "Paiche","Un pez con un gran sabor"),
    Plate(R.drawable.selva_juane, "Juane","De la selva su encanto"),
)

val platos_lista: List<Plate> = listOf(
    Plate(R.drawable.costa_ceviche, "Ceviche", "En muchas variedades"),
    Plate(R.drawable.costa_chupe_de_pescado, "Chupe de pescado", "Ideal para el frío"),
    Plate(R.drawable.sierra_rocoto_relleno, "Rocoto relleno", "Arequipa en su esplendor"),
    Plate(R.drawable.sierra_pachamanca, "Pachamanca","Lo que todo turista debe probar"),
    Plate(R.drawable.sierra_cuy_chactado, "Cuy chactado","Arequipa y Cusco"),
    Plate(R.drawable.sierra_pastel_de_papa, "Pastel de papa","Esta delicia no te puede faltar"),
    Plate(R.drawable.selva_paiche, "Paiche","Un pez con un gran sabor"),
    Plate(R.drawable.selva_juane, "Juane","De la selva su encanto"),
)


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*JetpackComposeExpTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    //Material Design top app bar displays information and actions relating to the current screen.
                    TopAppBar(title = {
                        Text("Platillos")
                    })
                    PlateList(platos_lista, this@MainActivity)
                }
            }*/
            RegionListIntent(intent.getParcelableExtra("regionData"))
        }
    }
}

@Parcelize
data class Plate(
    @DrawableRes val imageResource: Int,
    val plate_name: String,
    val plate_description: String
) : Parcelable

@Composable
fun PlateList(plates: List<Plate>, context: Context) {
    //The vertically scrolling list that only composes and lays out the currently visible items.
    LazyColumn() {
        items(plates) { plate ->
            PlateListItem(plate, context)
        }
    }
}

@Composable
fun PlateListItem(plate: Plate, context: Context) {
    //used to make a CardView.
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                //open DetailActivity on item click
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("plateData", plate)
                startActivity(context, intent, null)

            }
    ) {
        //places its children in a horizontal sequence.
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            PlateImage(plate)
            //places its children in a vertical sequence.
            Column(
                modifier = Modifier
                    .weight(6f, true)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
            ) {
                //used to display text
                Text(
                    text = plate.plate_name,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Red
                )
                Text(
                    text = "${plate.plate_description}",
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
fun PlateImage(plate: Plate?) {
    //used to display an image.
    Image(
        painter = painterResource(plate!!.imageResource),
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
fun plateListPreview() {
    JetpackComposeExpTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text("Platillos")
            })
            PlateList(
                platos_lista,
                LocalContext.current
            )
        }
    }
}

@Composable
fun RegionListIntent(region: Region?) {
    /*Image(
        painter = painterResource(region!!.imageResource),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    )*/

    JetpackComposeExpTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text("Platillos")
            })
            PlateList(
                obtenerLista(region),
                LocalContext.current
            )
        }
    }
}

fun obtenerLista(region: Region?): List<Plate> {
    var lista_de_platos: List<Plate> = platos_costa_lista

    if(region!!.region_name == region_name_costa) {
        lista_de_platos = platos_costa_lista
    }

    if(region!!.region_name == region_name_sierra) {
        lista_de_platos = platos_sierra_lista
    }

    if(region!!.region_name == region_name_selva) {
        lista_de_platos = platos_selva_lista
    }

    return lista_de_platos
}