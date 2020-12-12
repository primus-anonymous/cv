package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.compose.*

private val IMG_HEIGHT = 200.dp
private val CARD_HEIGHT = 250.dp

@Composable
fun ProjectItem(
        project: Project,
        itemClicked: ProjectItemClicked?,
) {

    Surface(shape = RoundedCornerShape(DEFAULT_CORNER),
            elevation = 4.dp,
            modifier = Modifier.padding(SMALL_MARGIN)
                    .fillMaxWidth()
                    .height(CARD_HEIGHT)) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()
                .clickable(onClick = {
                    itemClicked?.invoke(project)
                })) {

            val (image, name, platform) = createRefs()


            Box(modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                height = Dimension.value(IMG_HEIGHT)
                width = Dimension.fillToConstraints
            }) {
                UrlImage(url = project.webPic.orEmpty(),
                         modifier = Modifier
                                 .fillMaxSize()
                                 .padding(DEFAULT_MARGIN))
            }

            Text(text = project.name.orEmpty(),
                 modifier = Modifier
                         .constrainAs(name) {
                             bottom.linkTo(parent.bottom)
                             start.linkTo(parent.start)
                             end.linkTo(parent.end)
                             top.linkTo(image.bottom)
                         }
                         .padding(bottom = HALF_MARGIN,
                                  start = HALF_MARGIN,
                                  end = HALF_MARGIN)
            )

            Image(imageVector = vectorResource(id = if (project.platform == Project.PLATFORM_ANDROID) {
                R.drawable.ic_android
            } else {
                R.drawable.ic_apple
            }),
                  modifier = Modifier
                          .zIndex(1.0f)
                          .constrainAs(platform) {
                              top.linkTo(parent.top)
                              end.linkTo(parent.end)
                          }
                          .padding(DEFAULT_MARGIN))
        }
    }
}

@Preview(widthDp = 200)
@Composable
fun PreviewProjectItem() {
    MaterialTheme(colors = CvColors) {
        ProjectItem(project = Project(
                platform = "android",
                webPic = "https://images.freeimages.com/images/large-previews/996/easter-1399885.jpg",
                name = "Project Name"
        ),
                    itemClicked = null)
    }
}