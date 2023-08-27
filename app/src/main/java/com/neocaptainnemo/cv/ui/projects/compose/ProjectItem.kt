package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.compose.LightColors
import com.neocaptainnemo.cv.ui.compose.UrlImage

private val projectCardImageHeight = 200.dp
private val projectCardHeight = 250.dp

@Composable
fun ProjectItem(
    project: Project,
    itemClicked: ProjectItemClicked?,
) {

    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_default_corner)),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.app_small_margin))
            .fillMaxWidth()
            .height(projectCardHeight)
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
                    height = Dimension.value(projectCardImageHeight)
                    width = Dimension.fillToConstraints
                }
            ) {
                UrlImage(
                    url = project.webPic.orEmpty(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.app_default_margin))
                )
            }

            val halfMargin = dimensionResource(id = R.dimen.app_half_margin)

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
                    .padding(dimensionResource(id = R.dimen.app_default_margin))
            )
        }
    }
}

@Preview(widthDp = 200)
@Composable
fun PreviewProjectItem() {
    MaterialTheme(LightColors) {
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
