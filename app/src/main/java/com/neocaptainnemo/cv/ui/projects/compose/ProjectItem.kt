package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.compose.UrlImage
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.defaultCorner
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.halfMargin
import com.neocaptainnemo.cv.ui.compose.smallMargin

private val imageHeight = 200.dp
private val cardHeight = 250.dp

@Composable
fun ProjectItem(
    project: Project,
    itemClicked: ProjectItemClicked?,
) {

    Surface(
        shape = RoundedCornerShape(defaultCorner),
        elevation = 4.dp,
        modifier = Modifier
            .padding(smallMargin)
            .fillMaxWidth()
            .height(cardHeight)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    itemClicked?.invoke(project)
                })
        ) {

            val (image, name, platform) = createRefs()

            Box(
                modifier = Modifier.constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    height = Dimension.value(imageHeight)
                    width = Dimension.fillToConstraints
                }
            ) {
                UrlImage(
                    url = project.webPic.orEmpty(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(defaultMargin)
                )
            }

            Text(
                text = project.name.orEmpty(),
                modifier = Modifier
                    .constrainAs(name) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(image.bottom)
                    }
                    .padding(
                        bottom = halfMargin,
                        start = halfMargin,
                        end = halfMargin
                    )
            )

            Icon(
                painter = painterResource(
                    id = if (project.platform == Project.PLATFORM_ANDROID) {
                        R.drawable.ic_android
                    } else {
                        R.drawable.ic_apple
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .zIndex(1.0f)
                    .constrainAs(platform) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(defaultMargin)
            )
        }
    }
}

@Preview(widthDp = 200)
@Composable
fun PreviewProjectItem() {
    MaterialTheme(colors = cvColors()) {
        ProjectItem(
            project = Project(
                platform = "android",
                webPic = "https://images.freeimages.com/images/large-previews/996/easter-1399885.jpg",
                name = "Project Name"
            ),
            itemClicked = null
        )
    }
}
